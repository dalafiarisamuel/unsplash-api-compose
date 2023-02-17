package ng.devtamuno.unsplash.compose.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.data.model.ui.PhotoUrls
import ng.devtamuno.unsplash.compose.data.model.ui.dummyPhoto
import ng.devtamuno.unsplash.compose.ui.components.*
import ng.devtamuno.unsplash.compose.ui.event.HomeScreenEvent
import ng.devtamuno.unsplash.compose.ui.state.HomeScreenState
import ng.devtamuno.unsplash.compose.ui.theme.UnsplashAPIComposeTheme
import ng.devtamuno.unsplash.compose.ui.theme.appWhite

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    state: HomeScreenState = HomeScreenState(),
    imageList: Flow<PagingData<Photo>> = flowOf(PagingData.empty()),
    dispatch: (HomeScreenEvent) -> Unit = {}
) {

    val listScrollState = rememberLazyGridState()
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    val toolbarHeight = 105.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    var toolbarOffsetHeightPx by remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx + delta
                toolbarOffsetHeightPx = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // Returning Zero so we just observe the scroll but don't execute it
                return Offset.Zero
            }
        }
    }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            dispatch(HomeScreenEvent.ImagePreviewDialog.Open)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 21.dp, end = 21.dp)
    ) {

        Spacer(modifier = Modifier.padding(top = 20.dp))

        Text(
            text = stringResource(id = R.string.unsplash_images),
            color = appWhite,
            fontSize = 22.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold
        )

        CollapsibleSearchBar(
            toolbarOffset = toolbarOffsetHeightPx,
            toolbarHeight = toolbarHeight,
            keyboardAction = {
                coroutine.launch(Dispatchers.Main) {
                    listScrollState.scrollToItem(0)
                }
                dispatch(HomeScreenEvent.Search)
            },
            textValue = state.searchFieldValue,
            textValueChange = {
                dispatch(HomeScreenEvent.UpdateSearchField(it))
            },
        )

        ChipComponent(
            modifier = Modifier.padding(top = 20.dp),
            selectedText = state.searchFieldValue,
        ) {
            coroutine.launch(Dispatchers.Main) {
                listScrollState.scrollToItem(0)
            }
            dispatch(HomeScreenEvent.SelectChip(it))
        }

        UnsplashImageList(
            modifier = Modifier.fillMaxSize(),
            imageList = imageList.collectAsLazyPagingItems(),
            lazyListState = listScrollState,
            nestedScrollConnection = nestedScrollConnection,
            onItemClicked = {
                dispatch(HomeScreenEvent.OnImageClicked(it))
            },
            onItemLongClicked = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) -> {
                        dispatch(HomeScreenEvent.OnImageLongClicked(it))
                    }
                    else -> {
                        launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                }
            }
        )

        if (state.isImagePreviewDialogVisible) {
            ImagePreviewDialog(
                photo = state.selectedImage
            ) {
                dispatch(HomeScreenEvent.ImagePreviewDialog.Dismiss)
            }
        }

        if (state.isDownloadImageDialogVisible) {
            DownloadImageAlertDialog(
                onDismissClicked = {
                    dispatch(HomeScreenEvent.DownloadImageDialog.Dismiss)
                },
                onConfirmedClicked = {
                    dispatch(HomeScreenEvent.DownloadImageDialog.Dismiss)
                    dispatch(HomeScreenEvent.DownloadSelectedImage)
                    Toast.makeText(context, R.string.download_has_started, Toast.LENGTH_SHORT)
                        .show()
                }
            )
        }
    }

}

@ExperimentalComposeUiApi
@OptIn(ExperimentalFoundationApi::class)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun PreviewHomeScreen() {

    UnsplashAPIComposeTheme {
        HomeScreen(
            state = HomeScreenState(
                selectedImage = dummyPhoto,
                isImagePreviewDialogVisible = true,
                isDownloadImageDialogVisible = false,
                searchFieldValue = "Comet"
            ),
            imageList = flowOf(PagingData.from(listOf(dummyPhoto)))
        )
    }
}

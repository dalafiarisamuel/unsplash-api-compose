package ng.devtamuno.unsplash.compose.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.data.model.ui.dummyPhoto
import ng.devtamuno.unsplash.compose.ui.components.*
import ng.devtamuno.unsplash.compose.ui.dialog.ImagePreviewDialog
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
    onLongClicked: (Photo) -> Unit = {},
    dispatch: (HomeScreenEvent) -> Unit = {},
) {

    val lazyGridState = rememberLazyStaggeredGridState()
    val coroutine = rememberCoroutineScope()
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)
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
                    lazyGridState.scrollToItem(0)
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
                lazyGridState.scrollToItem(0)
            }
            dispatch(HomeScreenEvent.SelectChip(it))
        }

        UnsplashImageList(
            modifier = Modifier.fillMaxSize(),
            imageList = imageList,
            lazyGridState = lazyGridState,
            nestedScrollConnection = nestedScrollConnection,
            onItemClicked = {
                dispatch(HomeScreenEvent.OnImageClicked(it))
            },
            onItemLongClicked = {
                it?.let { onLongClicked(it) }
            }
        )

        if (state.isImagePreviewDialogVisible) {
            ImagePreviewDialog(
                photo = state.selectedImage
            ) {
                dispatch(HomeScreenEvent.ImagePreviewDialog.Dismiss)
            }
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

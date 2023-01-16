package ng.devtamuno.unsplash.compose.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.ui.components.*
import ng.devtamuno.unsplash.compose.ui.theme.appWhite
import ng.devtamuno.unsplash.compose.ui.viewmodel.ImageListViewModel

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun UnsplashHomeScreen() {

    val viewModel: ImageListViewModel = viewModel()

    val listScrollState = rememberLazyListState()
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    var selectedImage by remember { mutableStateOf<Photo?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }
    var isDownloadImageDialogVisible by remember { mutableStateOf(false) }
    val isListEmpty = remember { MutableTransitionState(false) }

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
            isDownloadImageDialogVisible = true
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
                viewModel.searchCurrentQuery()
            },
            textValue = viewModel.textFieldState,
            textValueChange = {
                viewModel.textFieldState = it
            },
        )

        ChipComponent(
            modifier = Modifier.padding(top = 20.dp),
            selectedText = viewModel.selectedChipState,
        ) {
            coroutine.launch(Dispatchers.Main) {
                listScrollState.scrollToItem(0)
            }
            viewModel.updateSelectedChipState(it)
        }

        UnsplashImageList(modifier = Modifier.fillMaxSize(),
            imageList = viewModel.photos.collectAsLazyPagingItems(),
            lazyListState = listScrollState,
            nestedScrollConnection = nestedScrollConnection,
            isDataReturnedEmpty = {
                isListEmpty.targetState = it
            },
            onItemClicked = {
                selectedImage = it
                isDialogVisible = true
            },
            onItemLongClicked = {
                selectedImage = it
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context, Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) -> {
                        isDownloadImageDialogVisible = true
                    }
                    else -> {
                        launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    }
                }
            })

        AnimatedVisibility(
            visibleState = isListEmpty,
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(tween(durationMillis = 250))
        ) {
            EmptyListStateComponent(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            )
        }

        if (isDialogVisible) {
            ImagePreviewDialog(photo = selectedImage) {
                isDialogVisible = false
            }
        }

        if (isDownloadImageDialogVisible) {
            val message = stringResource(R.string.download_has_started)
            DownloadImageAlertDialog(
                onDismissClicked = {
                    isDownloadImageDialogVisible = false
                },
                onConfirmedClicked = {
                    isDownloadImageDialogVisible = false
                    viewModel.downloadFile(selectedImage)
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

}

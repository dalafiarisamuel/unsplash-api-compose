package ng.devtamuno.unsplash.compose.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.ui.components.ChipComponent
import ng.devtamuno.unsplash.compose.ui.components.CollapsibleSearchBar
import ng.devtamuno.unsplash.compose.ui.components.EmptyListStateComponent
import ng.devtamuno.unsplash.compose.ui.components.UnsplashImageList
import ng.devtamuno.unsplash.compose.ui.theme.appDark
import ng.devtamuno.unsplash.compose.ui.theme.appWhite
import ng.devtamuno.unsplash.compose.ui.viewmodel.ImageListViewModel

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun UnsplashHomeScreen() {

    val viewModel: ImageListViewModel = viewModel()

    val listScrollState = rememberLazyListState()
    val coroutine = rememberCoroutineScope()

    val selectedImage = remember { mutableStateOf<Photo?>(null) }
    var isDialogVisible by remember { mutableStateOf(false) }
    val isListEmpty = remember { MutableTransitionState(false) }

    val toolbarHeight = 105.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // Returning Zero so we just observe the scroll but don't execute it
                return Offset.Zero
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = appDark)
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
            toolbarOffset = toolbarOffsetHeightPx.value,
            toolbarHeight = toolbarHeight,
            keyboardAction = {
                coroutine.launch(Dispatchers.Main) {
                    listScrollState.scrollToItem(0)
                }
                viewModel.searchCurrentQuery()
            },
            textValue = viewModel.textFieldState.value,
            textValueChange = {
                viewModel.textFieldState.value = it
            },
        )

        ChipComponent(
            modifier = Modifier
                .padding(top = 20.dp),
            selectedText = viewModel.selectedChipState.value,
        ) {
            coroutine.launch(Dispatchers.Main) {
                listScrollState.scrollToItem(0)
            }
            viewModel.updateSelectedChipState(it)
        }


        UnsplashImageList(
            flowData = viewModel.photos,
            lazyListState = listScrollState,
            nestedScrollConnection = nestedScrollConnection,
            isDataReturnedEmpty = {
                isListEmpty.targetState = it
            },
            onItemClicked = {
                selectedImage.value = it
                isDialogVisible = true
            },
            onItemLongClicked = {

            }
        )

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
            ImagePreviewDialog(
                selectedImage.value
            ) {
                isDialogVisible = false
            }
        }

    }

}

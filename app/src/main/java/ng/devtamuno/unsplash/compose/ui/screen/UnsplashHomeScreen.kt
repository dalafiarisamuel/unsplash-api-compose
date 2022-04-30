package ng.devtamuno.unsplash.compose.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.ui.components.*
import ng.devtamuno.unsplash.compose.ui.theme.appDark
import ng.devtamuno.unsplash.compose.ui.theme.appWhite
import ng.devtamuno.unsplash.compose.ui.viewmodel.ImageListViewModel

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun UnsplashHomeScreen(viewModel: ImageListViewModel = viewModel()) {

    val listScrollState = rememberLazyListState()

    val keyboard = LocalSoftwareKeyboardController.current

    Surface(modifier = Modifier.fillMaxSize(), color = appDark) {

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

            Text(
                text = stringResource(id = R.string.search_images),
                color = appWhite,
                fontSize = 13.sp,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(top = 25.dp)
            )

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {

                UnsplashSearchBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(weight = 3f)
                        .padding(end = 10.dp),
                    textValue = viewModel.textFieldState.value,
                    keyboard = keyboard,
                    keyboardAction = {
                        viewModel.scroll(0, listScrollState)
                        viewModel.searchCurrentQuery()
                    }
                ) {
                    viewModel.textFieldState.value = it
                }

                SearchButton(
                    modifier = Modifier
                        .size(55.dp)
                        .weight(weight = 0.6f)

                ) {
                    keyboard?.hide()
                    viewModel.scroll(0, listScrollState)
                    viewModel.searchCurrentQuery()
                }
            }

            ChipComponent(
                modifier = Modifier
                    .padding(top = 20.dp),
                selectedText = viewModel.selectedChipState.value,
            ) {
                viewModel.scroll(0, listScrollState)
                viewModel.updateSelectedChipState(it)
            }


            UnsplashImageList(
                flowData = viewModel.photos,
                lazyListState = listScrollState,
                isDataReturnedEmpty = {
                    viewModel.isListEmpty.targetState = it
                },
                onItemClicked = {
                    viewModel.unsplashPhotoData.value = it
                    viewModel.dialogState.value = true
                },
                onItemLongClicked = {

                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    visibleState = viewModel.isListEmpty,
                    enter = fadeIn(initialAlpha = 0.4f),
                    exit = fadeOut(tween(durationMillis = 250))

                ) {
                    EmptyListStateComponent()
                }
            }


            if (viewModel.dialogState.value) {
                ImagePreviewDialog(
                    viewModel.unsplashPhotoData.value
                ) {
                    viewModel.dialogState.value = false
                }
            }
        }

    }

}

package ng.devtamuno.unsplash.compose.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ng.devtamuno.unsplash.compose.ui.navigation.PhotoScreen
import ng.devtamuno.unsplash.compose.ui.viewmodel.HomeScreenViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreenEntryPoint(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {

    HomeScreen(
        state = viewModel.state,
        imageList = viewModel.photos,
        onLongClicked = {
            navController.navigate(PhotoScreen.PHOTO_DETAIL.createPath(it.id))
        },
        dispatch = viewModel::dispatch
    )
}
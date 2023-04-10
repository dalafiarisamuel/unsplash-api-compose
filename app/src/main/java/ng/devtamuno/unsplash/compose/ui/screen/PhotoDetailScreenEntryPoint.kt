package ng.devtamuno.unsplash.compose.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ng.devtamuno.unsplash.compose.ui.viewmodel.PhotoDetailViewModel

@Composable
fun PhotoDetailScreenEntryPoint(
    navController: NavController,
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    PhotoDetailScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        onDownloadClicked = viewModel::downloadImage,
        onBackPressed = {
            navController.popBackStack()
        }
    )
}
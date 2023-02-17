package ng.devtamuno.unsplash.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import dagger.hilt.android.AndroidEntryPoint
import ng.devtamuno.unsplash.compose.ui.screen.HomeScreen
import ng.devtamuno.unsplash.compose.ui.theme.UnsplashAPIComposeTheme
import ng.devtamuno.unsplash.compose.ui.viewmodel.HomeScreenViewModel

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UnsplashAPIComposeTheme {
                HomeScreen(
                    state = viewModel.state,
                    imageList = viewModel.photos
                ) {
                    viewModel.dispatch(it)
                }
            }
        }
    }
}

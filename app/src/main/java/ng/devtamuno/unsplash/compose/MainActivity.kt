package ng.devtamuno.unsplash.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import dagger.hilt.android.AndroidEntryPoint
import ng.devtamuno.unsplash.compose.ui.navigation.AppScreen
import ng.devtamuno.unsplash.compose.ui.theme.UnsplashAPIComposeTheme

@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { UnsplashAPIComposeTheme { AppScreen() } }
    }
}

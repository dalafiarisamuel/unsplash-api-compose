package ng.devtamuno.unsplash.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ng.devtamuno.unsplash.compose.ui.screen.HomeScreenEntryPoint
import ng.devtamuno.unsplash.compose.ui.screen.PhotoDetailScreenEntryPoint

@Composable
fun AppScreen() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PhotoScreen.PHOTO_LIST.route) {

        composable(PhotoScreen.PHOTO_LIST.route) { HomeScreenEntryPoint(navController) }

        composable(
            route = PhotoScreen.PHOTO_DETAIL.route,
            arguments = listOf(navArgument(ARG_PHOTO_ID) { type = NavType.StringType }),
        ) { PhotoDetailScreenEntryPoint(navController) }

    }
}
package ng.devtamuno.unsplash.compose.ui.state

import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashPhotoRemote


data class PhotoDetailState(
    val isLoading: Boolean = true,
    val photo: UnsplashPhotoRemote? = null,
    val error: Throwable? = null
)

package ng.devtamuno.unsplash.compose.ui.state

import androidx.compose.runtime.Stable
import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashPhotoRemote

@Stable
data class PhotoDetailState(
    val isLoading: Boolean = true,
    val photo: UnsplashPhotoRemote? = null,
    val error: Throwable? = null,
    val intentPhotoId: String? = null
)

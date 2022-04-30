package ng.devtamuno.unsplash.compose.data.model.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashResponseRemote(
    val results: List<UnsplashPhotoRemote>
)
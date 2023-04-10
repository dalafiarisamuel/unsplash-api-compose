package ng.devtamuno.unsplash.compose.data.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UnsplashResponseRemote(
    val total: Long,
    @Json(name = "total_pages")
    val totalPages: Long,
    val results: List<UnsplashPhotoRemote>
)
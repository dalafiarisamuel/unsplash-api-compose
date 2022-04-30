package ng.devtamuno.unsplash.compose.data.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UnsplashPhotoRemote(
    val id: String,
    @Json(name = "blur_hash")
    val blurHash: String?,
    val width: Int,
    val height: Int,
    val color: String,
    @Json(name = "alt_description")
    val alternateDescription: String?,
    val description: String?,
    val urls: UnsplashPhotoUrls,
    val user: UnsplashUser
)

@JsonClass(generateAdapter = true)
data class UnsplashPhotoUrls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

@JsonClass(generateAdapter = true)
data class UnsplashUser(
    val name: String,
    val username: String
) {
    val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageLoader&utm_medium=referral"
}
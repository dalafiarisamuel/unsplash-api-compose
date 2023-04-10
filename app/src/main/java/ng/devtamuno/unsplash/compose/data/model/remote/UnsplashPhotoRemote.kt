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
    val likes: Int,
    @Json(name = "alt_description")
    val alternateDescription: String?,
    @Json(name = "created_at")
    val createdAt: String,
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
    val id: String,
    val name: String,
    val username: String,
    val bio: String?,
    @Json(name = "total_collections")
    val totalCollections: Int,
    @Json(name = "total_photos")
    val totalPhotos: Long,
    @Json(name = "total_likes")
    val totalLikes: Long,
    @Json(name = "portfolio_url")
    val portfolioUrl: String?,
    @Json(name = "profile_image")
    val profileImage: ProfileImage,
) {
    val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageLoader&utm_medium=referral"
}

@JsonClass(generateAdapter = true)
data class ProfileImage(
    val small: String,
    val medium: String,
    val large: String
)
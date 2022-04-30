package ng.devtamuno.unsplash.compose.data.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val blurHash: String?,
    val width: Int,
    val height: Int,
    val color: String,
    val alternateDescription: String?,
    val description: String?,
    val urls: PhotoUrls,
    val user: PhotoCreator
) : Parcelable

@Parcelize
data class PhotoUrls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
) : Parcelable


@Parcelize
data class PhotoCreator(
    val name: String,
    val username: String,
    val attributionUrl: String
) : Parcelable

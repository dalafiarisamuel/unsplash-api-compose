package ng.devtamuno.unsplash.compose.data.repository

import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashPhotoRemote
import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashResponseRemote

interface ImageRepository {
    suspend fun getImageSearchResult(
        query: String,
        page: Int,
        loadSize: Int
    ): UnsplashResponseRemote

    suspend fun getPhoto(photoId: String): Resource<UnsplashPhotoRemote>
}
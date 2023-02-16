package ng.devtamuno.unsplash.compose.data.repository

import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashResponseRemote

interface ImageRepository {
    suspend fun getImageSearchResult(
        query: String,
        page: Int,
        loadSize: Int
    ): UnsplashResponseRemote
}
package ng.devtamuno.unsplash.compose.data.repository

import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashPhotoRemote
import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashResponseRemote
import ng.devtamuno.unsplash.compose.networking.ApiInterface

internal class ImageRepositoryImpl(private val api: ApiInterface) : ImageRepository {

    override suspend fun getImageSearchResult(
        query: String,
        page: Int,
        loadSize: Int
    ): UnsplashResponseRemote {
        return api.searchPhotos(query, page, loadSize)
    }
    override suspend fun getPhoto(photoId: String): Resource<UnsplashPhotoRemote> {
        return resourceHelper { api.getPhoto(photoId) }
    }
}
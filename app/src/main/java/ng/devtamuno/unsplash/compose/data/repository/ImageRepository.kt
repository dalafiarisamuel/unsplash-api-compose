package ng.devtamuno.unsplash.compose.data.repository

import ng.devtamuno.unsplash.compose.networking.ApiInterface
import javax.inject.Inject

class ImageRepository @Inject constructor(private val api: ApiInterface) {
    suspend fun getImageSearchResult(query: String, page: Int, loadSize: Int) =
        api.searchPhotos(query, page, loadSize)
}
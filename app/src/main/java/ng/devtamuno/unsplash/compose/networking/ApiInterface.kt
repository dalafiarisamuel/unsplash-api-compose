package ng.devtamuno.unsplash.compose.networking

import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashPhotoRemote
import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashResponseRemote
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Accept-Version: v1")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponseRemote
    @GET("photos/{id}")
    suspend fun getPhoto(@Path("id") photoId: String): UnsplashPhotoRemote
}
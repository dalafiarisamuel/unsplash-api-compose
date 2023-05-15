package ng.devtamuno.unsplash.compose.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException
import ng.devtamuno.unsplash.compose.data.mapper.PhotoMapper
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.data.repository.ImageRepository
import retrofit2.HttpException

class ImagePagingSource(
    private val repository: ImageRepository,
    private val photoMapper: PhotoMapper,
    private val query: String,
) : PagingSource<Int, Photo>() {
    companion object {
        private const val STARTING_PAGE_INDEX = 1
        const val PAGE_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int {
        return STARTING_PAGE_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = repository.getImageSearchResult(query, position, params.loadSize)
            val photos = photoMapper.mapToUIList(response.results)

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (position >= response.totalPages) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
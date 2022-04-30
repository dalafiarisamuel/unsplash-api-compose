package ng.devtamuno.unsplash.compose.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ng.devtamuno.unsplash.compose.data.mapper.PhotoMapper
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.data.repository.ImageRepository
import retrofit2.HttpException
import java.io.IOException


private const val STARTING_PAGE_INDEX = 1

class ImagePagingSource(
    private val repository: ImageRepository,
    private val photoMapper: PhotoMapper,
    private val query: String
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = repository.getImageSearchResult(query, position, params.loadSize)
            val photos = photoMapper.mapToUIList(response.results)

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}
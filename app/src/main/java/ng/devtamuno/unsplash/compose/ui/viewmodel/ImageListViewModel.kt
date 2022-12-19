package ng.devtamuno.unsplash.compose.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ng.devtamuno.unsplash.compose.data.mapper.PhotoMapper
import ng.devtamuno.unsplash.compose.data.repository.ImageRepository
import ng.devtamuno.unsplash.compose.data.source.ImagePagingSource
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val photoMapper: PhotoMapper,
    state: SavedStateHandle
) : ViewModel() {

    var selectedChipState by mutableStateOf("")
    var textFieldState by mutableStateOf("")

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    private fun setSearchTerm(query: String) {
        currentQuery.value = query.ifEmpty { DEFAULT_QUERY }
    }

    fun searchCurrentQuery() {
        setSearchTerm(textFieldState)
    }

    fun updateSelectedChipState(term: String) {
        selectedChipState = term
        textFieldState = term
        setSearchTerm(term)
    }

    val photos = currentQuery.switchMap { queryString ->
        getImageSearchResult(queryString).cachedIn(viewModelScope)
    }.asFlow()

    private fun getImageSearchResult(query: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 200,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            ImagePagingSource(
                repository = repository,
                photoMapper = photoMapper,
                query = query
            )
        }
    ).liveData

    companion object {
        private const val CURRENT_QUERY = "current_query"
        const val DEFAULT_QUERY = "corgi"
    }
}

package ng.devtamuno.unsplash.compose.ui.viewmodel

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ng.devtamuno.unsplash.compose.data.mapper.PhotoMapper
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.data.repository.ImageRepository
import ng.devtamuno.unsplash.compose.data.source.ImagePagingSource
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val photoMapper: PhotoMapper,
    state: SavedStateHandle
) : ViewModel() {

    var selectedChipState = mutableStateOf("")
    var textFieldState = mutableStateOf("")
    var dialogState = mutableStateOf(false)
    var isListEmpty = MutableTransitionState(false)
    var unsplashPhotoData = mutableStateOf<Photo?>(null)

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    private fun setSearchTerm(query: String) {
        currentQuery.value = query.ifEmpty { DEFAULT_QUERY }
    }

    fun searchCurrentQuery() {
        setSearchTerm(textFieldState.value)
    }

    fun updateSelectedChipState(term: String) {
        selectedChipState.value = term
        textFieldState.value = term
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

    fun scroll(position: Int, lazyList: LazyListState) {
        viewModelScope.launch(Dispatchers.Main) {
            lazyList.scrollToItem(position)
        }
    }


    companion object {
        private const val CURRENT_QUERY = "current_query"
        const val DEFAULT_QUERY = "corgi"
    }
}

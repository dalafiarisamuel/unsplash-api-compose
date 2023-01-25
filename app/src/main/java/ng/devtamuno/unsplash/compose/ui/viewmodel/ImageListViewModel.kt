package ng.devtamuno.unsplash.compose.ui.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import ng.devtamuno.unsplash.compose.data.mapper.PhotoMapper
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.data.repository.ImageRepository
import ng.devtamuno.unsplash.compose.data.source.ImagePagingSource
import ng.devtamuno.unsplash.compose.file.FileDownloader
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val photoMapper: PhotoMapper,
    private val fileDownloader: FileDownloader,
    state: SavedStateHandle
) : ViewModel() {

    var selectedChipState by mutableStateOf("")
    var textFieldState by mutableStateOf("")

    private var currentQuery: MutableStateFlow<String>
    private val randomDefaultQuery get() = DEFAULT_QUERY.random()

    init {
        currentQuery = MutableStateFlow(state[CURRENT_QUERY] ?: randomDefaultQuery)
        watchCurrentQueryField()
    }

    private fun setSearchTerm(query: String) {
        currentQuery.value = query.ifEmpty { randomDefaultQuery }
    }

    private fun watchCurrentQueryField() {
        currentQuery
            .flatMapMerge<String, Unit> {
                selectedChipState = it
                flowOf(Unit)
            }.launchIn(viewModelScope)
    }

    fun searchCurrentQuery() {
        setSearchTerm(textFieldState)
    }

    fun updateSelectedChipState(term: String) {
        selectedChipState = term
        textFieldState = term
        setSearchTerm(term)
    }

    val photos = currentQuery.flatMapLatest { queryString ->
        getImageSearchResult(queryString).cachedIn(viewModelScope)
    }

    private fun getImageSearchResult(query: String) = Pager(
        config = PagingConfig(
            pageSize = 25,
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
    ).flow

    fun downloadFile(photo: Photo?) {
        if (photo != null) {
            fileDownloader.downloadImageFileToDownloadFolder(photo.urls.full)
        }
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private val DEFAULT_QUERY = listOf("corgi", "comet", "ai", "dreams")
    }
}

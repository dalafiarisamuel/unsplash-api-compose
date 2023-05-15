package ng.devtamuno.unsplash.compose.ui.viewmodel


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import ng.devtamuno.unsplash.compose.data.mapper.PhotoMapper
import ng.devtamuno.unsplash.compose.data.repository.ImageRepository
import ng.devtamuno.unsplash.compose.data.source.ImagePagingSource
import ng.devtamuno.unsplash.compose.ui.event.HomeScreenEvent
import ng.devtamuno.unsplash.compose.ui.state.HomeScreenState
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val photoMapper: PhotoMapper,
    state: SavedStateHandle
) : MviViewModel<HomeScreenEvent, HomeScreenState>(HomeScreenState()) {

    companion object {
        const val CURRENT_QUERY = "current_query"
        private val DEFAULT_QUERY = listOf("Nigeria", "Egypt", "Africa", "Dreams", "Husky")
    }

    private var currentQuery: MutableStateFlow<String>
    private val randomDefaultQuery get() = DEFAULT_QUERY.random()

    init {
        currentQuery = MutableStateFlow(state[CURRENT_QUERY] ?: randomDefaultQuery)
        watchCurrentQueryField()

        handleSelectImage()
        handleSearchEvent()

        handleSelectChipEvent()
        handleUpdateSearchFieldEvent()

        handleOpenImagePreviewDialogEvent()
        handleDismissImagePreviewDialogEvent()

        handleOnImageClicked()
    }

    private fun setSearchTerm(query: String) {
        currentQuery.value = query.ifEmpty { randomDefaultQuery }
    }

    private fun watchCurrentQueryField() {
        currentQuery
            .debounce(1.seconds)
            .flatMapMerge<String, Unit> {
                state = state.copy(searchFieldValue = it)
                flowOf(Unit)
            }.launchIn(viewModelScope)
    }

    val photos = currentQuery
        .debounce(1.seconds)
        .flatMapLatest { queryString ->
            getImageSearchResult(queryString)
        }.cachedIn(viewModelScope)

    private fun getImageSearchResult(query: String) = Pager(
        config = PagingConfig(
            pageSize = ImagePagingSource.PAGE_SIZE,
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

    private fun handleSelectChipEvent() {
        on<HomeScreenEvent.SelectChip> {
            state = state.copy(
                searchFieldValue = it.chipValue
            )
            setSearchTerm(state.searchFieldValue)
        }
    }

    private fun handleSelectImage() {
        on<HomeScreenEvent.SelectImage> {
            state = state.copy(selectedImage = it.image)
        }
    }

    private fun handleSearchEvent() {
        on<HomeScreenEvent.Search> {
            setSearchTerm(state.searchFieldValue)
        }
    }

    private fun handleUpdateSearchFieldEvent() {
        on<HomeScreenEvent.UpdateSearchField> {
            state = state.copy(
                searchFieldValue = it.searchTerm
            )
            setSearchTerm(state.searchFieldValue)
        }
    }

    private fun handleDismissImagePreviewDialogEvent() {
        on<HomeScreenEvent.ImagePreviewDialog.Dismiss> {
            state = state.copy(isImagePreviewDialogVisible = false)
        }
    }

    private fun handleOpenImagePreviewDialogEvent() {
        on<HomeScreenEvent.ImagePreviewDialog.Open> {
            state = state.copy(isImagePreviewDialogVisible = true)
        }
    }

    private fun handleOnImageClicked() {
        on<HomeScreenEvent.OnImageClicked> {
            state = state.copy(
                selectedImage = it.image,
                isImagePreviewDialogVisible = true
            )
        }
    }

}

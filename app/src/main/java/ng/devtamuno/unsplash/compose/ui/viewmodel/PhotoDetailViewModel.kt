package ng.devtamuno.unsplash.compose.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ng.devtamuno.unsplash.compose.data.repository.ImageRepository
import ng.devtamuno.unsplash.compose.data.repository.Resource
import ng.devtamuno.unsplash.compose.file.FileDownloader
import ng.devtamuno.unsplash.compose.ui.navigation.ARG_PHOTO_ID
import ng.devtamuno.unsplash.compose.ui.state.PhotoDetailState

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val fileDownloader: FileDownloader,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PhotoDetailState())
    val uiState: StateFlow<PhotoDetailState> = _uiState.asStateFlow()

    init {
        val intentPhotoId = savedStateHandle.get<String>(ARG_PHOTO_ID)!!
        getSelectedPhotoById(intentPhotoId)
    }

    private fun getSelectedPhotoById(photoId: String) = viewModelScope.launch {
        _uiState.update { it.copy(intentPhotoId = photoId) }

        when (val result = repository.getPhoto(photoId = photoId)) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        photo = result.result,
                        error = null
                    )
                }
            }

            is Resource.Failure -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }
        }
    }

    fun downloadImage(url: String) {
        fileDownloader.downloadImageFileToDownloadFolder(url)
    }
}
package ng.devtamuno.unsplash.compose.ui.state

import androidx.compose.runtime.Stable
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
@Stable
data class HomeScreenState(
    val selectedImage: Photo? = null,
    val isImagePreviewDialogVisible: Boolean = false,
    val isDownloadImageDialogVisible: Boolean = false,
    val searchFieldValue: String = "",
)

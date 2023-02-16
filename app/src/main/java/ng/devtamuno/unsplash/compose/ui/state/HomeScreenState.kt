package ng.devtamuno.unsplash.compose.ui.state

import ng.devtamuno.unsplash.compose.data.model.ui.Photo

data class HomeScreenState(
    val selectedImage: Photo? = null,
    val isImagePreviewDialogVisible: Boolean = false,
    val isDownloadImageDialogVisible: Boolean = false,
    val searchFieldValue: String = ""
)

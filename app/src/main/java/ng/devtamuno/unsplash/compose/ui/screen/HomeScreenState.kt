package ng.devtamuno.unsplash.compose.ui.screen

import ng.devtamuno.unsplash.compose.data.model.ui.Photo

data class HomeScreenState(
    val selectedImage: Photo?,
    val isImagePreviewDialogVisible: Boolean,
    val isDownloadImageDialogVisible: Boolean,
)

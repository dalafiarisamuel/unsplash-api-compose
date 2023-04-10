package ng.devtamuno.unsplash.compose.ui.event

import ng.devtamuno.unsplash.compose.data.model.ui.Photo

sealed class HomeScreenEvent {

    data class SelectChip(val chipValue: String) : HomeScreenEvent()

    data class SelectImage(val image: Photo?) : HomeScreenEvent()

    data class OnImageClicked(val image: Photo?) : HomeScreenEvent()

    data class UpdateSearchField(val searchTerm: String) : HomeScreenEvent()
    data class UpdateCollapsibleToolBarVisibility(val isContentVisible: Boolean) : HomeScreenEvent()

    sealed class ImagePreviewDialog : HomeScreenEvent() {

        object Dismiss : ImagePreviewDialog()

        object Open : ImagePreviewDialog()
    }

    object Search : HomeScreenEvent()

}

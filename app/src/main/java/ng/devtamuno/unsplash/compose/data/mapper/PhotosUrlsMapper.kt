package ng.devtamuno.unsplash.compose.data.mapper

import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashPhotoUrls
import ng.devtamuno.unsplash.compose.data.model.ui.PhotoUrls
import javax.inject.Inject

class PhotosUrlsMapper @Inject constructor() : UIModelMapper<UnsplashPhotoUrls, PhotoUrls>() {
    override fun mapToUI(entity: UnsplashPhotoUrls): PhotoUrls {
        return with(entity) {
            PhotoUrls(raw, full, regular, small, thumb)
        }
    }

    override fun mapFromUI(model: PhotoUrls): UnsplashPhotoUrls {
        throw Exception("Doesn't work that way")
    }
}
package ng.devtamuno.unsplash.compose.data.mapper

import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashPhotoRemote
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import javax.inject.Inject

class PhotoMapper @Inject constructor(
    private val photosUrlsMapper: PhotosUrlsMapper,
    private val photoCreatorMapper: PhotoCreatorMapper
) : UIModelMapper<UnsplashPhotoRemote, Photo>() {
    override fun mapToUI(entity: UnsplashPhotoRemote): Photo {
        return with(entity) {
            Photo(
                id,
                blurHash,
                width,
                height,
                color,
                alternateDescription,
                description,
                photosUrlsMapper.mapToUI(urls),
                photoCreatorMapper.mapToUI(user)
            )
        }
    }

    override fun mapFromUI(model: Photo): UnsplashPhotoRemote {
        throw Exception("Doesn't work that way")
    }
}
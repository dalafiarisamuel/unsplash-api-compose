package ng.devtamuno.unsplash.compose.data.mapper

import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashUser
import ng.devtamuno.unsplash.compose.data.model.ui.PhotoCreator
import javax.inject.Inject

class PhotoCreatorMapper @Inject constructor() : UIModelMapper<UnsplashUser, PhotoCreator>() {
    override fun mapToUI(entity: UnsplashUser): PhotoCreator {
        return with(entity) {
            PhotoCreator(name, username, attributionUrl)
        }
    }

    override fun mapFromUI(model: PhotoCreator): UnsplashUser {
        throw Exception("Doesn't work that way")
    }
}
package ng.devtamuno.unsplash.compose.data.dummy

import ng.devtamuno.unsplash.compose.data.model.remote.ProfileImage
import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashPhotoRemote
import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashPhotoUrls
import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashResponseRemote
import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashUser

object RemoteData {

    val remotePhoto = UnsplashPhotoRemote(
        id = "ldLty8YYYP4",
        color = "#0c2640",
        alternateDescription = "blue sky with stars during night time",
        blurHash = "L02~\$RkEQ*ogH;axogaxn3ofbHay",
        description = "Old stone background texture",
        height = 3238,
        width = 4857,
        likes = 200,
        createdAt = "2016-05-03T11:00:28-04:00",
        urls = UnsplashPhotoUrls(
            raw = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
            full = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
            regular = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
            small = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
            thumb = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5"
        ),
        user = UnsplashUser(
            id = "pXhwzz1JtQU",
            name = "Rich Wooten",
            username = "whatsawoot",
            bio = null,
            totalCollections = 12,
            totalLikes = 437,
            totalPhotos = 5000,
            portfolioUrl = "https://this-is-a-fake-url",
            profileImage = ProfileImage(
                small = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
                medium = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
                large = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
            )
        )
    )

    val remoteResponse = UnsplashResponseRemote(
        totalPages = 1,
        total = 3,
        results = listOf(remotePhoto, remotePhoto, remotePhoto)
    )
}
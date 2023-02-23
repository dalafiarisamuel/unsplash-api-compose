package ng.devtamuno.unsplash.compose.data.dummy

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
        urls = UnsplashPhotoUrls(
            raw = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
            full = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
            regular = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
            small = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5",
            thumb = "https://images.unsplash.com/photo-1606707764561-ed73aab9fdd5"
        ),
        user = UnsplashUser(
            name = "Rich Wooten",
            username = "whatsawoot",
        )
    )

    val remoteResponse = UnsplashResponseRemote(listOf(remotePhoto, remotePhoto, remotePhoto))
}
package ng.devtamuno.unsplash.compose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.data.model.remote.ProfileImage
import ng.devtamuno.unsplash.compose.data.model.remote.UnsplashUser
import ng.devtamuno.unsplash.compose.ui.theme.UnsplashAPIComposeTheme
import ng.devtamuno.unsplash.compose.ui.theme.appWhite

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtistCard(modifier: Modifier = Modifier, unsplashUser: UnsplashUser) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        elevation = 24.dp,
        shape = RoundedCornerShape(10.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberAsyncImagePainter(unsplashUser.profileImage.large),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(65.dp)
                    .border(width = 1.5.dp, color = appWhite, shape = CircleShape)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = unsplashUser.name,
                    color = appWhite,
                    fontSize = 17.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.basicMarquee()
                )

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.unsplash_svg),
                        tint = appWhite,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "@${unsplashUser.username}",
                        color = appWhite,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 20.dp,
                        alignment = Alignment.Start
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconText(
                        drawableId = R.drawable.round_favorite,
                        display = pluralStringResource(
                            id = R.plurals.like_plural,
                            count = unsplashUser.totalLikes.toInt(),
                            formatArgs = arrayOf(unsplashUser.totalLikes.toInt())
                        )
                    )

                    IconText(
                        drawableId = R.drawable.collections,
                        display = pluralStringResource(
                            id = R.plurals.collection_plural,
                            count = unsplashUser.totalCollections,
                            formatArgs = arrayOf(unsplashUser.totalCollections)
                        )
                    )

                    IconText(
                        drawableId = R.drawable.ic_image,
                        display = pluralStringResource(
                            id = R.plurals.photo_plural,
                            count = unsplashUser.totalPhotos.toInt(),
                            formatArgs = arrayOf(unsplashUser.totalPhotos.toInt())
                        )
                    )

                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewArtistCard() {
    UnsplashAPIComposeTheme {
        val unsplashUser = UnsplashUser(
            id = "niinoiud373",
            name = "Samuel Dalafiari",
            username = "devTamuno",
            bio = null,
            totalCollections = 12,
            totalLikes = 437,
            totalPhotos = 5000,
            portfolioUrl = "https://this-is-a-fake-url",
            profileImage = ProfileImage(
                small = "https://this-is-a-fake-url",
                medium = "https://this-is-a-fake-url",
                large = "https://this-is-a-fake-url",
            )
        )
        ArtistCard(unsplashUser = unsplashUser)
    }
}
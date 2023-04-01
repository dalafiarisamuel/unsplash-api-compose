package ng.devtamuno.unsplash.compose.ui.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.request.ImageResult
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.data.model.ui.Photo
import ng.devtamuno.unsplash.compose.ui.theme.color
import ng.devtamuno.unsplash.compose.ui.theme.complementary

@Composable
fun ImagePreviewDialog(photo: Photo?, onDismissCLicked: () -> Unit) {

    Dialog(
        onDismissRequest = onDismissCLicked,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        DialogContent(
            imageUrl = photo?.urls?.regular,
            imageColor = photo?.color,
            authorOrDescriptionText = photo?.description ?: photo?.alternateDescription
            ?: photo?.user?.name
        )

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun DialogContent(
    imageUrl: String? = null,
    imageColor: String? = null,
    authorOrDescriptionText: String? = null
) {
    val imageColorParsed = (imageColor?.color ?: Color(0xFF212121))
    val imageColorParseComplementary = imageColorParsed.complementary()
    val isShowProgress = MutableTransitionState(true)

    val painter = rememberAsyncImagePainter(imageUrl)

    when (painter.state) {
        is AsyncImagePainter.State.Loading,
        is AsyncImagePainter.State.Empty -> { /*default state*/
        }
        is AsyncImagePainter.State.Error,
        is AsyncImagePainter.State.Success -> {
            isShowProgress.targetState = false
        }
    }

    Box(
        modifier = Modifier
            .width(400.dp)
            .height(500.dp)

    ) {

        Card(
            backgroundColor = imageColorParsed,
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painter,
                contentScale = ContentScale.Inside,
                contentDescription = authorOrDescriptionText,
                modifier = Modifier.wrapContentSize()
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    imageColorParsed,
                    shape = RoundedCornerShape(
                        bottomStart = 10.dp,
                        bottomEnd = 10.dp
                    )
                )
                .align(Alignment.BottomCenter)
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_image),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.CenterVertically),
                colorFilter = ColorFilter.tint(
                    color = imageColorParseComplementary
                )
            )

            Text(
                text = authorOrDescriptionText.orEmpty(),
                color = imageColorParseComplementary,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                softWrap = false,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .align(Alignment.CenterVertically)
                    .basicMarquee()
            )

        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visibleState = isShowProgress,
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(tween(durationMillis = 250))

        ) {
            CircularProgressIndicator(
                color = imageColorParseComplementary
            )
        }
    }

}
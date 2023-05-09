package ng.devtamuno.unsplash.compose.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import coil.compose.AsyncImage
import com.devtamuno.composeblurhash.ext.rememberBlurHashPainter
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
            imageUrl = photo?.urls?.full,
            imageColor = photo?.color,
            blurHash = photo?.blurHash,
            imageWidth = photo?.width,
            imageHeight = photo?.height,
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
    blurHash: String? = null,
    imageWidth: Int? = null,
    imageHeight: Int? = null,
    authorOrDescriptionText: String? = null,
) {
    val imageColorParsed = (imageColor?.color ?: Color(0xFF212121))
    val imageColorParseComplementary = imageColorParsed.complementary()
    val aspectRatio: Float by remember {
        derivedStateOf { (imageWidth?.toFloat() ?: 1.0F) / (imageHeight?.toFloat() ?: 1.0F) }
    }
    val rememberBlurHash = rememberBlurHashPainter(
        blurString = blurHash ?: "",
        width = imageWidth ?: 1,
        height = imageHeight ?: 1,
    )

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
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = rememberBlurHash,
                error = rememberBlurHash,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .aspectRatio(aspectRatio)
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp)
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
    }

}
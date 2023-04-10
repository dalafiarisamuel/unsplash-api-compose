package ng.devtamuno.unsplash.compose.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.ui.components.ArtistCard
import ng.devtamuno.unsplash.compose.ui.components.ErrorComponent
import ng.devtamuno.unsplash.compose.ui.components.NavBar
import ng.devtamuno.unsplash.compose.ui.components.PhotoLargeDisplay
import ng.devtamuno.unsplash.compose.ui.dialog.DownloadImageAlertDialog
import ng.devtamuno.unsplash.compose.ui.state.PhotoDetailState
import ng.devtamuno.unsplash.compose.ui.theme.appWhite


@Preview
@Composable
fun PhotoDetailScreen(
    modifier: Modifier = Modifier,
    state: PhotoDetailState = PhotoDetailState(),
    onDownloadClicked: (url: String) -> Unit = {},
    onBackPressed: () -> Unit = {},
) {

    val scrollableState = rememberScrollState()
    val locale = Locale.current
    val context = LocalContext.current
    var isDownloadImageDialogVisible by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            state.photo?.urls?.full?.let(onDownloadClicked)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollableState)
            .padding(horizontal = 12.dp, vertical = 20.dp)
            .then(modifier)
    ) {

        NavBar(modifier = Modifier.fillMaxWidth(), onBackPressed = onBackPressed)

        when {
            state.isLoading -> {
                LinearProgressIndicator(
                    color = appWhite,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                )
            }
            state.error != null -> {
                ErrorComponent(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("error_view"),
                    message = state.error.message ?: stringResource(
                        id = R.string.an_unknown_error_occurred
                    )
                )
            }

            else -> {

                Spacer(modifier = Modifier.padding(top = 10.dp))

                if (state.photo != null) {
                    ArtistCard(unsplashUser = state.photo.user)
                }

                Spacer(modifier = Modifier.padding(top = 10.dp))

                PhotoLargeDisplay(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(2f),
                    imageUrl = state.photo?.urls?.full.orEmpty(),
                    imageColor = state.photo?.color,
                    imageSize = Size(state.photo?.width ?: 1, state.photo?.height ?: 1)
                )

                if (state.photo?.description != null || state.photo?.alternateDescription != null) {
                    Spacer(modifier = Modifier.padding(top = 16.dp))

                    val content = state.photo.description ?: state.photo.alternateDescription

                    Text(
                        text = content.orEmpty().capitalize(locale),
                        color = appWhite,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.padding(top = 20.dp))

                OutlinedButton(
                    modifier = Modifier
                        .height(height = 45.dp)
                        .width(width = 200.dp)
                        .align(Alignment.CenterHorizontally),
                    elevation = ButtonDefaults.elevation(0.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ),
                            -> {
                                isDownloadImageDialogVisible = true
                            }
                            else -> {
                                launcher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            }
                        }
                    }
                ) {

                    Icon(
                        painterResource(id = R.drawable.round_downloading),
                        tint = appWhite,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.download_image), fontSize = 12.sp)
                }
            }
        }
    }

    if (isDownloadImageDialogVisible) {
        DownloadImageAlertDialog(
            onDismissClicked = {
                isDownloadImageDialogVisible = false
            },
            onConfirmedClicked = {
                isDownloadImageDialogVisible = false
                state.photo?.urls?.full?.let(onDownloadClicked)
                Toast.makeText(
                    context,
                    R.string.download_has_started, Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}


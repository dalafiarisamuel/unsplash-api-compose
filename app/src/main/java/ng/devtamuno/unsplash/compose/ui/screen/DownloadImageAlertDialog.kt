package ng.devtamuno.unsplash.compose.ui.screen


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.ui.theme.UnsplashAPIComposeTheme

@Composable
fun DownloadImageAlertDialog(
    onDismissClicked: () -> Unit,
    onConfirmedClicked: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = White,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = onDismissClicked,
        confirmButton = {
            TextButton(onClick = onConfirmedClicked) {
                Text(text = stringResource(R.string.download))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissClicked) {
                Text(text = stringResource(R.string.dismiss))
            }
        },
        title = { Text(text = stringResource(R.string.download_image)) },
        text = {
            Text(
                text = stringResource(R.string.are_you_sure_you_want_to_download_this_image),
                textAlign = TextAlign.Start
            )
        }
    )
}

@ExperimentalFoundationApi
@Preview
@Composable
private fun PreviewDownloadImageAlertDialog() {
    UnsplashAPIComposeTheme {
        DownloadImageAlertDialog(
            onDismissClicked = { },
            onConfirmedClicked = {}
        )
    }
}
package ng.devtamuno.unsplash.compose.ui.dialog


import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.ui.theme.UnsplashAPIComposeTheme
import ng.devtamuno.unsplash.compose.ui.theme.appDark
import ng.devtamuno.unsplash.compose.ui.theme.appWhite

@Composable
fun DownloadImageAlertDialog(
    onDismissClicked: () -> Unit,
    onConfirmedClicked: () -> Unit,
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = appDark,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        title = { Text(text = stringResource(R.string.download_image), color = appWhite) },
        text = {
            Text(
                text = stringResource(R.string.are_you_sure_you_want_to_download_this_image),
                textAlign = TextAlign.Start,
                color = appWhite
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirmedClicked) {
                Text(text = stringResource(R.string.download), color = appWhite)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissClicked) {
                Text(text = stringResource(R.string.dismiss), color = appWhite)
            }
        },
        onDismissRequest = onDismissClicked
    )
}

@ExperimentalFoundationApi
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewDownloadImageAlertDialog() {
    UnsplashAPIComposeTheme {
        DownloadImageAlertDialog(
            onDismissClicked = { },
            onConfirmedClicked = {}
        )
    }
}
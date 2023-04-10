package ng.devtamuno.unsplash.compose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.ui.theme.appWhite


@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NavBar(modifier: Modifier = Modifier, onBackPressed: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().then(modifier)
    ) {

        IconButton(onClick = onBackPressed, modifier = Modifier.testTag("back_icon")) {
            Icon(
                painterResource(id = R.drawable.arrow_back),
                tint = appWhite,
                contentDescription = null,
            )
        }
        Text(
            text = stringResource(id = R.string.photo_detail),
            color = appWhite,
            fontSize = 17.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .testTag("toolbar_title")
                .padding(start = 24.dp)
        )
    }

}
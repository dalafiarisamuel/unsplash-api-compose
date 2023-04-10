package ng.devtamuno.unsplash.compose.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.devtamuno.unsplash.compose.ui.theme.appWhite

@Composable
fun IconText(@DrawableRes drawableId: Int, display: String) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = drawableId),
            tint = appWhite.copy(alpha = 0.6f),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(3.dp))
        Text(
            text = display,
            style = MaterialTheme.typography.caption.copy(fontSize = 10.sp, letterSpacing = 0.sp),
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
    }
}
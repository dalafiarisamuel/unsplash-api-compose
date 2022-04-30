package ng.devtamuno.unsplash.compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ng.devtamuno.unsplash.compose.R

@ExperimentalComposeUiApi
@Preview
@Composable
fun SearchButton(
    modifier: Modifier = Modifier,
    onSearchButtonClicked: (() -> Unit)? = null
) {
    IconButton(
        onClick = {
            onSearchButtonClicked?.invoke()
        },
        modifier = modifier
            .background(
                shape = RoundedCornerShape(15.dp),
                color = colorResource(
                    id = R.color.colorDisabledGray
                )
            )
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.colorGrayDivider),
                shape = RoundedCornerShape(15.dp)
            )
    ) {

        Image(
            modifier = Modifier
                .wrapContentSize(),
            painter = painterResource(id = R.drawable.ic_search),
            contentScale = ContentScale.Inside,
            contentDescription = null
        )
    }
}
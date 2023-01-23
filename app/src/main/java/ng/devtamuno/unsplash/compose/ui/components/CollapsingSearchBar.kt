package ng.devtamuno.unsplash.compose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.ui.theme.UnsplashAPIComposeTheme
import ng.devtamuno.unsplash.compose.ui.theme.appWhite


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun CollapsibleSearchBar(
    modifier: Modifier = Modifier,
    toolbarOffset: Float = 0f,
    toolbarHeight: Dp = 100.dp,
    minShrinkHeight: Dp = 0.dp,
    textValue: String? = null,
    keyboardAction: (() -> Unit)? = null,
    textValueChange: ((String) -> Unit)? = null
) {

    val keyboard = LocalSoftwareKeyboardController.current

    CollapsingToolbarBase(
        modifier = modifier.wrapContentSize(),
        toolbarHeight = toolbarHeight,
        toolbarOffset = toolbarOffset,
        minShrinkHeight = minShrinkHeight,
    ) {

        Column(
            modifier = Modifier
                .wrapContentHeight()
        ) {

            Text(
                text = stringResource(id = R.string.search_images),
                color = appWhite,
                fontSize = 13.sp,
                fontStyle = FontStyle.Normal,
                modifier = Modifier.padding(top = 25.dp)
            )

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
            ) {

                UnsplashSearchBox(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(weight = 3f)
                        .padding(end = 10.dp),
                    textValue = textValue,
                    keyboard = keyboard,
                    keyboardAction = keyboardAction
                ) {
                    textValueChange?.invoke(it)
                }

                SearchButton(
                    modifier = Modifier
                        .size(55.dp)
                        .weight(weight = 0.6f)

                ) {
                    keyboard?.hide()
                    keyboardAction?.invoke()
                }
            }
        }

    }
}

@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
private fun PreviewCollapsibleSearchBar(){
    UnsplashAPIComposeTheme{
        CollapsibleSearchBar()
    }
}
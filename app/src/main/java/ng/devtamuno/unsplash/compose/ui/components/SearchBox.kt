package ng.devtamuno.unsplash.compose.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ng.devtamuno.unsplash.compose.R
import ng.devtamuno.unsplash.compose.ui.theme.ColorMatteBlack
import ng.devtamuno.unsplash.compose.ui.theme.appDark
import ng.devtamuno.unsplash.compose.ui.theme.appWhite

@ExperimentalComposeUiApi
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun UnsplashSearchBox(
    modifier: Modifier = Modifier,
    textValue: String? = null,
    keyboard: SoftwareKeyboardController? = null,
    keyboardAction: (() -> Unit)? = null,
    textValueChange: ((String) -> Unit)? = null
) {

    Row(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(10.dp),
                color = colorResource(
                    id = R.color.colorDisabledGray
                )
            )
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.colorGrayDivider),
                shape = RoundedCornerShape(10.dp)
            )
            .wrapContentHeight()
    ) {

        TextField(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_image_hint),
                    color = Color.DarkGray,
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Normal
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = appWhite,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = textValue ?: "",
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardAction?.invoke()
                    keyboard?.hide()
                }),
            singleLine = true,
            onValueChange = {
                textValueChange?.invoke(it)
            },
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 21.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        )

    }

}
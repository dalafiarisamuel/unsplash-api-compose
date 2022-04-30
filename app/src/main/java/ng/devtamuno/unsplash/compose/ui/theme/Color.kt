package ng.devtamuno.unsplash.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color


//light theme colors
val ColorWhiteLight = Color(0xFFFFFFFF)
val ColorGreyLightLight = Color(0xFFD4D8EB)
val ColorMatteBlackLight = Color(0xFF212121)
val ColorDisabledGreyLight = Color(0xFFF5F5FD)
val ColorGrayDividerLight = Color(0xFFD4D8EB)


// dark theme colors
val ColorWhiteDark = Color(0xFF212121)
val ColorGreyLightDark = Color(0xFF212121)
val ColorMatteBlackDark = Color(0xFFFFFFFF)
val ColorDisabledGreyDark = Color(0xFFF5F5FD)
val ColorGrayDividerDark = Color(0xFFF5F5FD)

val String.color
    get() = Color(android.graphics.Color.parseColor(this))


val appWhite
    @Composable
    @ReadOnlyComposable
    get() = if (isSystemInDarkTheme()) ColorWhiteLight else ColorWhiteDark

val appDark
    @Composable
    @ReadOnlyComposable
    get() = if (isSystemInDarkTheme()) ColorMatteBlackDark else ColorWhiteLight

fun Color.complementary() = Color(
    red = 1F - red,
    green = 1F - green,
    blue = 1F - blue
)








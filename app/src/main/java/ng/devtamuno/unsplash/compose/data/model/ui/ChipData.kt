package ng.devtamuno.unsplash.compose.data.model.ui

import androidx.compose.runtime.Stable

@Stable
data class ChipData(val emoji: String, val chipText: String) {
    val displayText get() = "$emoji $chipText"
}
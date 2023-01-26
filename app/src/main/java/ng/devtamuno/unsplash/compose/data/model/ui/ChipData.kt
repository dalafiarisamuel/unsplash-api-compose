package ng.devtamuno.unsplash.compose.data.model.ui

data class ChipData(val emoji: String, val chipText: String) {
    val displayText get() = "$emoji $chipText"
}
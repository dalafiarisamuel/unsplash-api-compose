object DebugDependencies {

    private const val androidxFragmentTesting =
        "androidx.fragment:fragment-testing:${Versions.androidxFragmentKtx}"

    private const val composeUiTooling = "androidx.compose.ui:ui-tooling:1.1.0"

    val debugImplementation = listOf(androidxFragmentTesting, composeUiTooling)
}
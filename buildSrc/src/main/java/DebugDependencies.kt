object DebugDependencies {

    private const val androidxFragmentTesting =
        "androidx.fragment:fragment-testing:${Versions.androidxFragmentKtx}"

    val debugImplementation = listOf(androidxFragmentTesting)
}
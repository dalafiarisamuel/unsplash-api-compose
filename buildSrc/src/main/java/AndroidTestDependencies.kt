object AndroidTestDependencies {
    private const val androidxTestExt = "androidx.test.ext:junit-ktx:${Versions.androidxTestExt}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    private const val mockitoDexMaker =
        "com.linkedin.dexmaker:dexmaker-mockito:${Versions.mockitoDexMaker}"
    private const val googleTruth = "com.google.truth:truth:${Versions.googleTruth}"
    private const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    private const val kotlinxCouroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutineVersion}"
    private const val androidXcore = "androidx.arch.core:core-testing:${Versions.androidXcore}"
    private const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltVersion}"
    private const val espressoContrib =
        "androidx.test.espresso:espresso-contrib:${Versions.espressoContrib}"
    private const val okHttpMockServer =
        "com.squareup.okhttp3:mockwebserver:${Versions.okHttpMockServer}"

    val androidTestImplementation =
        listOf(
            androidxTestExt,
            espressoCore,
            mockitoDexMaker,
            googleTruth,
            mockito,
            kotlinxCouroutine,
            androidXcore,
            hiltTesting,
            espressoContrib,
            okHttpMockServer
        )
}
object AndroidTestDependencies {
    private const val androidxTestExt = "androidx.test.ext:junit-ktx:${Versions.androidxTestExt}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    private const val mockK = "io.mockk:mockk:${Versions.mockK}"
    private const val mockKJvm = "io.mockk:mockk-agent-jvm:${Versions.mockK}"
    private const val kotlinxCouroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutineVersion}"
    private const val androidXcore = "androidx.arch.core:core-testing:${Versions.androidXcore}"
    private const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltVersion}"
    private const val espressoContrib =
        "androidx.test.espresso:espresso-contrib:${Versions.espressoContrib}"
    private const val okHttpMockServer =
        "com.squareup.okhttp3:mockwebserver:${Versions.okHttpMockServer}"
    private const val jUnit = "androidx.test.ext:junit:1.1.3"
    private const val kotestFramework = "io.kotest:kotest-runner-junit5:${Versions.kotestVersion}"
    private const val kotestAssertions = "io.kotest:kotest-assertions-core:${Versions.kotestVersion}"
    private const val kotestProperty = "io.kotest:kotest-property:${Versions.kotestVersion}"

    object Compose {

        const val composeJunitTest = "androidx.compose.ui:ui-test-junit4:1.1.0"
    }

    val androidTestImplementation =
        listOf(
            androidxTestExt,
            espressoCore,
            mockK,
            mockKJvm,
            kotestFramework,
            kotestAssertions,
            kotestProperty,
            kotlinxCouroutine,
            androidXcore,
            hiltTesting,
            espressoContrib,
            okHttpMockServer,
            jUnit,
            Compose.composeJunitTest
        )
}
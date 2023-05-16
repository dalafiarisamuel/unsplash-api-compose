object TestDependencies {

    private const val junit4 = "junit:junit:${Versions.junit4Version}"
    private const val hamcrest = "org.hamcrest:hamcrest-all:${Versions.hamcrest}"
    private const val roboElectric = "org.robolectric:robolectric:${Versions.roboElectric}"
    private const val kotlinxCouroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutineVersion}"
    private const val mockK = "io.mockk:mockk:${Versions.mockK}"
    private const val mockKJvm = "io.mockk:mockk-agent-jvm:${Versions.mockK}"
    private const val okHttpMockServer =
        "com.squareup.okhttp3:mockwebserver:${Versions.okHttpMockServer}"
    private const val kotestFramework = "io.kotest:kotest-runner-junit5:${Versions.kotestVersion}"
    private const val kotestAssertions =
        "io.kotest:kotest-assertions-core:${Versions.kotestVersion}"
    private const val kotestProperty = "io.kotest:kotest-property:${Versions.kotestVersion}"
    private const val combine = "app.cash.turbine:turbine:${Versions.combineVersion}"


    val testImplementation =
        listOf(
            junit4,
            hamcrest,
            roboElectric,
            kotlinxCouroutine,
            kotestFramework,
            kotestAssertions,
            kotestProperty,
            mockK,
            mockKJvm,
            okHttpMockServer,
            combine
        )
}
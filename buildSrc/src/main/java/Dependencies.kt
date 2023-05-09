object Dependencies {
    private const val kotlinStandardLibrary =
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    private const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    private const val androidxAnnotation =
        "androidx.annotation:annotation:${Versions.androidxAnnotation}"
    private const val kotlinCoroutineAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutineVersion}"
    private const val kotlinCoroutineCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutineVersion}"
    private const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    private const val retrofitMoshiConverter =
        "com.squareup.retrofit2:converter-moshi:${Versions.retrofit2ConverterMoshi}"
    private const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshiKotlin}"
    private const val ioCardAndroidSdk = "io.card:android-sdk:${Versions.ioCard}"
    private const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    private const val javaxInject = "javax.inject:javax.inject:${Versions.javaxInject}"
    private const val hiltViewModel =
        "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hiltViewModel}"
    private const val hiltWorker = "androidx.hilt:hilt-work:${Versions.hiltWorker}"
    private const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"
    private const val workManagerSupport = "androidx.work:work-gcm:${Versions.workManager}"
    private const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    private const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    private const val retroMock = "co.infinum:retromock:${Versions.retroMock}"
    private const val androidLifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidLifecycle}"
    private const val lifecycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidLifecycle}"
    private const val lifecycleExtension =
        "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtension}"
    private const val androidxPagingRuntime =
        "androidx.paging:paging-runtime:${Versions.androidxPagingRuntime}"
    private const val savedState =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.savedState}"


    object ComposeDependencies {

        private const val composeUi = "androidx.compose.ui:ui:${Versions.composeVersion}"
        private const val composeMaterial =
            "androidx.compose.material:material:1.3.1"
        private const val composeRuntimeLiveData =
            "androidx.compose.runtime:runtime-livedata:${Versions.composeVersion}"
        private const val composeUiToolingPreview =
            "androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}"
        private const val composePaging = "androidx.paging:paging-compose:1.0.0-alpha14"
        private const val composeActivity = "androidx.activity:activity-compose:1.4.0"
        private const val composeViewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1"
        private const val coilCompose = "io.coil-kt:coil-compose:2.2.2"
        private const val composeNavigation = "androidx.navigation:navigation-compose:2.4.0"
        private const val composehiltNavigation = "androidx.hilt:hilt-navigation-compose:1.1.0-alpha01"
        private const val composeLifecyleExtension = "androidx.lifecycle:lifecycle-runtime-compose:2.6.1"
        private const val composeBlurHash = "com.github.dalafiarisamuel:composeblurhash:1.0.2"

        val implementations = listOf(
            composeUi,
            composeMaterial,
            composeUiToolingPreview,
            composePaging,
            composeActivity,
            composeViewModel,
            coilCompose,
            composeNavigation,
            composehiltNavigation,
            composeLifecyleExtension,
            composeBlurHash
        )

    }

    val implementations = listOf(
        kotlinStandardLibrary,
        ktx,
        androidxAnnotation,
        kotlinCoroutineAndroid,
        kotlinCoroutineCore,
        retrofit2,
        retrofitMoshiConverter,
        moshiKotlin,
        ioCardAndroidSdk,
        hiltAndroid,
        javaxInject,
        hiltWorker,
        workManager,
        workManagerSupport,
        loggingInterceptor,
        okHttp,
        retroMock,
        androidLifecycleRuntime,
        lifecycleViewModel,
        lifecycleExtension,
        androidxPagingRuntime,
        savedState,
    ) + ComposeDependencies.implementations
}
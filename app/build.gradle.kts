plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.android)
    kotlin(Plugins.kapt)
    id("name.remal.check-dependency-updates") version "1.2.2"
    id(Plugins.daggerHilt)
    id(Plugins.androidxNavigationsafeArgsKotlin)
    id(Plugins.kotlinParcelize)
}

android {
    compileSdk = Versions.compilesdk

    defaultConfig {
        applicationId = Application.id
        minSdk = Versions.minsdk
        targetSdk = Versions.targetsdk
        versionCode = Application.versionCode
        versionName = Application.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }

        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        targetCompatibility = Java.javaVersion
        sourceCompatibility = Java.javaVersion

    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.5"
        kotlinCompilerVersion = "1.5.31"
    }

    kotlinOptions {
        jvmTarget = Java.javaVersion.toString()
        useIR = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementAll(Dependencies.implementations)
    implementAll(SupportDependencies.supportImplementation)
    testImplementAll(TestDependencies.testImplementation)
    testAndroidImplementAll(AndroidTestDependencies.androidTestImplementation)
    kaptImplementAll(AnnotationProcessors.AnnotationProcessorsImplementation)
    kaptAndroidTestImplementAll(AnnotationProcessors.AnnotationProcessorsImplementation)
    debugImplementationAll(DebugDependencies.debugImplementation)
    implementation("androidx.compose.ui:ui:1.1.0")
    implementation("androidx.compose.material:material:1.1.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.1.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.0")
    implementation("androidx.paging:paging-compose:1.0.0-alpha14")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.1.0")
    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("androidx.navigation:navigation-compose:2.4.0")
    implementation("androidx.paging:paging-runtime:3.1.1")

    //viewmodel and livedata
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

}
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementAll(list: List<String>) {
    list.forEach {
        add("implementation", it)
    }
}

fun DependencyHandler.testImplementAll(list: List<String>) {
    list.forEach {
        add("testImplementation", it)
    }
}

fun DependencyHandler.testAndroidImplementAll(list: List<String>) {
    list.forEach {
        add("androidTestImplementation", it)
    }
}

fun DependencyHandler.kaptImplementAll(list: List<String>) {
    list.forEach {
        add("kapt", it)
    }
}

fun DependencyHandler.kaptAndroidTestImplementAll(list: List<String>) {
    list.forEach {
        add("kaptAndroidTest", it)
    }
}

fun DependencyHandler.debugImplementationAll(list: List<String>) {
    list.forEach {
        add("debugImplementation", it)
    }
}

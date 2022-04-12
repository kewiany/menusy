object BuildPlugin {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
}


object Library {

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.7.0"
        const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
        const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        const val activityCompose = "androidx.activity:activity-compose:1.3.1"
    }
}

object TestLibrary {

    object AndroidX {
        const val extJunit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val composeUi = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    }

    const val junit = "junit:junit:4.13.2"
}

object DebugLibrary {
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiTest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
}

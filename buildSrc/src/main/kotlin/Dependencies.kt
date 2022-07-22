object BuildPlugin {
    const val androidApplication = "com.android.application"
    const val androidLibrary = "com.android.library"
    const val kotlinAndroid = "org.jetbrains.kotlin.android"
    const val kotlinKapt = "kotlin-kapt"
    const val hilt = "dagger.hilt.android.plugin"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val koverPlugin = "org.jetbrains.kotlinx.kover"
}

object Library {

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
    const val gson = "com.google.code.gson:gson:2.9.0"

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.7.0"
        const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
        const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
        const val composeUiTooling = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
        const val dataStore = "androidx.datastore:datastore-preferences:1.0.0"
        const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        const val activityCompose = "androidx.activity:activity-compose:1.3.1"
        const val navigationRuntime = "androidx.navigation:navigation-runtime-ktx:2.4.2"
        const val navigationCompose = "androidx.navigation:navigation-compose:2.5.0-alpha04"
        const val hilt = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"
        const val hiltCompiler = "androidx.hilt:hilt-compiler:1.0.0-alpha03"
        const val room = "androidx.room:room-runtime:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    }
}

object TestLibrary {

    object AndroidX {
        const val extJunit = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
        const val composeUi = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    }

    const val junit = "junit:junit:4.13.2"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2"
    const val mockk = "io.mockk:mockk:1.12.4"
    const val turbine = "app.cash.turbine:turbine:0.8.0"
}

object DebugLibrary {
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiTest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
}

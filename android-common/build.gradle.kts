plugins {
    id(BuildPlugin.androidLibrary)
    id(BuildPlugin.kotlinAndroid)
    id(BuildPlugin.kotlinKapt)
    id(BuildPlugin.hilt)
}

android {
    compileSdk = AndroidSdkVersions.compile

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(project(":common"))
    implementation(Library.AndroidX.core)
    implementation(Library.AndroidX.lifecycle)
    implementation(Library.AndroidX.viewModel)
    implementation(Library.AndroidX.hiltNavigation)
    kapt(Library.AndroidX.hiltCompiler)
    api(Library.hilt)
    kapt(Library.hiltCompiler)
}

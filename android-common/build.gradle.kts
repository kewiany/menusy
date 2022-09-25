plugins {
    id(BuildPlugin.androidLibrary)
    id(BuildPlugin.kotlinAndroid)
    id(BuildPlugin.kotlinKapt)
    id(BuildPlugin.hilt)
}

android {
    compileSdk = AndroidSdkVersions.compile

    defaultConfig {
        minSdk = AndroidSdkVersions.min
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":model"))
    api(Library.AndroidX.core)
    api(Library.AndroidX.lifecycle)
    api(Library.AndroidX.composeUi)
    api(Library.AndroidX.composeMaterial)
    api(Library.AndroidX.composeUiTooling)

    implementation(Library.AndroidX.navigationCompose)

    api(Library.AndroidX.viewModel)
    implementation(Library.AndroidX.hiltNavigation)
    kapt(Library.AndroidX.hiltCompiler)
    api(Library.hilt)
    kapt(Library.hiltCompiler)
}

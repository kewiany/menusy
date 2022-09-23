plugins {
    id(BuildPlugin.androidLibrary)
    id(BuildPlugin.kotlinAndroid)
    id(BuildPlugin.kotlinKapt)
}

android {
    compileSdk = AndroidSdkVersions.compile

    defaultConfig {
        minSdk = AndroidSdkVersions.min
        targetSdk = AndroidSdkVersions.target
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
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":android-common"))

    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(Library.AndroidX.core)
    implementation(Library.AndroidX.composeUi)
    implementation(Library.AndroidX.composeMaterial)
    implementation(Library.AndroidX.composeUiTooling)
    implementation(Library.AndroidX.lifecycle)
    implementation(Library.AndroidX.activityCompose)

    kapt(Library.AndroidX.hiltCompiler)
    kapt(Library.hiltCompiler)

    testImplementation(project(":test-common"))
    testImplementation(TestLibrary.junit)
    testImplementation(TestLibrary.coroutines)
    testImplementation(TestLibrary.mockk)
    testImplementation(TestLibrary.turbine)
    androidTestImplementation(TestLibrary.AndroidX.runner)
    androidTestImplementation(TestLibrary.AndroidX.rules)
    androidTestImplementation(TestLibrary.AndroidX.extJunit)
    androidTestImplementation(TestLibrary.AndroidX.espresso)
    androidTestImplementation(TestLibrary.AndroidX.composeUi)
    androidTestImplementation(TestLibrary.cucumber)
    debugImplementation(DebugLibrary.composeUiTooling)
    debugImplementation(DebugLibrary.composeUiTest)
}
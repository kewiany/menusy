plugins {
    id(BuildPlugin.androidApplication)
    id(BuildPlugin.kotlinAndroid)
    id(BuildPlugin.kotlinKapt)
    id(BuildPlugin.hilt)
}

android {
    namespace = "xyz.kewiany.menusy"
    compileSdk = AndroidSdkVersions.compile
    defaultConfig {
        applicationId = "xyz.kewiany.menusy"
        minSdk = AndroidSdkVersions.min
        targetSdk = AndroidSdkVersions.target
        testApplicationId = "xyz.kewiany.menusy.test"
        testInstrumentationRunner = "xyz.kewiany.menusy.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
        freeCompilerArgs = listOf(
            "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xuse-experimental=androidx.compose.ui.ExperimentalComposeUiApi",
            "-Xjvm-default=compatibility"
        )
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
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(project(":feature-history"))
    implementation(project(":feature-language"))
    implementation(project(":feature-menu"))
    implementation(project(":feature-order"))
    implementation(project(":feature-search"))
    implementation(project(":feature-welcome"))

    implementation(Library.AndroidX.dataStore)
    implementation(Library.AndroidX.navigationRuntime)
    implementation(Library.AndroidX.navigationCompose)
    implementation(Library.AndroidX.hiltNavigation)
    implementation(Library.AndroidX.splashScreen)
    implementation(Library.AndroidX.room)
    annotationProcessor(Library.AndroidX.roomCompiler)
    kapt(Library.AndroidX.roomCompiler)
    implementation(Library.AndroidX.roomKtx)
    kapt(Library.AndroidX.hiltCompiler)
    implementation(Library.hilt)
    kapt(Library.hiltCompiler)
    implementation(Library.gson)
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
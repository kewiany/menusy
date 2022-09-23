plugins {
    id(BuildPlugin.androidLibrary)
    id(BuildPlugin.kotlinAndroid)
    id(BuildPlugin.kotlinAndroidExtensions)
    id(BuildPlugin.kotlinKapt)
}

android {
    compileSdk = AndroidSdkVersions.compile

    defaultConfig {
        minSdk = AndroidSdkVersions.min
        targetSdk = AndroidSdkVersions.target

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
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
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(Library.AndroidX.core)
    implementation(Library.AndroidX.dataStore)
    implementation(Library.AndroidX.room)
    annotationProcessor(Library.AndroidX.roomCompiler)
    kapt(Library.AndroidX.roomCompiler)
    implementation(Library.AndroidX.roomKtx)
    implementation(Library.gson)
    implementation(Library.slf4j)
    implementation(Library.javaxinject)
    testImplementation(project(":test-common"))
    testImplementation(TestLibrary.junit)
    testImplementation(TestLibrary.coroutines)
    testImplementation(TestLibrary.mockk)
    testImplementation(TestLibrary.turbine)
}

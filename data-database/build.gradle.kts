plugins {
    id(BuildPlugin.androidLibrary)
    id(BuildPlugin.kotlinAndroid)
    id(BuildPlugin.kotlinKapt)
}

android {
    compileSdk = AndroidSdkVersions.compile

    defaultConfig {
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
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(Library.AndroidX.room)
    implementation(Library.AndroidX.roomKtx)
    annotationProcessor(Library.AndroidX.roomCompiler)
    kapt(Library.AndroidX.roomCompiler)
}

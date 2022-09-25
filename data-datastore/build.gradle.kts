plugins {
    id(BuildPlugin.androidLibrary)
    id(BuildPlugin.kotlinAndroid)
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
    implementation(project(":domain"))

    implementation(Library.AndroidX.dataStore)
    implementation(Library.gson)
}

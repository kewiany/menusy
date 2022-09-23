plugins {
    id(BuildPlugin.androidLibrary)
    id(BuildPlugin.kotlinAndroid)
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
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    api(project(":data-database"))
    api(project(":data-datastore"))
    api(project(":data-network"))
    implementation(Library.AndroidX.core)
    testImplementation(project(":test-common"))
    testImplementation(TestLibrary.junit)
    testImplementation(TestLibrary.coroutines)
    testImplementation(TestLibrary.mockk)
    testImplementation(TestLibrary.turbine)
}

plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":common"))
    api(TestLibrary.junit)
    api(TestLibrary.coroutines)
    api(TestLibrary.mockk)
    api(TestLibrary.turbine)
}
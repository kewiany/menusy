plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":common"))
    api(project(":model"))
    testImplementation(project(":test-common"))
    testImplementation(TestLibrary.junit)
    testImplementation(TestLibrary.coroutines)
    testImplementation(TestLibrary.mockk)
    testImplementation(TestLibrary.turbine)
}

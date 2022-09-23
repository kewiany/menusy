plugins {
    kotlin("jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain"))
    implementation(Library.coroutines)
    implementation(Library.gson)
    implementation(Library.slf4j)
    implementation(Library.javaxinject)
}
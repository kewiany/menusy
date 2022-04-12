plugins {
    id(BuildPlugin.androidApplication) version "7.3.0-alpha01" apply false
    id(BuildPlugin.androidLibrary) version "7.3.0-alpha01" apply false
    id(BuildPlugin.kotlinAndroid) version Versions.kotlin apply false
}

tasks.register("clean").configure {
    delete("build")
}
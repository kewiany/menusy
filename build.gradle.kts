plugins {
    id(BuildPlugin.androidApplication) version "7.4.0-alpha08" apply false
    id(BuildPlugin.androidLibrary) version "7.3.0-alpha01" apply false
    id(BuildPlugin.kotlinAndroid) version Versions.kotlin apply false
    id(BuildPlugin.koverPlugin) version "0.4.3"
}

buildscript {
    dependencies {
        classpath(BuildPlugin.hiltPlugin)
    }
}

tasks.koverVerify {
    rule {
        name = "Minimal line coverage rate in percents"
        bound {
            minValue = 5
        }
    }
}

extensions.configure<kotlinx.kover.api.KoverExtension> {
    intellijEngineVersion.set("1.0.639")
    jacocoEngineVersion.set("0.8.7")
}
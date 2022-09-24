pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(":app")
include(":android-common")
include(":common")
include(":test-common")
include(":data")
include(":data-database")
include(":data-network")
include(":data-datastore")
include(":domain")
include(":model")
include(":feature-history")
include(":feature-menu")
include(":feature-order")
include(":feature-search")

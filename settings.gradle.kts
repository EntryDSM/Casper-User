rootProject.name = "Casper-User"

pluginManagement {
    includeBuild("casper-convention")
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

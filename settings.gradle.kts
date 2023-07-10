rootProject.name = "kotlinx-io-release-tests"

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

include(":gradle-bytestring-jvm")
include(":gradle-bytestring-multiplatform")
include(":gradle-core-jvm")
include(":gradle-core-multiplatform")

project(":gradle-bytestring-jvm").projectDir = file("./gradle-projects/bytestring-jvm")
project(":gradle-bytestring-multiplatform").projectDir = file("./gradle-projects/bytestring-multiplatform")
project(":gradle-core-jvm").projectDir = file("./gradle-projects/core-jvm")
project(":gradle-core-multiplatform").projectDir = file("./gradle-projects/core-multiplatform")

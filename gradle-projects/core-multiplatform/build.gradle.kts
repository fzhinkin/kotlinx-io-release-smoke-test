import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask

repositories {
    mavenCentral()
    mavenLocal()
}

plugins {
    kotlin("multiplatform")
}

val kotlinxIoVersion: String by project.parent!!

@OptIn(ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    jvm {
        jvmToolchain(8)
    }
    js(IR) {
        browser {
            testTask {
                useMocha()
            }
        }
        nodejs()
    }
    wasmWasi {
        nodejs()
    }
    wasmJs {
        nodejs()
        browser {
            testTask {
                useMocha()
            }
        }
    }

    androidNativeArm32()
    androidNativeArm64()
    androidNativeX64()
    androidNativeX86()

    iosArm64()
    iosX64()
    iosSimulatorArm64()

    watchosX64()
    watchosArm32()
    watchosDeviceArm64()
    watchosSimulatorArm64()
    watchosArm64()

    tvosArm64()
    tvosX64()
    tvosSimulatorArm64()

    iosSimulatorArm64()
    watchosSimulatorArm64()

    linuxArm64()
    linuxX64()

    macosArm64()
    macosX64()

    mingwX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-io-core:$kotlinxIoVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

rootProject.the<NodeJsRootExtension>().apply {
    nodeVersion = "21.0.0-v8-canary202310177990572111"
    nodeDownloadBaseUrl = "https://nodejs.org/download/v8-canary"
}

rootProject.tasks.withType<KotlinNpmInstallTask>().configureEach {
    args.add("--ignore-engines")
}

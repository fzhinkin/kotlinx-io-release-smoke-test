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
                useKarma {
                    useChromeHeadless()
                }
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
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }

    ios()
    watchos()
    tvos()

    iosSimulatorArm64()
    watchosSimulatorArm64()

    linuxArm64()
    macosArm64()
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

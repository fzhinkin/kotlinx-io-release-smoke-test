import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

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
    js(IR)
    wasmWasi()
    wasmJs()

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

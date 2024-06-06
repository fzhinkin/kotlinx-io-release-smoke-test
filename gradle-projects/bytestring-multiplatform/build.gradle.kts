import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
}

val kotlinxIoVersion: String by project.parent!!

@OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)
kotlin {
    targetHierarchy.default()

    jvmToolchain(8)

    jvm()
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
                implementation("org.jetbrains.kotlinx:kotlinx-io-bytestring:$kotlinxIoVersion")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

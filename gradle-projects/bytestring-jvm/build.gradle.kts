plugins {
    kotlin("jvm")
}

val kotlinxIoVersion: String by project.parent!!

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-io-bytestring:$kotlinxIoVersion")

    testImplementation(kotlin("test"))
}

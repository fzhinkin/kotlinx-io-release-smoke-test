import org.apache.tools.ant.taskdefs.condition.Os

buildscript {
    val kotlinVersion: String by project
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

apply(plugin = "kotlin")

repositories {
    mavenCentral()
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

tasks {
    val kotlinVersion: String by project
    val kotlinxIoVersion: String by project

    val verifyMavenProjects by registering(Exec::class) {
        executable = if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            "./mvnw.cmd"
        } else {
            "./mvnw"
        }
        workingDir = File(projectDir, "maven-projects")
        args = listOf("-DKOTLIN_VERSION=$kotlinVersion", "-DKOTLINX_IO_VERSION=$kotlinxIoVersion", "verify")
    }
    val cleanMavenProjects by registering(Exec::class) {
        executable = if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            "./mvnw.cmd"
        } else {
            "./mvnw"
        }
        workingDir = File(projectDir, "maven-projects")
        args = listOf("-DKOTLIN_VERSION=$kotlinVersion", "-DKOTLINX_IO_VERSION=$kotlinxIoVersion", "clean")

    }
    named("check").configure {
        dependsOn(verifyMavenProjects)
    }

    named("clean").configure {
        dependsOn(cleanMavenProjects)
    }
}

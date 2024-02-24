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
    val stagingRepository: String by project
    if (stagingRepository.isNotBlank()) {
        maven(stagingRepository)
    }
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        val stagingRepository: String by project
        if (stagingRepository.isNotBlank()) {
            maven(stagingRepository)
        }
    }
}

tasks {
    val kotlinVersion: String by project
    val kotlinxIoVersion: String by project
    val stagingRepository: String by project

    val verifyMavenProjects by registering(Exec::class) {
        executable = if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            "./mvnw.cmd"
        } else {
            "./mvnw"
        }
        workingDir = File(projectDir, "maven-projects")
        args = buildList {
            add("-DKOTLIN_VERSION=$kotlinVersion")
            add("-DKOTLINX_IO_VERSION=$kotlinxIoVersion")
            if (stagingRepository.isNotBlank()) {
                add("-DSTAGING_REPOSITORY_URL=$stagingRepository")
                add("-Pstaging")
            }
            add("verify")
        }
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

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

val stagingRepositoryId: String = project.findProperty("stagingRepository")?.toString()
    ?: throw IllegalArgumentException("stagingRepository property is missing")
val stagingRepository: String = "https://oss.sonatype.org/content/repositories/$stagingRepositoryId"
val kotlinxIoVersion: String = project.findProperty("kotlinxIoVersion")?.toString()
    ?: throw IllegalArgumentException("kotlinxIoVersion property is missing")

repositories {
    mavenCentral()
    if (stagingRepository.isNotBlank()) {
        maven(stagingRepository)
    }
}

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        if (stagingRepository.isNotBlank()) {
            maven(stagingRepository)
        }
    }
}

tasks {
    val kotlinVersion: String by project

    val verifyMavenProjects by registering(Exec::class) {
        workingDir = File(projectDir, "maven-projects")
        executable = workingDir.resolve(getMavenWrapperName()).absolutePath
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
        workingDir = File(projectDir, "maven-projects")
        executable = workingDir.resolve(getMavenWrapperName()).absolutePath
        args = listOf("-DKOTLIN_VERSION=$kotlinVersion", "-DKOTLINX_IO_VERSION=$kotlinxIoVersion", "clean")
    }
    named("check").configure {
        dependsOn(verifyMavenProjects)
    }

    named("clean").configure {
        dependsOn(cleanMavenProjects)
    }
}

fun getMavenWrapperName(): String =
    if (Os.isFamily(Os.FAMILY_WINDOWS)) {
        "mvnw.cmd"
    } else {
        "mvnw"
    }

import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(BuildPlugins.androidGradle)
        // classpath(BuildPlugins.jacocoAndroid)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id("com.github.ben-manes.versions") version Versions.BuildPlugins.gradleVersions
    id("org.jlleitschuh.gradle.ktlint") version Versions.BuildPlugins.ktlintGradle
    id("io.gitlab.arturbosch.detekt") version Versions.BuildPlugins.detekt
}

allprojects {
    repositories {
        jcenter()
        google()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint {
        version.set(Versions.BuildPlugins.ktlint)
        outputToConsole.set(true)
        disabledRules.set(setOf("no-wildcard-imports"))
    }
}

detekt {
    toolVersion = Versions.BuildPlugins.detekt
    input = files("$projectDir/src/main/kotlin")
}

tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
    resolutionStrategy {
        componentSelection {
            all {
                val quals = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea", "eap")
                val rejected = quals.any { qualifier ->
                    candidate.version.matches(Regex("(?i).*[.-]$qualifier[.\\d-+]*"))
                }
                if (rejected) {
                    reject("Release candidate")
                }
            }
        }
    }
}

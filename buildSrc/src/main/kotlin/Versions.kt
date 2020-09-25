object Versions {
    const val kotlin = "1.4.10"

    object App {
        private val githubRunId = (System.getenv("GITHUB_RUN_ID") ?: "1").toInt()
        private val githubRunNumber = (System.getenv("GITHUB_RUN_NUMBER") ?: "1").toInt()
        private val isBetaBuild = (System.getenv("track") ?: "") == "internal"
        private const val buildOffset = 143896282
        private val betaOffset = if (isBetaBuild) 100000000 else 0
        val code = githubRunNumber + buildOffset + betaOffset
        val name = "1.10.$code-gh$githubRunId"
    }

    object BuildPlugins {
        const val detekt = "1.13.1"
        const val gradleVersions = "0.33.0"
        const val jacocoAndroid = "0.2"
        const val ktlint = "0.39.0"
        const val ktlintGradle = "9.4.0"
    }

    object Libs {
        // AndroidX
        const val navigation = "2.3.0"
    }

    object Android {
        const val buildTools = "29.0.2"
        const val compileSdk = 29
        const val targetSdk = compileSdk
        const val minSdk = 14
    }
}

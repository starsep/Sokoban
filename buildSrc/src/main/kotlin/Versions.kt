object Versions {
    const val kotlin = "1.3.71"

    object App {
        val code = (System.getenv("GITHUB_RUN_ID") ?: "1").toInt()
        val name = "1.0.$code"
    }

    object BuildPlugins {
        const val androidGradle = "3.6.2"
        const val detekt = "1.7.2"
        const val gradleVersions = "0.28.0"
        const val jacocoAndroid = "0.1.4"
        const val ktlint = "0.36.0"
        const val ktlintGradle = "9.2.1"
    }

    object Libs {
        // AndroidX
        const val androidXAnnotations = "1.1.0"
        const val appCompat = "1.0.2"
        const val constraintLayout = "1.1.3"
        const val coreKtx = "1.0.2"
        const val material = "1.1.0"
        const val navigation = "2.2.1"

        // third party
        const val timber = "4.7.1"
    }

    object TestLibs {
        const val androidJunit = "1.1.1"
        const val espresso = "3.2.0"
        const val junit = "4.12"
        const val robolectric = "4.3.1"
    }

    object Android {
        const val buildTools = "29.0.2"
        const val compileSdk = 29
        const val targetSdk = compileSdk
        const val minSdk = 14
    }
}

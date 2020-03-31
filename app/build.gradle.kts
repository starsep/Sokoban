plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    // id("jacoco-android")
}

android {
    /*testOptions {
        unitTests {
            includeAndroidResources = true
        }
    }*/

    dexOptions {
        javaMaxHeapSize = "4g"
    }

    compileSdkVersion(Versions.Android.compileSdk)
    buildToolsVersion = Versions.Android.buildTools
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
        applicationId = "com.starsep.sokoban.release"
        minSdkVersion(Versions.Android.minSdk)
        targetSdkVersion(Versions.Android.targetSdk)
        versionCode = Versions.App.code
        versionName = Versions.App.name
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            // testCoverageEnabled true
        }
        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    if (System.getenv("KEYSTORE_LOCATION") != null) {
        signingConfigs.create("release") {
            storeFile = file(System.getenv("KEYSTORE_LOCATION"))
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
        buildTypes.getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

// apply(from="jacoco.gradle")
apply(from="dependencies.gradle.kts")

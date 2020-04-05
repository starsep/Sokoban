plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
//    id("androidx.navigation.safeargs.kotlin")
    // id("jacoco-android")
}

apply(plugin = "androidx.navigation.safeargs.kotlin")

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
    /*sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
        getByName("androidTest").assets.srcDirs("$projectDir/schemas")
    }*/
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
        create("beta") {
            initWith(getByName("release"))
            versionNameSuffix = "-beta"
        }
    }
    if (System.getenv("KEYSTORE_LOCATION") != null) {
        signingConfigs.create("release") {
            storeFile = file(System.getenv("KEYSTORE_LOCATION"))
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEY_PASSWORD")
        }
        buildTypes.getByName("beta") {
            signingConfig = signingConfigs.getByName("release")
        }
        buildTypes.getByName("release") {
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions.apply {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

// apply(from="jacoco.gradle")
apply(from = "dependencies.gradle.kts")

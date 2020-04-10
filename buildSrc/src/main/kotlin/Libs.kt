object Libs {
    // Kotlin
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"

    // AndroidX
    const val appCompat = "androidx.appcompat:appcompat:${Versions.Libs.appCompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.Libs.constraintLayout}"
    const val androidXAnnotations = "androidx.annotation:annotation:${Versions.Libs.androidXAnnotations}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Libs.lifecycle}"
    const val preference = "androidx.preference:preference-ktx:${Versions.Libs.preference}"
    const val room = "androidx.room:room-runtime:${Versions.Libs.room}"
    const val roomKapt = "androidx.room:room-compiler:${Versions.Libs.room}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.Libs.navigation}"
    const val navigationUIKtx = "androidx.navigation:navigation-ui-ktx:${Versions.Libs.navigation}"

    // Google
    const val material =  "com.google.android.material:material:${Versions.Libs.material}"
    const val playServicesGames = "com.google.android.gms:play-services-games:${Versions.Libs.playServicesGames}"

    // other
    const val gson = "com.google.code.gson:gson:2.8.6"
    const val koin = "org.koin:koin-android:${Versions.Libs.koin}"
    const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.Libs.koin}"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.Libs.leakCanary}"
    const val timber = "com.jakewharton.timber:timber:${Versions.Libs.timber}"
}

object BuildPlugins {
    const val androidGradle =
        "com.android.tools.build:gradle:${Versions.BuildPlugins.androidGradle}"
    const val jacocoAndroid =
        "com.dicedmelon.gradle:jacoco-android:${Versions.BuildPlugins.jacocoAndroid}"
    const val navigation =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Libs.navigation}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.TestLibs.junit}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
}

object AndroidTestLibs {
    const val coreArch = "androidx.arch.core:core-testing:2.1.0"
    const val androidJunit = "androidx.test.ext:junit:${Versions.TestLibs.androidJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.TestLibs.espresso}"
    const val robolectric = "org.robolectric:robolectric:${Versions.TestLibs.robolectric}"
}

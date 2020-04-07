object Libs {
    const val gson = "com.google.code.gson:gson:2.8.6"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
}

object AndroidLibs {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.Libs.appCompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.Libs.constraintLayout}"
    const val material =  "com.google.android.material:material:${Versions.Libs.material}"
    const val androidXAnnotations = "androidx.annotation:annotation:${Versions.Libs.androidXAnnotations}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Libs.lifecycle}"
    const val timber = "com.jakewharton.timber:timber:${Versions.Libs.timber}"
    const val playServicesGames = "com.google.android.gms:play-services-games:${Versions.Libs.playServicesGames}"
    const val preference = "androidx.preference:preference-ktx:${Versions.Libs.preference}"
    const val room = "androidx.room:room-runtime:${Versions.Libs.room}"
    const val roomKapt = "androidx.room:room-compiler:${Versions.Libs.room}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.Libs.navigation}"
    const val navigationUIKtx = "androidx.navigation:navigation-ui-ktx:${Versions.Libs.navigation}"
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
    const val mockito = "org.mockito:mockito-core:1.10.19"
    const val powerMock = "org.powermock:powermock-api-mockito:1.6.2"
    const val powerMockJunit = "org.powermock:powermock-module-junit4:1.6.2"
}

object AndroidTestLibs {
    const val androidJunit = "androidx.test.ext:junit:${Versions.TestLibs.androidJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.TestLibs.espresso}"
    const val robolectric = "org.robolectric:robolectric:${Versions.TestLibs.robolectric}"
}

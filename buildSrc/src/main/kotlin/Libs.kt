object Libs {
    const val gson = "com.google.code.gson:gson:2.8.6"
    const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
}

object AndroidLibs {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.Libs.appCompat}"
    const val material =  "com.google.android.material:material:${Versions.Libs.material}"
    const val androidXAnnotations = "androidx.annotation:annotation:${Versions.Libs.androidXAnnotations}"
    const val lifecycle = "android.arch.lifecycle:extensions:1.1.1"
    const val timber = "com.jakewharton.timber:timber:${Versions.Libs.timber}"
    const val playServicesGames = "com.google.android.gms:play-services-games:15.0.1"
    const val room = "android.arch.persistence.room:runtime:1.1.1"
    const val roomKapt = "android.arch.persistence.room:compiler:1.1.1"
}

object BuildPlugins {
    const val androidGradle =
        "com.android.tools.build:gradle:${Versions.BuildPlugins.androidGradle}"
    const val jacocoAndroid =
        "com.dicedmelon.gradle:jacoco-android:${Versions.BuildPlugins.jacocoAndroid}"
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

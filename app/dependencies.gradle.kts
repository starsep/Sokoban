val implementation by configurations
val kapt by configurations
val testImplementation by configurations
val debugImplementation by configurations

dependencies {
    implementation(Libs.kotlinStdlib)
    implementation(Libs.lifecycleViewModel)
    implementation(Libs.lifecycleScope)
    implementation(Libs.room)
    implementation(Libs.roomKtx)
    implementation(Libs.appCompat)
    implementation(Libs.constraintLayout)
    implementation(Libs.material)
    implementation(Libs.androidXAnnotations)
    implementation(Libs.playServicesGames)
    implementation(Libs.navigationFragmentKtx)
    implementation(Libs.navigationUIKtx)
    implementation(Libs.preference)
    implementation(Libs.gson)
    implementation(Libs.timber)
    implementation(Libs.koin)
    implementation(Libs.koinViewModel)
    debugImplementation(Libs.leakCanary)

    kapt(Libs.roomKapt)

    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.mockitoKotlin)
    testImplementation(AndroidTestLibs.robolectric)
    testImplementation(AndroidTestLibs.coreArch)
}

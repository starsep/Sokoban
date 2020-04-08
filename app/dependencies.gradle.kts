val implementation by configurations
val kapt by configurations
val testImplementation by configurations

dependencies {
    implementation(Libs.kotlinStdlib)
    implementation(Libs.lifecycleViewModel)
    implementation(Libs.room)
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

    kapt(Libs.roomKapt)

    testImplementation(TestLibs.junit)
    testImplementation(TestLibs.mockito)
    testImplementation(TestLibs.powerMock) {
        exclude(module = "hamcrest-core")
        exclude(module = "objenesis-core")
    }
    testImplementation(TestLibs.powerMockJunit) {
        exclude(module = "hamcrest-core")
        exclude(module = "objenesis-core")
    }
    testImplementation(AndroidTestLibs.robolectric)
}

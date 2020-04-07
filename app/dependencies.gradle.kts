val implementation by configurations
val kapt by configurations
val testImplementation by configurations

dependencies {
    implementation(Libs.kotlinStdlib)
    implementation(AndroidLibs.lifecycleViewModel)
    implementation(AndroidLibs.room)
    implementation(AndroidLibs.appCompat)
    implementation(AndroidLibs.constraintLayout)
    implementation(AndroidLibs.material)
    implementation(AndroidLibs.androidXAnnotations)
    implementation(AndroidLibs.playServicesGames)
//    implementation(AndroidLibs.navigationRuntimeKtx)
    implementation(AndroidLibs.navigationFragmentKtx)
    implementation(AndroidLibs.navigationUIKtx)
    implementation(AndroidLibs.preference)
    implementation(Libs.gson)
    implementation(AndroidLibs.timber)

    kapt(AndroidLibs.roomKapt)

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

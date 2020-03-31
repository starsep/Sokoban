val implementation by configurations
val kapt by configurations
val testImplementation by configurations

dependencies {
    implementation(Libs.kotlinStdlib)
    implementation(AndroidLibs.lifecycle)
    implementation(AndroidLibs.room)
    implementation(AndroidLibs.appCompat)
    implementation(AndroidLibs.supportDesign)
    implementation(AndroidLibs.supportAnnotations)
    implementation(AndroidLibs.playServicesGames)
    implementation(Libs.gson)

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
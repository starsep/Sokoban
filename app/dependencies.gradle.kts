val implementation by configurations
val kapt by configurations
val testImplementation by configurations
val debugImplementation by configurations

dependencies {
    implementation(AndroidX.multidex)
    implementation(AndroidX.lifecycle.viewModelKtx)
    implementation(AndroidX.lifecycle.runtimeKtx)
    implementation(AndroidX.room.runtime)
    implementation(AndroidX.room.ktx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
    implementation(Google.android.material)
    implementation(AndroidX.annotation)
    implementation(Google.android.playServices.games)
    implementation(AndroidX.navigation.fragmentKtx)
    implementation(AndroidX.navigation.uiKtx)
    implementation(AndroidX.preferenceKtx)
    implementation("com.google.code.gson:gson:_")
    implementation(JakeWharton.timber)
    implementation("org.koin:koin-android:_")
    implementation("org.koin:koin-androidx-viewmodel:_")
    debugImplementation(Square.leakCanary.android)

    kapt(AndroidX.room.compiler)

    testImplementation(Testing.junit4)
    testImplementation(Testing.mockito.kotlin)
    testImplementation(Testing.roboElectric)
    testImplementation(AndroidX.archCore.testing)
}

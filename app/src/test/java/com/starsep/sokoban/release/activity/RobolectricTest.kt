package com.starsep.sokoban.release.activity

import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Build.VERSION_CODES.P
import org.robolectric.annotation.Config

@Config(sdk = [LOLLIPOP, P], manifest = "AndroidManifest.xml")
abstract class RobolectricTest

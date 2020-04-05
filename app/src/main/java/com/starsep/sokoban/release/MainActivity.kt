package com.starsep.sokoban.release

import android.app.ActivityManager
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (taskDescription == null) {
                val launcherIcon = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
                taskDescription = ActivityManager.TaskDescription(
                    getString(R.string.app_name),
                    launcherIcon,
                    ContextCompat.getColor(this, R.color.colorPrimary)
                )
            }
            setTaskDescription(taskDescription)
        }
        setContentView(R.layout.activity_main_menu)
    }

    companion object {
        private var taskDescription: ActivityManager.TaskDescription? = null
    }
}

package com.starsep.sokoban.release

import android.app.Application
import com.starsep.sokoban.release.database.Database
import timber.log.Timber

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupDatabase()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupDatabase() {
        Database.initDb(this)
    }
}
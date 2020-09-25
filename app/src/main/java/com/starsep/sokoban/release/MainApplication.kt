package com.starsep.sokoban.release

import androidx.multidex.MultiDexApplication
import com.starsep.sokoban.release.database.Database
import com.starsep.sokoban.release.model.GameModel
import com.starsep.sokoban.release.res.Textures
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MainApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupKoin()
        setupDatabase()
        setupTextures()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupDatabase() {
        Database.initDb(this)
    }

    private fun setupKoin() {
        val gameModule = module {
            viewModel { GameModel(get()) }
        }
        startKoin {
            androidContext(this@MainApplication)
            modules(gameModule)
        }
    }

    private fun setupTextures() {
        Textures.init(this)
    }
}

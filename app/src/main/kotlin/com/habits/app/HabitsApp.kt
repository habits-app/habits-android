package com.habits.app

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.habits.app.core.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class HabitsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        AndroidThreeTen.init(this)

        startKoin {
            androidContext(this@HabitsApp)
            androidLogger(Level.INFO)
            modules(appModules + coreModule)
        }
    }
}

package com.newagedavid.climifyapp

import android.app.Application
import com.newagedavid.climifyapp.di.appModule
import com.newagedavid.climifyapp.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber


class ClimifyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@ClimifyApp)
            modules(databaseModule, appModule)
        }
    }
}

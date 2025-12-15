package com.example.offlinetest

import android.app.Application
import com.example.offlinetest.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OfflineTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@OfflineTestApp)
            modules(appModule)
        }
    }
}

package com.shevelev.mywinningstreaks

import android.app.Application
import com.shevelev.storage.di.StorageModuleCommon
import com.shevelev.storage.di.StorageModuleAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyWinningStreaksApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyWinningStreaksApp)
            modules(
                StorageModuleCommon,
                StorageModuleAndroid,
            )
        }
    }
}
package com.shevelev.mywinningstreaks

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.shevelev.mywinningstreaks.di.AppModuleAndroid
import com.shevelev.mywinningstreaks.di.AppModuleCommon
import com.shevelev.mywinningstreaks.storage.di.StorageModuleAndroid
import com.shevelev.mywinningstreaks.storage.di.StorageModuleCommon
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyWinningStreaksApp : Application(), LifecycleEventObserver {
    @Volatile
    private var _isInForeground = false
    val isInForeground: Boolean
        get() = _isInForeground

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyWinningStreaksApp)
            modules(
                AppModuleAndroid,
                AppModuleCommon,
                StorageModuleCommon,
                StorageModuleAndroid,
            )
        }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) {
            _isInForeground = true
        } else if (event == Lifecycle.Event.ON_STOP) {
            _isInForeground = false
        }
    }
}
package com.shevelev.storage.di

import com.shevelev.storage.settings.Settings
import com.shevelev.storage.settings.SettingsImpl
import org.koin.dsl.module

val StorageModule = module {
    single<Settings> {
        SettingsImpl(
            factory = get(),
        )
    }
}

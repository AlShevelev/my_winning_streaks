package com.shevelev.storage.di

import com.shevelev.storage.settings.factory.SettingsFactory
import com.shevelev.storage.settings.factory.SettingsFactoryImpl
import org.koin.dsl.module

val StorageModuleAndroid = module {
    single<SettingsFactory> {
        SettingsFactoryImpl(
            context = get(),
        )
    }
}
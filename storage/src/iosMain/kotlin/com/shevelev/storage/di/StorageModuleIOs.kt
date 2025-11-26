package com.shevelev.storage.di

import com.shevelev.storage.database.factory.DatabaseDriverFactory
import com.shevelev.storage.database.factory.DatabaseDriverFactoryImpl
import com.shevelev.storage.settings.factory.SettingsFactory
import com.shevelev.storage.settings.factory.SettingsFactoryImpl
import org.koin.dsl.module

val StorageModuleIOs = module {
    single<SettingsFactory> {
        SettingsFactoryImpl()
    }

    single<DatabaseDriverFactory> {
        DatabaseDriverFactoryImpl()
    }
}
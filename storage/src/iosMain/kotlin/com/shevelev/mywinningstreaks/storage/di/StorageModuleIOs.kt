package com.shevelev.mywinningstreaks.storage.di

import com.shevelev.mywinningstreaks.storage.database.factory.DatabaseDriverFactory
import com.shevelev.mywinningstreaks.storage.database.factory.DatabaseDriverFactoryImpl
import com.shevelev.mywinningstreaks.storage.settings.factory.SettingsFactory
import com.shevelev.mywinningstreaks.storage.settings.factory.SettingsFactoryImpl
import org.koin.dsl.module

val StorageModuleIOs = module {
    single<SettingsFactory> {
        SettingsFactoryImpl()
    }

    single<DatabaseDriverFactory> {
        DatabaseDriverFactoryImpl()
    }
}
package com.shevelev.storage.di

import com.shevelev.storage.database.DatabaseRepository
import com.shevelev.storage.database.DatabaseRepositoryImpl
import com.shevelev.storage.settings.Settings
import com.shevelev.storage.settings.SettingsImpl
import org.koin.dsl.module

val StorageModuleCommon = module {
    single<Settings> {
        SettingsImpl(
            factory = get(),
        )
    }

    single<DatabaseRepository> {
        DatabaseRepositoryImpl(
            driverFactory = get(),
        )
    }
}

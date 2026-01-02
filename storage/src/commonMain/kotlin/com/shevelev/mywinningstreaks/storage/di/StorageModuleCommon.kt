package com.shevelev.mywinningstreaks.storage.di

import com.shevelev.mywinningstreaks.storage.database.DatabaseRepository
import com.shevelev.mywinningstreaks.storage.database.DatabaseRepositoryImpl
import com.shevelev.mywinningstreaks.storage.settings.Settings
import com.shevelev.mywinningstreaks.storage.settings.SettingsImpl
import com.shevelev.mywinningstreaks.storage.settings.SettingsRepository
import com.shevelev.mywinningstreaks.storage.settings.SettingsRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val StorageModuleCommon = module {
    single<Settings> {
        SettingsImpl(
            factory = get(),
        )
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(
                settings = get()
        )
    }

    single<DatabaseRepository> {
        DatabaseRepositoryImpl(
            driverFactory = get(),
            dispatcher = Dispatchers.IO,
        )
    }
}

package com.shevelev.mywinningstreaks.storage.database.factory

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.shevelev.mywinningstreaks.storage.api.database.AppStorageDatabase

internal class DatabaseDriverFactoryImpl : DatabaseDriverFactory {
    override fun createDriver() = NativeSqliteDriver(AppStorageDatabase.Schema, "app_storage.db")
}
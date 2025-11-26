package com.shevelev.storage.database.factory

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.shevelev.storage.api.database.AppStorageDatabase

internal class DatabaseDriverFactoryImpl : DatabaseDriverFactory {
    override fun createDriver() = NativeSqliteDriver(AppStorageDatabase.Schema, "app_storage.db")
}
package com.shevelev.storage.database.factory

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.shevelev.storage.api.database.AppStorageDatabase

internal class DatabaseDriverFactoryImpl(private val context: Context) : DatabaseDriverFactory {
    override fun createDriver(): SqlDriver =
        AndroidSqliteDriver(AppStorageDatabase.Schema, context, "app_storage.db")
}
package com.shevelev.mywinningstreaks.storage.database.factory

import app.cash.sqldelight.db.SqlDriver

internal interface DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
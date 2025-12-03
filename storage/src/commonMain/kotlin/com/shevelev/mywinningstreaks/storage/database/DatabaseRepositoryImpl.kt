package com.shevelev.mywinningstreaks.storage.database

import com.shevelev.mywinningstreaks.storage.database.factory.DatabaseDriverFactory

internal class DatabaseRepositoryImpl(
    private var driverFactory: DatabaseDriverFactory
) : DatabaseRepository {
    private val database by lazy { driverFactory.createDriver() }

}
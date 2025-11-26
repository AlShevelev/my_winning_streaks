package com.shevelev.storage.database

import com.shevelev.storage.database.factory.DatabaseDriverFactory

internal class DatabaseRepositoryImpl(
    private var driverFactory: DatabaseDriverFactory
) : DatabaseRepository {
    private val database by lazy { driverFactory.createDriver() }

}
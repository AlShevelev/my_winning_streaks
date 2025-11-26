package com.shevelev.storage.settings.factory

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

internal interface SettingsFactory {
    fun create(): DataStore<Preferences>
}

internal abstract class SettingsFactoryBase : SettingsFactory {
    abstract override fun create(): DataStore<Preferences>

    protected fun createDataStore(producePath: () -> String): DataStore<Preferences> =
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { producePath().toPath() }
        )

    companion object {
        protected const val DATA_STORE_FILE_NAME = "mws.preferences_pb"
    }
}


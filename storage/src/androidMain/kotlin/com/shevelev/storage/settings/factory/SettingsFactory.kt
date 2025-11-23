package com.shevelev.storage.settings.factory

import android.content.Context

internal class SettingsFactoryImpl(private val context: Context) : SettingsFactory {
    override fun create() = createDataStore(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )
}
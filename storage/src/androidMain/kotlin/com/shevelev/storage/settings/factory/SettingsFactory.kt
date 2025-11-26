package com.shevelev.storage.settings.factory

import android.content.Context

internal class SettingsFactoryImpl(private val context: Context) : SettingsFactoryBase() {
    override fun create() = createDataStore(
        producePath = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath }
    )
}
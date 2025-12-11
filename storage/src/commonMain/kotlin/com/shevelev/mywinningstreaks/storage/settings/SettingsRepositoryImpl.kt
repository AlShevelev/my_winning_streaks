package com.shevelev.mywinningstreaks.storage.settings

import kotlinx.coroutines.CoroutineDispatcher

internal class SettingsRepositoryImpl(
    private val dispatcher: CoroutineDispatcher,
    private val settings: Settings,
) : SettingsRepository {
    override suspend fun getDaysToShow(): Int =
        settings.readInt(DAYS_TO_SHOW_KEY) ?: DAYS_TO_SHOW_DEFAULT_VALUE

    companion object {
        private const val DAYS_TO_SHOW_KEY = "DAYS_TO_SHOW"
        private const val DAYS_TO_SHOW_DEFAULT_VALUE = 30
    }
}
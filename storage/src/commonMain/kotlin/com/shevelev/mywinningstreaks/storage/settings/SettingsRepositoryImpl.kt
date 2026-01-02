package com.shevelev.mywinningstreaks.storage.settings

internal class SettingsRepositoryImpl(
    private val settings: Settings,
) : SettingsRepository {
    override val defaultDaysToShow: Int
        get() = DAYS_TO_SHOW_DEFAULT_VALUE

    override suspend fun getDaysToShow(): Int =
        settings.readInt(DAYS_TO_SHOW_KEY) ?: DAYS_TO_SHOW_DEFAULT_VALUE

    override suspend fun setDaysToShow(value: Int) {
        settings.putInt(DAYS_TO_SHOW_KEY, value)
    }

    companion object {
        private const val DAYS_TO_SHOW_KEY = "DAYS_TO_SHOW"
        private const val DAYS_TO_SHOW_DEFAULT_VALUE = 30
    }
}
package com.shevelev.mywinningstreaks.storage.settings

import com.shevelev.mywinningstreaks.coreentities.utils.DateTimeUtils
import kotlinx.datetime.LocalTime

internal class SettingsRepositoryImpl(
    private val settings: Settings,
) : SettingsRepository {
    override suspend fun getDaysToShow(): Int =
        settings.readInt(DAYS_TO_SHOW_KEY) ?: DAYS_TO_SHOW_DEFAULT_VALUE

    override suspend fun setDaysToShow(value: Int) = settings.putInt(DAYS_TO_SHOW_KEY, value)

    override suspend fun getTimeToNotify(): LocalTime =
        settings.readString(TIME_TO_NOTIFY_KEY)?.let { DateTimeUtils.stringToLocalTime(it) }
            ?: TIME_TO_NOTIFY_KEY_DEFAULT_VALUE

    override suspend fun setTimeToNotify(value: LocalTime) =
        settings.putString(TIME_TO_NOTIFY_KEY, DateTimeUtils.localTimeToString(value))

    companion object {
        private const val DAYS_TO_SHOW_KEY = "DAYS_TO_SHOW"
        private const val DAYS_TO_SHOW_DEFAULT_VALUE = 30

        private const val TIME_TO_NOTIFY_KEY = "TIME_TO_NOTIFY"
        private val TIME_TO_NOTIFY_KEY_DEFAULT_VALUE = LocalTime(8, 0)
    }
}
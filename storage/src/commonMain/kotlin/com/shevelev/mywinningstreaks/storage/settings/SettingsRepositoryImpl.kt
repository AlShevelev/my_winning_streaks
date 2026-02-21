package com.shevelev.mywinningstreaks.storage.settings

import com.shevelev.mywinningstreaks.coreentities.utils.DateTimeUtils
import kotlinx.datetime.LocalTime

internal class SettingsRepositoryImpl(
    private val settings: Settings,
) : SettingsRepository {
    override suspend fun getDaysToShow(): Int =
        settings.readInt(DAYS_TO_SHOW_KEY) ?: DAYS_TO_SHOW_DEFAULT_VALUE

    override suspend fun setDaysToShow(value: Int) = settings.putInt(DAYS_TO_SHOW_KEY, value)

    override suspend fun getTimeToStart(): LocalTime =
        settings.readString(TIME_TO_FAIL_KEY)?.let { DateTimeUtils.stringToLocalTime(it) }
            ?: TIME_TO_START_KEY_DEFAULT_VALUE

    override suspend fun setTimeToFail(value: LocalTime) =
        settings.putString(TIME_TO_FAIL_KEY, DateTimeUtils.localTimeToString(value))

    override suspend fun getHowOften(): Int =
        settings.readInt(HOW_OFTEN_KEY) ?: HOW_OFTEN_DEFAULT_VALUE

    override suspend fun setHowOften(value: Int) = settings.putInt(HOW_OFTEN_KEY, value)

    override suspend fun getHowManyTimes(): Int =
        settings.readInt(HOW_MANY_TIMES_KEY) ?: HOW_MANY_TIMES_DEFAULT_VALUE

    override suspend fun setHowManyTimes(value: Int) = settings.putInt(HOW_MANY_TIMES_KEY, value)

    companion object {
        private const val DAYS_TO_SHOW_KEY = "DAYS_TO_SHOW"
        private const val DAYS_TO_SHOW_DEFAULT_VALUE = 30

        private const val TIME_TO_FAIL_KEY = "TIME_TO_FAIL"
        private val TIME_TO_START_KEY_DEFAULT_VALUE = LocalTime(8, 0)

        private const val HOW_OFTEN_KEY = "HOW_OFTEN"
        private const val HOW_OFTEN_DEFAULT_VALUE = 5

        private const val HOW_MANY_TIMES_KEY = "HOW_MANY_TIMES"
        private const val HOW_MANY_TIMES_DEFAULT_VALUE = 1
    }
}
package com.shevelev.mywinningstreaks.storage.settings

import kotlinx.datetime.LocalTime

interface SettingsRepository {
    suspend fun getDaysToShow(): Int

    suspend fun setDaysToShow(value: Int)

    suspend fun getTimeToStart(): LocalTime

    suspend fun setTimeToFail(value: LocalTime)

    suspend fun getHowOften(): Int

    suspend fun setHowOften(value: Int)

    suspend fun getHowManyTimes(): Int

    suspend fun setHowManyTimes(value: Int)
}

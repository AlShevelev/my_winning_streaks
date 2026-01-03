package com.shevelev.mywinningstreaks.storage.settings

import kotlinx.datetime.LocalTime

interface SettingsRepository {
    val defaultDaysToShow: Int

    val defaultTimeToStart: LocalTime

    val defaultHowOften: Int

    val defaultHowManyTimes: Int

    suspend fun getDaysToShow(): Int

    suspend fun setDaysToShow(value: Int)

    suspend fun getTimeToStart(): LocalTime

    suspend fun setTimeToStart(value: LocalTime)

    suspend fun getHowOften(): Int

    suspend fun setHowOften(value: Int)

    suspend fun getHowManyTimes(): Int

    suspend fun setHowManyTimes(value: Int)
}

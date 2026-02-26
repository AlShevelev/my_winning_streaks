package com.shevelev.mywinningstreaks.storage.settings

import kotlinx.datetime.LocalTime

interface SettingsRepository {
    suspend fun getDaysToShow(): Int

    suspend fun setDaysToShow(value: Int)

    suspend fun getTimeToNotify(): LocalTime

    suspend fun setTimeToNotify(value: LocalTime)
}

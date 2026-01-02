package com.shevelev.mywinningstreaks.storage.settings

interface SettingsRepository {
    val defaultDaysToShow: Int

    suspend fun getDaysToShow(): Int

    suspend fun setDaysToShow(value: Int)
}
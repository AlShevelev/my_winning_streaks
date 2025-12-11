package com.shevelev.mywinningstreaks.storage.settings

interface SettingsRepository {
    suspend fun getDaysToShow(): Int
}
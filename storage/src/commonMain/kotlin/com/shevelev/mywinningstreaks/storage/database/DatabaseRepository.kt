package com.shevelev.mywinningstreaks.storage.database

import com.shevelev.mywinningstreaks.storage.database.dto.Streak

interface DatabaseRepository {
    suspend fun getAllStreaks(): List<Streak>

    suspend fun addStreak(streak: Streak)

    suspend fun updateStreakTitle(id: Long, title: String)

    suspend fun deleteStreak(id: Long)
}
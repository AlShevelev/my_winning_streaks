package com.shevelev.mywinningstreaks.storage.database

import com.shevelev.mywinningstreaks.storage.database.dto.Streak

interface DatabaseRepository {
    suspend fun getAllStreaks(): List<Streak>
}
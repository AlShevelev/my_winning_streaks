package com.shevelev.mywinningstreaks.storage.database

import com.shevelev.mywinningstreaks.storage.database.dto.AllStreaks

interface DatabaseRepository {
    suspend fun getAllStreaks(): AllStreaks
}
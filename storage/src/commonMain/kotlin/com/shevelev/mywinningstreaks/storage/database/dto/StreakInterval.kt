package com.shevelev.mywinningstreaks.storage.database.dto

import com.shevelev.mywinningstreaks.coreentities.Status
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class StreakInterval(
    val id: Long,
    val streakId: Long,
    val fromDate: Instant,
    val toDate: Instant,
    val type: Status,
)
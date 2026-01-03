package com.shevelev.mywinningstreaks.storage.database.dto

import com.shevelev.mywinningstreaks.coreentities.Status
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalTime::class)
data class StreakInterval(
    val id: Long,
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val status: Status,
)
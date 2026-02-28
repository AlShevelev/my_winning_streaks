package com.shevelev.mywinningstreaks.storage.database.dto

import kotlinx.datetime.LocalDate

data class Streak(
    val id: Long,
    val sortingOrder: Long,
    val title: String,
    val createdAt: LocalDate,
    val intervals: List<StreakInterval>,
)
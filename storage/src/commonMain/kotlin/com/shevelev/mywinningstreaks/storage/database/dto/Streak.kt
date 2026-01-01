package com.shevelev.mywinningstreaks.storage.database.dto

data class Streak(
    val id: Long,
    val sortingOrder: Long,
    val title: String,
    val intervals: List<StreakInterval>,
)
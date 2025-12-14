package com.shevelev.mywinningstreaks.shared.usecases.dto

internal data class Streak(
    val id: Long,
    val title: String,
    val lastIntervalId: Long,
    val totalDays: Int,
    val arcs: List<StreakArc>,
)
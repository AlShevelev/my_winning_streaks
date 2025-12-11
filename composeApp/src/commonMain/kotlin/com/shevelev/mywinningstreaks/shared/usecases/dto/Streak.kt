package com.shevelev.mywinningstreaks.shared.usecases.dto

internal data class Streak(
    val id: Long,
    val title: String,
    val lastIntervalId: Long,
    val arcs: List<StreakArc>,
)
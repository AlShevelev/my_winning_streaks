package com.shevelev.mywinningstreaks.shared.usecases.dto

internal data class Streak(
    val id: Long,
    val title: String,
    val lastIntervalId: Long,
    val totalDaysToShow: Int,
    val totalDays: Int,
    val winDays: Int,
    val failDays: Int,
    val sickDays: Int,
    val arcs: List<StreakArc>,
)
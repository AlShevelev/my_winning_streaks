package com.shevelev.mywinningstreaks.shared.usecases.dto

import com.shevelev.mywinningstreaks.coreentities.utils.DateTimeUtils
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalDate

@OptIn(ExperimentalTime::class)
internal data class Streak(
    val id: Long,
    val title: String,
    val lastIntervalId: Long?,
    val lastToDate: LocalDate,
    val totalDaysToShow: Int,
    val totalDays: Int,
    val winDays: Int,
    val failDays: Int,
    val sickDays: Int,
    val arcs: List<StreakArc>,
) {
    val canMark: Boolean
        get() = DateTimeUtils.nowLocalDate > lastToDate
}
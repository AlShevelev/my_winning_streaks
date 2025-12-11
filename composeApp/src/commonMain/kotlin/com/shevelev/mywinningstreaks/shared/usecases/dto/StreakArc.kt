package com.shevelev.mywinningstreaks.shared.usecases.dto

import com.shevelev.mywinningstreaks.coreentities.Status

internal data class StreakArc(
    val from: Float,
    val to: Float,
    val status: Status,
)
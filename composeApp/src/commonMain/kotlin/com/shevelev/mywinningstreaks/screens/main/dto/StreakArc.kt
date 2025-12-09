package com.shevelev.mywinningstreaks.screens.main.dto

import com.shevelev.mywinningstreaks.coreentities.Status

data class StreakArc(
    val from: Float,
    val to: Float,
    val status: Status,
)

package com.shevelev.mywinningstreaks.shared.usecases

import com.shevelev.mywinningstreaks.shared.usecases.dto.StreakArc
import com.shevelev.mywinningstreaks.storage.database.dto.Streak

internal interface DiagramArcCalculator {
    suspend fun calculateArcs(dbStreak: Streak, daysToShow: Int): List<StreakArc>
}
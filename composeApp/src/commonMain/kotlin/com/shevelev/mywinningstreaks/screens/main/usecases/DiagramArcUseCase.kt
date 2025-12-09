package com.shevelev.mywinningstreaks.screens.main.usecases

import com.shevelev.mywinningstreaks.screens.main.dto.StreakArc
import com.shevelev.mywinningstreaks.storage.database.dto.Streak

interface DiagramArcUseCase {
    suspend fun calculateArcs(dbStreak: Streak, daysToShow: Int): List<StreakArc>
}
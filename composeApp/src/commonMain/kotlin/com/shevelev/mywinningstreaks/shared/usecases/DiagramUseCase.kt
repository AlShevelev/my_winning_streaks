package com.shevelev.mywinningstreaks.shared.usecases

import com.shevelev.mywinningstreaks.shared.usecases.dto.Streak
import kotlinx.coroutines.flow.StateFlow

internal interface DiagramUseCase {
    val diagrams: StateFlow<List<Streak>?>

    suspend fun init()

    suspend fun addStreak(title: String)
}
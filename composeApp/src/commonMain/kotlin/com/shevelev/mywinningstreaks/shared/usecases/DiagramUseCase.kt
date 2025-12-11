package com.shevelev.mywinningstreaks.shared.usecases

import com.shevelev.mywinningstreaks.shared.usecases.dto.Streak
import kotlinx.coroutines.flow.Flow

internal interface DiagramUseCase {
    val diagrams: Flow<List<Streak>?>

    suspend fun init()

    suspend fun addStreak(title: String)
}
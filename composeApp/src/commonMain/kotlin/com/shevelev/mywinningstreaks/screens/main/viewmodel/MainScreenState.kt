package com.shevelev.mywinningstreaks.screens.main.viewmodel

import com.shevelev.mywinningstreaks.shared.usecases.dto.Streak

internal sealed interface MainScreenState {
    data object Loading : MainScreenState

    data object Empty : MainScreenState

    data class Data(
        val streak: Streak
    ) : MainScreenState
}
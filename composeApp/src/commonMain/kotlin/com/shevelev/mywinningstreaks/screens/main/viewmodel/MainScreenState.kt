package com.shevelev.mywinningstreaks.screens.main.viewmodel

import com.shevelev.mywinningstreaks.shared.usecases.dto.Streak

internal sealed class MainScreenState(
    val addButtonEnabled: Boolean,
    val pagerButtonEnabled: Boolean,
    val gridButtonEnabled: Boolean,
    val settingsButtonEnabled: Boolean,
) {
    data object Loading : MainScreenState(
        addButtonEnabled = false,
        pagerButtonEnabled = false,
        gridButtonEnabled = false,
        settingsButtonEnabled = false,
    )

    data object Empty : MainScreenState(
        addButtonEnabled = true,
        pagerButtonEnabled = false,
        gridButtonEnabled = false,
        settingsButtonEnabled = true,
    )

    data class Data(
        val streaks: List<Streak>
    ) : MainScreenState(
        addButtonEnabled = true,
        pagerButtonEnabled = false,
        gridButtonEnabled = true,
        settingsButtonEnabled = true,
    )
}
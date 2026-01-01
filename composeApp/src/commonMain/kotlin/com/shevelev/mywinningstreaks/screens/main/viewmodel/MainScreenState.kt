package com.shevelev.mywinningstreaks.screens.main.viewmodel

import com.shevelev.mywinningstreaks.shared.usecases.dto.Streak

internal sealed class MainScreenState(
    val addButtonEnabled: Boolean,
    open val pagerButtonEnabled: Boolean,
    open val gridButtonEnabled: Boolean,
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
        val streaks: List<Streak>,
        val onePageMode: Boolean,
        override val pagerButtonEnabled: Boolean,
        override val gridButtonEnabled: Boolean,
    ) : MainScreenState(
        addButtonEnabled = true,
        pagerButtonEnabled = true,
        gridButtonEnabled = true,
        settingsButtonEnabled = true,
    )
}
package com.shevelev.mywinningstreaks.screens.settings.viewmodel

import kotlinx.datetime.LocalTime

internal sealed interface SettingsScreenState {
    data object Loading : SettingsScreenState

    data class Data(
        val recentDaysToShow: Int,
        val recentDaysToShowValues: List<Int>,

        val timeToNotify: LocalTime,

        val askPermissionsButtonVisible: Boolean,
    ) : SettingsScreenState
}
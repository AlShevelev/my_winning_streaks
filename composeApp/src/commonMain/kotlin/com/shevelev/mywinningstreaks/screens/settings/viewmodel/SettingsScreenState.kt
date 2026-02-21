package com.shevelev.mywinningstreaks.screens.settings.viewmodel

import kotlinx.datetime.LocalTime

internal sealed interface SettingsScreenState {
    data object Loading : SettingsScreenState

    data class Data(
        val recentDaysToShow: Int,
        val recentDaysToShowValues: List<Int>,

        val timeToStart: LocalTime,

        val howOften: Int,
        val howOftenValues: List<Int>,

        val howManyTimes: Int,
        val howManyTimesValues: List<Int>,

        val askPermissionsButtonVisible: Boolean,
    ) : SettingsScreenState
}
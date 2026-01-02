package com.shevelev.mywinningstreaks.screens.settings.viewmodel

internal data class SettingsScreenState(
    val possibleRecentDaysToShow: List<Int>,
    val selectedRecentDaysToShow: Int,
)
package com.shevelev.mywinningstreaks.shared.alarms

import kotlinx.datetime.LocalTime

internal interface AlarmsManagement {
    fun setAlarms(timeToFail: LocalTime, howOftenMinutes: Int, howManyTimes: Int)
}
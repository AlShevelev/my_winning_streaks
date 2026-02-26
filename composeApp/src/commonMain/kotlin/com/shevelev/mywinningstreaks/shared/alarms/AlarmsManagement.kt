package com.shevelev.mywinningstreaks.shared.alarms

import kotlinx.datetime.LocalTime

internal interface AlarmsManagement {
    fun setAlarm(timeToNotify: LocalTime)
}
package com.shevelev.mywinningstreaks.shared.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalTime

internal class AlarmsManagementImpl(
    private val context: Context,
) : AlarmsManagement {
    private val alarmManager: AlarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setAlarm(timeToNotify: LocalTime) {
        with (alarmManager) {
            cancel(createPendingIntent(EVENT_1_ID))

            setRepeating(
                AlarmManager.RTC_WAKEUP,
                localTimeToAlarmManagerTime(timeToNotify),
                AlarmManager.INTERVAL_DAY,
                createPendingIntent(EVENT_1_ID)
            )
        }
    }

    @Suppress("SameParameterValue")
    private fun createPendingIntent(id: Int): PendingIntent = PendingIntent.getBroadcast(
        context,
        id,
        Intent(context, AlarmReceiver::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    @OptIn(ExperimentalTime::class)
    private fun localTimeToAlarmManagerTime(time: LocalTime): Long =
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
        }.timeInMillis

    companion object {
        private const val EVENT_1_ID = 93278621
    }
}
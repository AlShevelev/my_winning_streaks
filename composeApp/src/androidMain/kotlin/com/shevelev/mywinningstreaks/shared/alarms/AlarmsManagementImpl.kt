package com.shevelev.mywinningstreaks.shared.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Calendar
import kotlin.time.ExperimentalTime
import kotlinx.datetime.LocalTime

internal class AlarmsManagementImpl(
    private val context: Context,
) : AlarmsManagement {
    private val alarmManager: AlarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @androidx.annotation.RequiresPermission(android.Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun setAlarm(timeToNotify: LocalTime) {
        with (alarmManager) {
            val intent = createPendingIntent(EVENT_1_ID)

            cancel(intent)

            runCatching {
                setAlarmClock(
                    AlarmManager.AlarmClockInfo(localTimeToAlarmManagerTime(timeToNotify), intent),
                    intent
                )
            }.onFailure { Log.e(null, it.message, it) }
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
    private fun localTimeToAlarmManagerTime(time: LocalTime): Long {
        val calendar: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
        }

        // For the past time, set the alarm for the next day - to prevent immediate triggering
        if (Calendar.getInstance().timeInMillis > calendar.timeInMillis) {
            calendar.add(Calendar.DATE, 1)
        }

        return calendar.timeInMillis
    }

    companion object {
        private const val EVENT_1_ID = 93278621
    }
}
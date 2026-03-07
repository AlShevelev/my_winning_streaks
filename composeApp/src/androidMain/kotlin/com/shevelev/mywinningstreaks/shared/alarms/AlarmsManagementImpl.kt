package com.shevelev.mywinningstreaks.shared.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.Calendar
import kotlinx.datetime.LocalTime

internal class AlarmsManagementImpl(
    private val context: Context,
) : AlarmsManagement {
    private val alarmManager: AlarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    @androidx.annotation.RequiresPermission(android.Manifest.permission.SCHEDULE_EXACT_ALARM)
    override fun setAlarm(timeToNotify: LocalTime) {
        with (alarmManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if(!canScheduleExactAlarms()) return@with
            }

            val intent = createPendingIntent(EVENT_1_ID)

            cancel(intent)

            try {
                setAlarmClock(
                    AlarmManager.AlarmClockInfo(localTimeToAlarmManagerTime(timeToNotify), intent),
                    intent
                )
            } catch (ex: SecurityException) {
                Log.e(null, ex.message, ex)
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun createPendingIntent(id: Int): PendingIntent = PendingIntent.getBroadcast(
        context,
        id,
        Intent(context, AlarmReceiver::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    private fun localTimeToAlarmManagerTime(time: LocalTime): Long {
        val calendar: Calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
        }

        val nowCalendar = Calendar.getInstance()

        if (// For the past time, set the alarm for the next day - to prevent immediate triggering
            nowCalendar.timeInMillis > calendar.timeInMillis ||
            ( // To prevent firing if a user opens the app immediately
                nowCalendar.get(Calendar.HOUR_OF_DAY) == calendar.get(Calendar.HOUR_OF_DAY) &&
                        nowCalendar.get(Calendar.MINUTE) == calendar.get(Calendar.MINUTE)
            )
        ) {
            calendar.add(Calendar.DATE, 1)
        }

        return calendar.timeInMillis
    }

    companion object {
        private const val EVENT_1_ID = 93278621
    }
}
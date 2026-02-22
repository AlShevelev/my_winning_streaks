package com.shevelev.mywinningstreaks.shared.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kotlin.random.Random
import kotlinx.datetime.LocalTime

internal class AlarmsManagementImpl(
    private val context: Context,
) : AlarmsManagement {
    private val alarmManager: AlarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setAlarms(timeToFail: LocalTime, howOftenMinutes: Int, howManyTimes: Int) {
        with (alarmManager) {
            cancelAll()

            for (i in 1..howManyTimes) {
                val timeToFire = timeToFail.minusMinutes(i * howOftenMinutes)
                if (timeToFire < 0L) break

                setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    timeToFire,
                    AlarmManager.INTERVAL_DAY,
                    createPendingIntent(Random.nextInt())
                )
            }
        }
    }

    private fun createPendingIntent(id: Int): PendingIntent = PendingIntent.getBroadcast(
        context,
        id,
        Intent(context, AlarmReceiver::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    private fun LocalTime.minusMinutes(minutes: Int)
        = (toMillisecondOfDay() - (minutes * 60 * 1_000)).toLong()
}
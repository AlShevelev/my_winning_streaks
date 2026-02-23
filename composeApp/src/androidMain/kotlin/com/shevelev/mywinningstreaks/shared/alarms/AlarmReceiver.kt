package com.shevelev.mywinningstreaks.shared.alarms

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.shevelev.mywinningstreaks.MainActivity
import com.shevelev.mywinningstreaks.MyWinningStreaksApp
import com.shevelev.mywinningstreaks.R

internal class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { context ->
            if ((context.applicationContext as MyWinningStreaksApp).isInForeground) return

            val notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            showNotification(context, notificationManager)
        }
    }

    private fun showNotification(context: Context, notificationManager: NotificationManager) {
        val channel = createNotificationChannel()
        notificationManager.createNotificationChannel(channel)

        val notification = buildNotification(context)
        notificationManager.notify(
            NOTIFICATION_ID,
            notification
        )
    }

    private fun createNotificationChannel(
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
    ): NotificationChannel = NotificationChannel(
        NOTIFICATION_CHANNEL_ID,
        NOTIFICATION_CHANNEL_NAME,
        importance,
    )

    private fun buildNotification(context: Context): Notification {
        return NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.marked_as_failure))
            .setSmallIcon(android.R.drawable.btn_star)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    PENDING_INTENT_ID,
                    Intent(context, MainActivity::class.java).apply {
                        setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    },
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )
            .build()
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID: String = "com.shevelev.mywinningstreaks.e32b52d8-8cfe-4604-822f-85cabc8264f0"
        private const val NOTIFICATION_CHANNEL_NAME: String = "MyWinningStreaksRunningNotification"
        private const val NOTIFICATION_ID: Int = 72688187
        private const val PENDING_INTENT_ID: Int = 72310196
    }
}
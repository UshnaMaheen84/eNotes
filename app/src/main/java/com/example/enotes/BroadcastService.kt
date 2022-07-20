package com.example.enotes

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"


class BroadcastService : BroadcastReceiver() {
    companion object {
        const val NOTIFICATION_ID = 100
        const val NOTIFICATION_CHANNEL_ID = "1000"
    }


    override fun onReceive(context: Context, intent: Intent) {

        val time= intent.getStringExtra("time")
        Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show()
        val title= intent.getStringExtra("title")


        createNotificationChannel(context)
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            context, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        notifyNotification(context,time, title,pendingIntent)


        if (time != null) {
            Log.e("success", time)
        }

    }
    private fun createNotificationChannel(context: Context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "my channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            NotificationManagerCompat.from(context).createNotificationChannel(notificationChannel)
        }
    }

    private fun notifyNotification(
        context: Context,
        time: String?,
        title: String?,
        pendingIntent: PendingIntent
    ) {
        with(NotificationManagerCompat.from(context)) {
            val build = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(time)
                .setContentText(title)
                .setSmallIcon(R.drawable.ic_lock_idle_alarm)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            notify(NOTIFICATION_ID, build.build())

        }

    }

}


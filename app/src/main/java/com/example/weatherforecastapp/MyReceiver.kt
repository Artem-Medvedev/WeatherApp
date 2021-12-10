package com.example.weatherforecastapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
       val builder = NotificationCompat.Builder(context,"notify")
           .setSmallIcon(R.drawable.cloud)
           .setContentTitle("Hello There!")
           .setContentText("Tap to see what is the weather today")
           .setPriority(NotificationCompat.PRIORITY_DEFAULT)

       val notificationManager =  NotificationManagerCompat.from(context)
        notificationManager.notify(200,builder.build() )
    }
}

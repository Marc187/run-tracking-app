package com.example.runningbuddy.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.runningbuddy.MainActivity
import com.example.runningbuddy.MainActivity.Companion.ACTION_PAUSE_SERVICE
import com.example.runningbuddy.MainActivity.Companion.ACTION_START_OR_RESUME_SERVICE
import com.example.runningbuddy.MainActivity.Companion.ACTION_STOP_SERVICE
import com.example.runningbuddy.MainActivity.Companion.NOTIFICATION_CHANNEL_ID
import com.example.runningbuddy.MainActivity.Companion.NOTIFICATION_CHANNEL_NAME
import com.example.runningbuddy.MainActivity.Companion.NOTIFICATION_ID
import com.example.runningbuddy.R

class TrackingService: LifecycleService() {

    var isFirstRun = true

    //This run when we send an intent to this activity
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if(isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        println("Started or resumed service")
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    println("Paused service")
                }
                ACTION_STOP_SERVICE -> {
                    println("Stopped service")
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForegroundService() {
        val notifactionManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_baseline_directions_run_24)
            .setContentTitle("RunningBuddy")
            .setContentText("00:00:00")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notifactionManager)
        }

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}
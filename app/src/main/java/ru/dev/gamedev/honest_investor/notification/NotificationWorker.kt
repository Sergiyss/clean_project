package ru.dev.gamedev.honest_investor.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import ru.dev.gamedev.honest_investor.MainActivity
import ru.dev.gamedev.honest_investor.R
import ru.dev.gamedev.honest_investor.subTitleNotifi
import ru.dev.gamedev.honest_investor.titleNotifi


class NotificationWorker(val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        showNotification()
        return Result.success()
    }

    private fun showNotification() {
        createNotificationChannel()

        val intent = Intent(context, MainActivity::class.java).apply {

            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        }

        val notifyPendingIntent = PendingIntent.getActivity(
            context, 10, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )


        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(titleNotifi)
            .setContentText(subTitleNotifi)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(notifyPendingIntent) // Устанавливаем Intent для перехода
            .setAutoCancel(true) // Закрыть уведомление при нажатии
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Реализовать запрос разрешений при необходимости
            return
        }
        NotificationManagerCompat.from(applicationContext).notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Название канала",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val notificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFICATION_ID = 1
    }
}
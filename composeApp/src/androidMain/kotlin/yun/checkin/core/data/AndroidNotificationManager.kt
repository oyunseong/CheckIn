package yun.checkin.core.data

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import yun.checkin.core.data_api.NotificationManager as AppNotificationManager

class AndroidNotificationManager(
    private val context: Context
) : AppNotificationManager {

    companion object {
        const val ANDROID_NOTIFICATION_CHANNEL_ID = "work_end_notification"
        const val ANDROID_NOTIFICATION_CHANNEL_NAME = "Check_in Notification"
        const val ANDROID_NOTIFICATION_ID = 1001
    }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    init {
        createNotificationChannel()
    }

    override suspend fun scheduleWorkEndNotification() {
        // 기존 알림 취소
        cancelAllNotifications()

        // 현재 시간에서 8시간 30분 후 시간 계산
        val currentTime = System.currentTimeMillis()
        val workDurationMillis = (8 * 60 * 60 + 30 * 60) * 1000L // 8시간 30분을 밀리초로 변환
        val triggerTime = currentTime + workDurationMillis

        val intent = Intent(context, WorkEndNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ANDROID_NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // AlarmManager를 사용하여 알림 스케줄링
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        } else {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }

    override suspend fun cancelAllNotifications() {
        val intent = Intent(context, WorkEndNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ANDROID_NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
        notificationManager.cancel(ANDROID_NOTIFICATION_ID)
    }

    override suspend fun cancelNotification(notificationId: String) {
        // 간단한 구현에서는 모든 알림 취소
        cancelAllNotifications()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ANDROID_NOTIFICATION_CHANNEL_ID,
                ANDROID_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "출근 후 8시간 30분 뒤 퇴근 시간을 알려주는 알림"
            }

            notificationManager.createNotificationChannel(channel)
        }
    }
}

/**
 * 알림을 실제로 표시하는 BroadcastReceiver
 */
class WorkEndNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context)
    }

    private fun showNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 앱을 열기 위한 Intent 생성
        val appIntent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            appIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(
            context,
            AndroidNotificationManager.ANDROID_NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("퇴근 시간 알림")
            .setContentText("오늘 하루 수고하셨습니다! 퇴근 30분전 입니다.")
            .setStyle(NotificationCompat.BigTextStyle().bigText("오늘 하루 수고하셨습니다! 퇴근 30분전 입니다."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            AndroidNotificationManager.ANDROID_NOTIFICATION_ID,
            notification
        )
    }
}
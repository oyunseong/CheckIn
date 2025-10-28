package yun.checkin.core.data

import platform.UserNotifications.*
import platform.Foundation.*
import yun.checkin.core.data_api.NotificationManager

class IOSNotificationManager : NotificationManager {

    private val notificationCenter = UNUserNotificationCenter.currentNotificationCenter()

    init {
        requestNotificationPermission()
    }

    override suspend fun scheduleWorkEndNotification() {
        // 기존 알림 취소
        cancelAllNotifications()

        // 8시간 30분을 초로 계산 (8 * 3600 + 30 * 60 = 30600초)
        val workHoursInSeconds = 8 * 3600 + 30 * 60

        // 알림 콘텐츠 생성
        val content = UNMutableNotificationContent().apply {
            setTitle("퇴근 시간 알림")
            setBody("오늘 하루 수고하셨습니다! 퇴근 시간입니다.")
            setSound(UNNotificationSound.defaultSound())
        }

        // 알림 트리거 생성 (시간 기반)
        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
            timeInterval = workHoursInSeconds.toDouble(),
            repeats = false
        )

        // 알림 요청 생성
        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = "work_end_notification",
            content = content,
            trigger = trigger
        )

        // 알림 스케줄링
        notificationCenter.addNotificationRequest(request) { error ->
            if (error != null) {
                println("Failed to schedule notification: ${error.localizedDescription}")
            } else {
                println("Work end notification scheduled successfully")
            }
        }
    }

    override suspend fun cancelAllNotifications() {
        notificationCenter.removeAllPendingNotificationRequests()
        notificationCenter.removeAllDeliveredNotifications()
    }

    override suspend fun cancelNotification(notificationId: String) {
        notificationCenter.removePendingNotificationRequestsWithIdentifiers(listOf(notificationId))
    }

    private fun requestNotificationPermission() {
        val options =
            UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge

        notificationCenter.requestAuthorizationWithOptions(options) { granted, error ->
            if (granted) {
                println("Notification permission granted")
            } else {
                println("Notification permission denied: ${error?.localizedDescription}")
            }
        }
    }
}
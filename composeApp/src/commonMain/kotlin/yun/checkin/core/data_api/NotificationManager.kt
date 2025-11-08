package yun.checkin.core.data_api

import kotlinx.datetime.LocalDateTime

/**
 * 로컬 푸시 알림을 관리하는 인터페이스
 */
interface NotificationManager {
    /**
     * 퇴근 알림을 스케줄링합니다 (현재 시간으로부터 8시간 30분 후)
     */
    suspend fun scheduleWorkEndNotification(second : Int)

    /**
     * 모든 예약된 알림을 취소합니다
     */
    suspend fun cancelAllNotifications()

    /**
     * 특정 알림을 취소합니다
     * @param notificationId 알림 ID
     */
    suspend fun cancelNotification(notificationId: String)
}
package yun.checkin.core.data_api

import kotlinx.datetime.LocalDateTime

data class AttendanceRecord(
    val attendanceTime: LocalDateTime,
    val userId: String
)

interface CheckInRepository {
    suspend fun checkIn(userId: String): Result<Unit>

    suspend fun isCheckIn(userId: String): Result<Boolean>

    suspend fun getHistory(userId: String): Result<List<AttendanceRecord>>
}
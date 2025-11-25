package yun.checkin.core.data_api

import kotlinx.datetime.LocalDateTime

data class AttendanceRecord(
    val attendanceTime: LocalDateTime,
    val userId: String,
    val type: AttendanceType = AttendanceType.CHECK_IN
)

enum class AttendanceType {
    CHECK_IN,
    CHECK_OUT
}

interface CheckInRepository {
    suspend fun checkIn(): Result<Unit>

    suspend fun checkOut(): Result<Unit>

    suspend fun isCheckIn(): Result<Boolean>

    suspend fun getHistory(): Result<List<AttendanceRecord>>
}
package yun.checkin.core.data_api

interface CheckInRepository {
    suspend fun checkIn(userId: String): Result<Unit>
}
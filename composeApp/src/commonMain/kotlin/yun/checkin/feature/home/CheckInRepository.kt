package yun.checkin.feature.home

interface CheckInRepository {
    suspend fun checkIn(): Result<Unit>
}

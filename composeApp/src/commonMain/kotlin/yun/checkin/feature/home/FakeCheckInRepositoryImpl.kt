package yun.checkin.feature.home

import kotlinx.coroutines.delay

class FakeCheckInRepositoryImpl : CheckInRepository {
    private var hasCheckedIn = false

    override suspend fun checkIn(): Result<Unit> {
        delay(1000) // Simulate network latency
        return if (!hasCheckedIn) {
            hasCheckedIn = true
            Result.success(Unit)
        } else {
            Result.failure(Exception("이미 출석했습니다."))
        }
    }
}

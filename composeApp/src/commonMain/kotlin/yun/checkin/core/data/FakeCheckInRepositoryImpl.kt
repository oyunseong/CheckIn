package yun.checkin.core.data

import yun.checkin.FirebaseFirestore
import yun.checkin.core.data_api.CheckInRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class FakeCheckInRepositoryImpl : CheckInRepository {
    private val firestore = FirebaseFirestore()

    override suspend fun checkIn(userId: String): Result<Unit> {
        val currentTime = Clock.System.now().epochSeconds * 1000
        return firestore.saveData(
            collection = "user_attendance",
            data = mapOf(
                "user_id" to userId,
                "attendance_time" to currentTime
            )
        )
    }
}
package yun.checkin.core.data

import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.toLocalDateTime
import yun.checkin.FirebaseFirestore
import yun.checkin.core.data_api.CheckInRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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

    override suspend fun isCheckIn(userId: String): Result<Boolean> {
        val documentsResult = firestore.getDocuments(
            collection = "user_attendance",
            field = "user_id",
            value = userId
        )
        if (documentsResult.isEmpty()) return Result.success(false)

        val today = Clock.System.now().toLocalDateTime(currentSystemDefault()).date

        val hasCheckedInToday = documentsResult.any { doc ->
            val attendanceTimeMillis = doc?.get("attendance_time") as? Long ?: return@any false

            val checkInDate = Instant.fromEpochMilliseconds(attendanceTimeMillis)
                .toLocalDateTime(currentSystemDefault())
                .date

            checkInDate == today
        }

        return Result.success(hasCheckedInToday)
    }
}
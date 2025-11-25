package yun.checkin.core.data

import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.toLocalDateTime
import yun.checkin.FirebaseAuth
import yun.checkin.FirebaseFirestore
import yun.checkin.core.data_api.AttendanceRecord
import yun.checkin.core.data_api.CheckInRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class CheckInRepositoryImpl(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : CheckInRepository {

    override suspend fun checkIn(): Result<Unit> {
        val uuid = firebaseAuth.getCurrentUUID() ?: return Result.failure(IllegalStateException("User not logged in"))
        val currentTime = Clock.System.now().epochSeconds * 1000
        val saveResult = firestore.saveData(
            collection = "user_attendance",
            data = mapOf(
                "user_id" to uuid,
                "attendance_time" to currentTime,
                "type" to "check_in"
            )
        )

        return if (saveResult.isSuccess) {
            Result.success(Unit)
        } else {
            Result.failure(saveResult.exceptionOrNull()!!)
        }
    }

    override suspend fun checkOut(): Result<Unit> {
        val uuid = firebaseAuth.getCurrentUUID()
            ?: return Result.failure(IllegalStateException("User not logged in"))
        val currentTime = Clock.System.now().epochSeconds * 1000
        val saveResult = firestore.saveData(
            collection = "user_attendance",
            data = mapOf(
                "user_id" to uuid,
                "attendance_time" to currentTime,
                "type" to "check_out"
            )
        )

        return if (saveResult.isSuccess) {
            Result.success(Unit)
        } else {
            Result.failure(saveResult.exceptionOrNull()!!)
        }
    }

    override suspend fun isCheckIn(): Result<Boolean> {
        return try {
            val uuid = firebaseAuth.getCurrentUUID() ?: return Result.failure(IllegalStateException("User not logged in"))
            val documentsResult = firestore.queryData(
                collection = "user_attendance",
                field = "user_id",
                value = uuid
            )

            if (documentsResult.isEmpty()) {
                return Result.success(false)
            }

            val today = Clock.System.now().toLocalDateTime(currentSystemDefault()).date

            val hasCheckedInToday = documentsResult.any { doc ->
                val attendanceTimeMillis = doc["attendance_time"] as? Long ?: return@any false

                val checkInDate = Instant.fromEpochMilliseconds(attendanceTimeMillis)
                    .toLocalDateTime(currentSystemDefault())
                    .date

                checkInDate == today
            }

            Result.success(hasCheckedInToday)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getHistory(): Result<List<AttendanceRecord>> {
        return try {
            val uuid = firebaseAuth.getCurrentUUID() ?: return Result.failure(IllegalStateException("User not logged in"))
            val documentsResult = firestore.queryData(
                collection = "user_attendance",
                field = "user_id",
                value = uuid
            )

            val attendanceRecords = documentsResult.mapNotNull { doc ->
                val attendanceTimeMillis = doc["attendance_time"] as? Long
                val userIdFromDoc = doc["user_id"] as? String

                if (attendanceTimeMillis != null && userIdFromDoc != null) {
                    val attendanceTime = Instant.fromEpochMilliseconds(attendanceTimeMillis)
                        .toLocalDateTime(currentSystemDefault())
                    AttendanceRecord(
                        attendanceTime = attendanceTime,
                        userId = userIdFromDoc
                    )
                } else null
            }.sortedByDescending { it.attendanceTime }

            Result.success(attendanceRecords)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
package yun.checkin

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import yun.checkin.logger.*

// iOS에서 cinterop를 통한 Swift Firebase 구현
// Android와 동일한 API 제공
@OptIn(ExperimentalForeignApi::class)
actual class FirebaseAuth {

    actual fun getCurrentUser(): String? {
        Logger.log("🔐 Firebase Auth - getCurrentUser() called")

        try {
            return getCurrentUserFromSwift()
        } catch (e: Exception) {
            Logger.log("❌ Firebase Auth - getCurrentUser() failed: ${e.message}")
            return null
        }
    }

    actual suspend fun signIn(email: String, password: String): Boolean =
        suspendCancellableCoroutine { continuation ->
            Logger.log("🔐 Firebase Auth - signIn() called for email: $email")

            try {
                signInWithSwift(email, password) { success, error ->
                    if (error != null) {
                        Logger.log("❌ Firebase Auth - signIn() failed: $error")
                        continuation.resume(false)
                    } else {
                        Logger.log("✅ Firebase Auth - signIn() success: $success")
                        continuation.resume(success)
                    }
                }
            } catch (e: Exception) {
                Logger.log("❌ Firebase Auth - signIn() exception: ${e.message}")
                continuation.resume(false)
            }
        }

    private fun getCurrentUserFromSwift(): String? {
        val userId = "ios_user_12345"
        Logger.log("👤 Swift Mock - returning user: $userId")
        return userId
    }

    private fun signInWithSwift(
        email: String,
        password: String,
        completion: (Boolean, String?) -> Unit
    ) {
        Logger.log("🔍 Swift Mock - validating email: $email")
        if (email.isNotEmpty() && password.length >= 6) {
            Logger.log("✅ Swift Mock - validation passed")
            completion(true, null)
        } else {
            Logger.log("❌ Swift Mock - validation failed")
            completion(false, "Invalid email or password")
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
actual class FirebaseFirestore {

    actual suspend fun saveData(collection: String, data: Map<String, Any>): Result<Unit> {
        Logger.log("💾 Firebase Firestore - saveData() called for collection: $collection")

        return try {
            saveDataWithSwift(collection, data)
            Logger.log("✅ Firebase Firestore - saveData() success")
            Result.success(Unit)
        } catch (e: Exception) {
            Logger.log("❌ Firebase Firestore - saveData() failed: ${e.message}")
            Result.failure(e)
        }
    }

    actual suspend fun getData(collection: String, documentId: String): Map<String, Any>? {
        Logger.log("📖 Firebase Firestore - getData() called for doc: $documentId")

        return try {
            val result = getDataFromSwift(collection, documentId)
            Logger.log("✅ Firebase Firestore - getData() result: ${result?.keys}")
            result
        } catch (e: Exception) {
            Logger.log("❌ Firebase Firestore - getData() failed: ${e.message}")
            null
        }
    }

    actual suspend fun getDocuments(
        collection: String,
        field: String,
        value: Any
    ): List<Map<String, Any?>?> {
        Logger.log("📚 Firebase Firestore - getDocuments() called for $field=$value")

        return try {
            val result = getDocumentsFromSwift(collection, field, value)
            Logger.log("✅ Firebase Firestore - getDocuments() found ${result.size} documents")
            result
        } catch (e: Exception) {
            Logger.log("❌ Firebase Firestore - getDocuments() failed: ${e.message}")
            emptyList()
        }
    }

    private suspend fun saveDataWithSwift(collection: String, data: Map<String, Any>) {
        Logger.log("📤 Swift Mock - saving data to $collection: ${data.keys}")
        delay(500)
        Logger.log("✅ Swift Mock - save completed")
    }

    private suspend fun getDataFromSwift(
        collection: String,
        documentId: String
    ): Map<String, Any>? {
        Logger.log("📥 Swift Mock - getting data from $collection/$documentId")
        delay(300)

        return if (documentId == "nonexistent") {
            Logger.log("🚫 Swift Mock - document not found")
            null
        } else {
            val result = mapOf(
                "id" to documentId,
                "data" to "swift_firebase_data",
                "timestamp" to 1234567890L,
                "platform" to "ios"
            )
            Logger.log("✅ Swift Mock - returning data: ${result.keys}")
            result
        }
    }

    private suspend fun getDocumentsFromSwift(
        collection: String,
        field: String,
        value: Any
    ): List<Map<String, Any?>?> {
        Logger.log("📊 Swift Mock - querying $collection where $field=$value")
        delay(400)

        val result = listOf(
            mapOf(
                "id" to "doc1_ios",
                field to value,
                "timestamp" to 1234567890L,
                "platform" to "ios"
            ),
            mapOf(
                "id" to "doc2_ios",
                field to value,
                "timestamp" to 1234567889L,
                "platform" to "ios"
            ),
            null
        )

        Logger.log("📋 Swift Mock - returning ${result.size} documents")
        return result
    }
}

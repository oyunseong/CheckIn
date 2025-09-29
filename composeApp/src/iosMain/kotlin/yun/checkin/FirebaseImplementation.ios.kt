package yun.checkin

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import yun.checkin.firebase.FirebaseHelper
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

// iOS에서 Swift Firebase Helper를 사용한 실제 구현
// 임시로 Logger 대신 println 사용 (cinterop 바인딩 문제 해결 전까지)

@OptIn(ExperimentalForeignApi::class)
actual class FirebaseAuth {

    actual fun getCurrentUser(): String? {
        FirebaseHelper.log("🔐 iOS Firebase Auth - getCurrentUser() called")

        // TODO: Swift에서 Auth 메서드 활성화 후 사용
        // return FirebaseHelper.getCurrentUser()

        // 임시 시뮬레이션
        val result = "ios_user_12345"
        FirebaseHelper.log("👤 iOS Firebase Auth - getCurrentUser result: $result")
        return result
    }

    actual suspend fun signIn(email: String, password: String): Boolean {
        FirebaseHelper.log("🔐 iOS Firebase Auth - signIn() called for email: $email")

        return try {
            // TODO: Swift에서 Auth 메서드 활성화 후 사용
            // val result = suspendCancellableCoroutine<Boolean> { continuation ->
            //     FirebaseHelper.signInWithEmail(email, password) { success, error ->
            //         if (error != null) {
            //             continuation.resumeWithException(Exception(error))
            //         } else {
            //             continuation.resume(success)
            //         }
            //     }
            // }

            // 임시 시뮬레이션
            val success = email.isNotEmpty() && password.length >= 6
            FirebaseHelper.log("✅ iOS Firebase Auth - signIn success: $success")
            success
        } catch (e: Exception) {
            FirebaseHelper.log("❌ iOS Firebase Auth - signIn failed: ${e.message}")
            throw e
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
actual class FirebaseFirestore {

    actual suspend fun saveData(collection: String, data: Map<String, Any>): Result<String> {
        FirebaseHelper.log("💾 iOS Firestore - saveData() to collection: $collection")

        return try {
            // 실제 Swift FirebaseHelper 사용!
            val result = suspendCancellableCoroutine<String> { continuation ->
                // Kotlin Map을 NSDictionary로 변환
                val nsData = data as Map<Any?, Any?>

                FirebaseHelper.saveDataWithCollection(collection, nsData) { success, error ->
                    if (error != null) {
                        FirebaseHelper.log("❌ iOS Firestore - saveData failed: $error")
                        continuation.resumeWithException(Exception(error))
                    } else if (success) {
                        FirebaseHelper.log("✅ iOS Firestore - saveData success via Swift!")
                        continuation.resume("Document saved successfully via Swift Firebase")
                    } else {
                        FirebaseHelper.log("❌ iOS Firestore - saveData failed: unknown error")
                        continuation.resumeWithException(Exception("Save failed"))
                    }
                }
            }
            Result.success(result)
        } catch (e: Exception) {
            FirebaseHelper.log("❌ iOS Firestore - saveData exception: ${e.message}")
            Result.failure(e)
        }
    }

    actual suspend fun getData(collection: String, documentId: String): Map<String, Any>? {
        FirebaseHelper.log("📄 iOS Firestore - getData() from $collection/$documentId")

        return try {
            suspendCancellableCoroutine { continuation ->
                FirebaseHelper.getDataWithCollection(collection, documentId) { data, error ->
                    if (error != null) {
                        FirebaseHelper.log("❌ iOS Firestore - getData failed: $error")
                        continuation.resumeWithException(Exception(error))
                    } else if (data != null) {
                        FirebaseHelper.log("✅ iOS Firestore - getData success via Swift!, keys: ${data.keys}")
                        // NSDictionary를 Map<String, Any>로 안전하게 변환
                        val kotlinMap = (data as? Map<*, *>)?.mapNotNull { (key, value) ->
                            val keyStr = key as? String ?: return@mapNotNull null
                            keyStr to (value ?: "null")
                        }?.toMap() ?: emptyMap()
                        continuation.resume(kotlinMap)
                    } else {
                        FirebaseHelper.log("🚫 iOS Firestore - getData: document not found")
                        continuation.resume(null)
                    }
                }
            }
        } catch (e: Exception) {
            FirebaseHelper.log("❌ iOS Firestore - getData failed: ${e.message}")
            throw e
        }
    }

    actual suspend fun queryData(
        collection: String,
        field: String,
        value: Any
    ): List<Map<String, Any>> {
        FirebaseHelper.log("🔍 iOS Firestore - queryData() in $collection where $field == $value")

        return try {
            suspendCancellableCoroutine { continuation ->
                val queryValue = when (value) {
                    is String -> value
                    is Number -> value
                    is Boolean -> value
                    else -> value.toString()
                }

                FirebaseHelper.getDocumentsWithCollection(
                    collection,
                    field,
                    queryValue
                ) { documents, error ->
                    if (error != null) {
                        FirebaseHelper.log("❌ iOS Firestore - queryData failed: $error")
                        continuation.resumeWithException(Exception(error))
                    } else if (documents != null) {
                        FirebaseHelper.log("✅ iOS Firestore - queryData success via Swift!, found ${documents.size} documents")
                        // Array<NSDictionary>를 List<Map<String, Any>>로 안전하게 변환
                        val kotlinList = (documents as? List<*>)?.mapNotNull { doc ->
                            (doc as? Map<*, *>)?.mapNotNull { (key, docValue) ->
                                val keyStr = key as? String ?: return@mapNotNull null
                                keyStr to (docValue ?: "null")
                            }?.toMap()
                        } ?: emptyList()
                        continuation.resume(kotlinList)
                    } else {
                        FirebaseHelper.log("🚫 iOS Firestore - queryData: no documents found")
                        continuation.resume(emptyList())
                    }
                }
            }
        } catch (e: Exception) {
            FirebaseHelper.log("❌ iOS Firestore - queryData failed: ${e.message}")
            throw e
        }
    }
}
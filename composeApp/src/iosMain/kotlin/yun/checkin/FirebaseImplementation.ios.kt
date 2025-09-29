package yun.checkin

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import yun.checkin.firebase.FirebaseHelper
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalForeignApi::class)
actual class FirebaseAuth {

    actual fun getCurrentUUID(): String? {
        // Swift의 getCurrentUUID 함수 호출
        return FirebaseHelper.getCurrentUUID()
    }

    actual suspend fun signIn(email: String, password: String): Boolean {
        FirebaseHelper.log("🔐 iOS Firebase Auth - signIn() called for email: $email")

        return try {
            // 실제 Swift FirebaseHelper 사용!
            val result = suspendCancellableCoroutine { continuation ->
                FirebaseHelper.signInWithEmail(email, password) { success, error ->
                    if (error != null) {
                        continuation.resumeWithException(Exception(error))
                    } else {
                        continuation.resume(success)
                    }
                }
            }
            FirebaseHelper.log("✅ iOS Firebase Auth - signIn success: $result")
            result
        } catch (e: Exception) {
            FirebaseHelper.log("❌ iOS Firebase Auth - signIn failed: ${e.message}")
            throw e
        }
    }

    actual suspend fun signUp(email: String, password: String): Boolean {
        FirebaseHelper.log("🔐 iOS Firebase Auth - signUp() called for email: $email")

        return try {
            // 실제 Swift FirebaseHelper 사용!
            val result = suspendCancellableCoroutine<Boolean> { continuation ->
                FirebaseHelper.createUserWithEmail(email, password) { success, error ->
                    if (error != null) {
                        continuation.resumeWithException(Exception(error))
                    } else {
                        continuation.resume(success)
                    }
                }
            }
            FirebaseHelper.log("✅ iOS Firebase Auth - signUp success: $result")
            result
        } catch (e: Exception) {
            FirebaseHelper.log("❌ iOS Firebase Auth - signUp failed: ${e.message}")
            throw e
        }
    }

    actual suspend fun signOut(): Result<Boolean> {
        FirebaseHelper.log("🔐 iOS Firebase Auth - signOut() called")

        return try {
            val result = suspendCancellableCoroutine<Boolean> { continuation ->
                FirebaseHelper.signOut { success, error ->
                    if (error != null) {
                        continuation.resumeWithException(Exception(error))
                    } else {
                        continuation.resume(success)
                    }
                }
            }
            FirebaseHelper.log("✅ iOS Firebase Auth - signOut success: $result")
            Result.success(result)
        } catch (e: Exception) {
            FirebaseHelper.log("❌ iOS Firebase Auth - signOut failed: ${e.message}")
            Result.failure(e)
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

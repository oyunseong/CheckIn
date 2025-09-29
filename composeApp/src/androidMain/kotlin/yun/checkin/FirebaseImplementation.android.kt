package yun.checkin

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import com.google.firebase.auth.FirebaseAuth as AndroidFirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore as AndroidFirebaseFirestore

// Android에서 실제 Firebase Android SDK 사용
actual class FirebaseAuth {

    private val auth = AndroidFirebaseAuth.getInstance()

    actual fun getCurrentUser(): String? {
        println("🔐 Android Firebase Auth - getCurrentUser() called")
        val result = auth.currentUser?.uid
        println("👤 Android Firebase Auth - getCurrentUser result: $result")
        return result
    }

    actual suspend fun signIn(email: String, password: String): Boolean {
        println("🔐 Android Firebase Auth - signIn() called for email: $email")

        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val success = result.user != null
            println("✅ Android Firebase Auth - signIn success: $success")
            success
        } catch (e: Exception) {
            println("❌ Android Firebase Auth - signIn failed: ${e.message}")
            throw e
        }
    }

    actual suspend fun signUp(email: String, password: String): Boolean {
        println("🔐 Android Firebase Auth - signUp() called for email: $email")

        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val success = result.user != null
            println("✅ Android Firebase Auth - signUp success: $success")
            success
        } catch (e: Exception) {
            println("❌ Android Firebase Auth - signUp failed")
            throw e
        }
    }

    actual suspend fun signOut(): Result<Boolean> {
        return suspendCancellableCoroutine { continuation ->
            auth.signOut()
            if (auth.currentUser == null) {
                continuation.resume(Result.success(true))
            } else {
                continuation.resume(Result.failure(Exception("Sign out failed")))
            }
        }
    }
}

actual class FirebaseFirestore {

    private val firestore = AndroidFirebaseFirestore.getInstance()

    actual suspend fun saveData(collection: String, data: Map<String, Any>): Result<String> {
        println("💾 Android Firestore - saveData() to collection: $collection")

        return try {
            val documentRef = firestore.collection(collection).add(data).await()
            println("✅ Android Firestore - saveData success, document ID: ${documentRef.id}")
            Result.success("Document saved successfully with ID: ${documentRef.id}")
        } catch (e: Exception) {
            println("❌ Android Firestore - saveData failed: ${e.message}")
            Result.failure(e)
        }
    }

    actual suspend fun getData(collection: String, documentId: String): Map<String, Any>? {
        println("📄 Android Firestore - getData() from $collection/$documentId")

        return try {
            val document = firestore.collection(collection).document(documentId).get().await()
            if (document.exists()) {
                val data = document.data
                println("✅ Android Firestore - getData success, keys: ${data?.keys}")
                data
            } else {
                println("🚫 Android Firestore - getData: document not found")
                null
            }
        } catch (e: Exception) {
            println("❌ Android Firestore - getData failed: ${e.message}")
            throw e
        }
    }

    actual suspend fun queryData(
        collection: String,
        field: String,
        value: Any
    ): List<Map<String, Any>> {
        println("🔍 Android Firestore - queryData() in $collection where $field == $value")

        return try {
            val querySnapshot = firestore.collection(collection)
                .whereEqualTo(field, value)
                .get()
                .await()

            val documents = querySnapshot.documents.mapNotNull { it.data }
            println("✅ Android Firestore - queryData success, found ${documents.size} documents")
            documents
        } catch (e: Exception) {
            println("❌ Android Firestore - queryData failed: ${e.message}")
            throw e
        }
    }
}
package yun.checkin

import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.google.firebase.auth.FirebaseAuth as GoogleFirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore as GoogleFirebaseFirestore

actual class FirebaseAuth {
    private val auth = GoogleFirebaseAuth.getInstance()

    actual fun getCurrentUser(): String? {
        return auth.currentUser?.uid
    }

    actual suspend fun signIn(email: String, password: String): Boolean =
        suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    continuation.resume(task.isSuccessful)
                }
        }
}

actual class FirebaseFirestore {
    private val firestore = GoogleFirebaseFirestore.getInstance()

    actual suspend fun saveData(collection: String, data: Map<String, Any>): Result<Unit> {
        return firestore.collection(collection)
            .add(data)
            .await()
            .let { Result.success(Unit) }
    }
}
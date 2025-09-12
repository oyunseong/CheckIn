package yun.checkin

import androidx.lifecycle.ViewModel

// Firebase 관련 기능을 위한 expect 선언
expect class FirebaseAuth() {
    fun getCurrentUser(): String?
    suspend fun signIn(email: String, password: String): Boolean
}

internal class AppViewModel : ViewModel() {
    // 이제 Firebase 관련 기능을 사용할 수 있습니다
    private val auth = FirebaseAuth()
    private val firestore = FirebaseFirestore()

    suspend fun signIn(email: String, password: String): Boolean {
        return auth.signIn(email, password)
    }

}
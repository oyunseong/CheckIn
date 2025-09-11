package yun.checkin

// iOS에서는 Firebase iOS SDK를 사용해야 합니다
// 현재는 기본 구현만 제공합니다
actual class FirebaseAuth {
    actual fun getCurrentUser(): String? {
        // TODO: iOS Firebase Auth 구현
        return null
    }

    actual suspend fun signIn(email: String, password: String): Boolean {
        // TODO: iOS Firebase Auth 구현
        return false
    }
}

actual class FirebaseFirestore {
    actual suspend fun saveData(collection: String, data: Map<String, Any>): Boolean {
        // TODO: iOS Firebase Firestore 구현
        return false
    }
}
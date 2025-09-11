package yun.checkin

// JVM/Desktop에서는 Firebase Admin SDK 또는 REST API를 사용해야 합니다
// 현재는 기본 구현만 제공합니다
actual class FirebaseAuth {
    actual fun getCurrentUser(): String? {
        // TODO: JVM Firebase 구현 (Admin SDK 또는 REST API)
        return null
    }

    actual suspend fun signIn(email: String, password: String): Boolean {
        // TODO: JVM Firebase 구현
        return false
    }
}

actual class FirebaseFirestore {
    actual suspend fun saveData(collection: String, data: Map<String, Any>): Boolean {
        // TODO: JVM Firebase 구현
        return false
    }
}
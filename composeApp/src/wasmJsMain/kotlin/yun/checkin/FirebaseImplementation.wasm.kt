package yun.checkin

// WASM/Web에서는 Firebase JavaScript SDK를 사용해야 합니다
// 현재는 기본 구현만 제공합니다
actual class FirebaseAuth {
    actual fun getCurrentUser(): String? {
        // TODO: WASM/Web Firebase 구현 (JavaScript SDK)
        return null
    }

    actual suspend fun signIn(email: String, password: String): Boolean {
        // TODO: WASM/Web Firebase 구현
        return false
    }
}

actual class FirebaseFirestore {
    actual suspend fun saveData(collection: String, data: Map<String, Any>): Boolean {
        // TODO: WASM/Web Firebase 구현
        return false
    }
}
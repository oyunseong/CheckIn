package yun.checkin

// WASM/Web에서는 Firebase JavaScript SDK를 사용해야 합니다
// 현재는 기본 구현만 제공합니다
actual class FirebaseAuth {
    actual fun getCurrentUUID(): String? {
        // TODO: WASM/Web Firebase 구현 (JavaScript SDK)
        return null
    }

    actual suspend fun signIn(email: String, password: String): Boolean {
        // TODO: WASM/Web Firebase 구현
        return false
    }

    actual suspend fun signUp(email: String, password: String): Boolean {
        return false
    }

    actual suspend fun signOut(): Result<Boolean> {
        return Result.failure(Exception("Not implemented"))
    }
}

actual class FirebaseFirestore {
    actual suspend fun saveData(collection: String, data: Map<String, Any>): Result<String> {
        return Result.failure(Exception("Not implemented"))
    }

    actual suspend fun getData(collection: String, documentId: String): Map<String, Any>? {
        return null
    }

    actual suspend fun queryData(
        collection: String,
        field: String,
        value: Any
    ): List<Map<String, Any>> {
        return emptyList()
    }
}
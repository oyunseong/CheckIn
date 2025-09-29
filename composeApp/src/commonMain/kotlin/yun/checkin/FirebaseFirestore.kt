package yun.checkin

expect class FirebaseFirestore() {
    suspend fun saveData(collection: String, data: Map<String, Any>): Result<String>

    suspend fun getData(collection: String, documentId: String): Map<String, Any>?

    suspend fun queryData(collection: String, field: String, value: Any): List<Map<String, Any>>
}
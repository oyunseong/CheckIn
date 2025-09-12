package yun.checkin

expect class FirebaseFirestore() {
    suspend fun saveData(collection: String, data: Map<String, Any>): Result<Unit>

    suspend fun getData(collection: String, documentId: String): Map<String, Any>?

    suspend fun getDocuments(collection: String, field: String, value: Any): List<Map<String, Any?>?>
}
package yun.checkin

expect class FirebaseAuth() {
    fun getCurrentUUID(): String?
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun signOut() : Result<Boolean>
}
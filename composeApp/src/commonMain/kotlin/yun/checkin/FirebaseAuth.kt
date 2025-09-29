package yun.checkin

expect class FirebaseAuth() {
    fun getCurrentUser(): String?
    suspend fun signIn(email: String, password: String): Boolean
    suspend fun signUp(email: String, password: String): Boolean
    suspend fun signOut() : Result<Boolean>
}
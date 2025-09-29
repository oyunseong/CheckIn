package yun.checkin.core.data_api

/**
 * Firebase 인증 결과를 나타내는 sealed class
 */
sealed class AuthResult {
    data object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

/**
 * 사용자 정보를 나타내는 data class
 */
data class User(
    val uid: String,
    val email: String?
)

/**
 * Firebase 인증 관련 기능을 정의하는 인터페이스
 */
interface AuthRepository {
    /**
     * 현재 로그인된 사용자 정보를 반환
     */
    suspend fun getCurrentUser(): User?

    /**
     * 이메일과 비밀번호로 로그인
     */
    suspend fun signInWithEmail(email: String, password: String): AuthResult

    /**
     * 이메일과 비밀번호로 회원가입
     */
    suspend fun signUpWithEmail(email: String, password: String): AuthResult

    /**
     * 로그아웃
     */
    suspend fun signOut()

    /**
     * 현재 사용자가 로그인되어 있는지 확인
     */
    suspend fun isSignedIn(): Boolean
}
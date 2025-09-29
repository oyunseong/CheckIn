package yun.checkin.core.data

import yun.checkin.FirebaseAuth
import yun.checkin.core.data_api.AuthRepository
import yun.checkin.core.data_api.AuthResult
import yun.checkin.core.data_api.User

/**
 * Android용 Firebase Auth 구현체
 */
class AuthRepositoryImpl : AuthRepository {
    private val firebaseAuth = FirebaseAuth()

    override suspend fun getCurrentUser(): User? {
        val firebaseUser = firebaseAuth.getCurrentUUID()
        return firebaseUser?.let {
            User(
                uid = it, // TODO UUID
                email = it, // TODO EMAIL
            )
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): AuthResult {
        return try {
            firebaseAuth.signIn(email, password)
            return AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "로그인에 실패했습니다.")
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String): AuthResult {
        return try {
            firebaseAuth.signUp(email, password)
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "회원가입에 실패했습니다.")
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun isSignedIn(): Boolean {
        println("uuid : ${firebaseAuth.getCurrentUUID()}")
        return firebaseAuth.getCurrentUUID() != null
    }
}
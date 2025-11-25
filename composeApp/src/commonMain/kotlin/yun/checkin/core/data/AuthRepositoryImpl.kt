package yun.checkin.core.data

import yun.checkin.FirebaseAuth
import yun.checkin.FirebaseFirestore
import yun.checkin.core.data_api.AuthRepository
import yun.checkin.core.data_api.AuthResult
import yun.checkin.core.data_api.User

/**
 * Android용 Firebase Auth 구현체
 */
class AuthRepositoryImpl(
    private val firestore: FirebaseFirestore = FirebaseFirestore()
) : AuthRepository {
    private val firebaseAuth = FirebaseAuth()

    override suspend fun getCurrentUser(): User? {
        val firebaseUser = firebaseAuth.getCurrentUUID()

        return firebaseUser?.let {
            User(
                uid = it,
                name = getUserName(it).getOrNull(),
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

    override suspend fun signUpWithEmail(
        email: String, password: String,
        name: String
    ): AuthResult {
        return try {
            firebaseAuth.signUp(email, password)

            // 회원가입 성공 후 사용자 이름 저장
            val uid = firebaseAuth.getCurrentUUID()
            if (uid != null) {
                saveUserName(uid, name)
            }

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

    override suspend fun saveUserName(uid: String, name: String): Result<Unit> {
        return try {
            val saveResult = firestore.saveData(
                collection = "users",
                data = mapOf(
                    "uid" to uid,
                    "name" to name
                )
            )
            if (saveResult.isSuccess) {
                Result.success(Unit)
            } else {
                Result.failure(
                    saveResult.exceptionOrNull() ?: Exception("Failed to save user name")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserName(uid: String): Result<String?> {
        return try {
            val documents = firestore.queryData(
                collection = "users",
                field = "uid",
                value = uid
            )

            val name = documents.firstOrNull()?.get("name") as? String
            Result.success(name)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isUserInGroup(uid: String): Boolean {
        return try {
            val documents = firestore.queryData(
                collection = "users",
                field = "uid",
                value = uid
            )

            return documents.firstOrNull()?.get("vw_user") as? Boolean == true
        } catch (e: Exception) {
            false
        }
    }


}
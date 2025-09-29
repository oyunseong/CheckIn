package yun.checkin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Firebase 관련 기능을 위한 expect 선언
expect class FirebaseAuth() {
    fun getCurrentUser(): String?
    suspend fun signIn(email: String, password: String): Boolean
}

internal class AppViewModel : ViewModel() {
    // Firebase 인스턴스들
    private val auth = FirebaseAuth()
    private val firestore = FirebaseFirestore()

    // UI 상태 관리
    private val _testResult = MutableStateFlow("")
    val testResult: StateFlow<String> = _testResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /**
     * 기본 로그인 기능
     */
    suspend fun signIn(email: String, password: String): Boolean {
        return auth.signIn(email, password)
    }

    /**
     * 현재 사용자 정보 조회
     */
    fun getCurrentUser(): String? {
        return auth.getCurrentUser()
    }

    /**
     * Swift Logger 테스트
     */
    fun testSwiftLogger() {
        _testResult.value = "🧪 Testing Swift Logger...\n"
        LoggerTest().testSwiftLogger()
        _testResult.value += "✅ Logger test completed! Check iOS console for 🍎 messages.\n"
        _testResult.value += "📱 In iOS: Look for messages with '🍎 [SwiftLog]' prefix\n"
        _testResult.value += "🤖 In Android: Only Kotlin println messages will appear"
    }

    /**
     * Firebase Auth 테스트 (Swift Logger 포함)
     */
    suspend fun testFirebaseWithLogger() {
        _isLoading.value = true
        try {
            // getCurrentUser 호출 (내부에서 Swift Logger 사용)
            val currentUser = getCurrentUser()

            // signIn 호출 (내부에서 Swift Logger 사용)  
            val signInResult = signIn("test@example.com", "password123")

            _testResult.value = """
                🔐 Firebase Auth Test Results:
                👤 Current User: $currentUser
                🔑 Sign In Result: $signInResult
                
                📱 Check iOS console for detailed logs with 🍎 prefix!
                💡 All Firebase operations now include Swift logging
            """.trimIndent()

        } catch (e: Exception) {
            _testResult.value = "❌ Firebase Test Error: ${e.message}"
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * 테스트 결과 초기화
     */
    fun clearTestResult() {
        _testResult.value = ""
    }

    /**
     * 데이터 저장 테스트
     */
    suspend fun testSaveData(): Result<Unit> {
        return firestore.saveData(
            "test", mapOf(
                "timestamp" to 1234567890L,
                "platform" to "multiplatform"
            )
        )
    }

    /**
     * 문서 조회 테스트
     */
    suspend fun testGetDocument(documentId: String): Map<String, Any>? {
        return firestore.getData("test", documentId)
    }

    /**
     * 문서 검색 테스트
     */
    suspend fun testQueryDocuments(field: String, value: Any): List<Map<String, Any?>?> {
        return firestore.getDocuments("test", field, value)
    }
}
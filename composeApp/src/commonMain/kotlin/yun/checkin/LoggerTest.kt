package yun.checkin

/**
 * Swift Logger 테스트를 위한 클래스
 * iOS에서 실행하면 Swift Logger.log()가 호출됩니다.
 */
class LoggerTest {

    fun testSwiftLogger() {
        // iOS에서만 실제 Swift Logger 호출
        // Android에서는 단순 출력
        logMessage("🧪 Testing Swift Logger from Kotlin!")
        logMessage("✨ This message should appear in iOS console with 🍎 prefix")
        logMessage("🔗 cinterop is working successfully!")
    }

    private fun logMessage(message: String) {
        // 플랫폼별 로깅
        println("Kotlin: $message") // 모든 플랫폼에서 기본 출력
    }
}
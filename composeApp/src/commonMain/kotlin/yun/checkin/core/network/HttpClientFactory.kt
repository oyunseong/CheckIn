package yun.checkin.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Factory for creating configured HttpClient instances
 */
object HttpClientFactory {
    /**
     * Creates a configured HttpClient with JSON serialization support and logging
     */
    fun create(): HttpClient {
        return HttpClient {
            // JSON 직렬화/역직렬화 설정
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            // HTTP 로깅 설정
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("🌐 HTTP: $message")
                    }
                }
                // 로그 레벨 설정
                // ALL: 모든 정보 (헤더, 바디 포함)
                // HEADERS: 헤더만
                // BODY: 바디만
                // INFO: 기본 정보만
                // NONE: 로그 없음
                level = LogLevel.ALL
            }

            // 예외 처리 로깅
            expectSuccess = false // 실패 응답도 예외를 던지지 않고 처리
        }
    }
}

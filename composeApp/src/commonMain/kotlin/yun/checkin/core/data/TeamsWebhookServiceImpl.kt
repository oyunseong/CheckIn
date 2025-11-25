package yun.checkin.core.data

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import yun.checkin.core.data_api.TeamsWebhookMessage
import yun.checkin.core.data_api.TeamsWebhookService

/**
 * Implementation of TeamsWebhookService using Ktor HTTP client
 * @param webhookUrl The Microsoft Teams webhook URL
 * @param httpClient Shared HttpClient instance for network requests
 */
class TeamsWebhookServiceImpl(
    private val webhookUrl: String,
    private val httpClient: HttpClient
) : TeamsWebhookService {

    override suspend fun sendMessage(name: String, text: String, isCheckIn: Boolean): Result<Unit> {
        return try {
            val message = TeamsWebhookMessage(
                name = name,
                text = text,
                isCheckIn = isCheckIn
            )

            println("📤 Teams Webhook 요청 시작")
            println("   URL: $webhookUrl")
            println("   Message: name='$name', text='$text'")
            val url = "https://default007ba5a3613b4f65955a6767eb3679.93.environment.api.powerplatform.com:443/powerautomate/automations/direct/workflows/ff179b60b97547d187d300c7c150fc9f/triggers/manual/paths/invoke?api-version=1&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=_S9FrFuuB3Kb_dR0Nv5_IPJWB98DPkV_95IevyzCxXU"
            val response: HttpResponse = httpClient.post(url) {
                contentType(ContentType.Application.Json)
                setBody(message)
            }


            //https://default007ba5a3613b4f65955a6767eb3679.93.environment.api.powerplatform.com:443/powerautomate/automations/direct/workflows/ff179b60b97547d187d300c7c150fc9f/triggers/manual/paths/invoke?api-version=1&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=_S9FrFuuB3Kb_dR0Nv5_IPJWB98DPkV_95IevyzCxXU
            if (response.status.isSuccess()) {
                println("✅ Teams Webhook 성공: ${response.status}")
                Result.success(Unit)
            } else {
                val errorBody = try {
                    response.bodyAsText()
                } catch (e: Exception) {
                    "Unable to read response body"
                }
                val errorMessage =
                    "Teams webhook failed - Status: ${response.status}, Body: $errorBody"
                println("❌ $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            println("💥 Teams Webhook 예외 발생: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

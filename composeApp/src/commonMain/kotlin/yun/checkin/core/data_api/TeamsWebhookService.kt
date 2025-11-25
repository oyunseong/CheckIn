package yun.checkin.core.data_api

import kotlinx.serialization.Serializable

/**
 * Data model for Teams webhook message
 */
@Serializable
data class TeamsWebhookMessage(
    val name: String,
    val text: String,
    val isCheckIn: Boolean
)

/**
 * Service interface for sending notifications to Microsoft Teams via webhook
 */
interface TeamsWebhookService {
    /**
     * Sends a message to Teams webhook
     * @param name Name of the person
     * @param text Message text
     * @param isCheckIn True for check-in, false for check-out
     * @return Result with Unit on success, or exception on failure
     */
    suspend fun sendMessage(name: String, text: String, isCheckIn: Boolean = true): Result<Unit>
}

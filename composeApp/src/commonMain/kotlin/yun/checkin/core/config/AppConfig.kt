package yun.checkin.core.config

/**
 * App configuration interface
 * Platform-specific implementations should provide actual values
 */
interface AppConfig {
    /**
     * Microsoft Teams webhook URL
     */
    val teamsWebhookUrl: String
}

/**
 * Expected platform-specific configuration
 */
expect object PlatformConfig : AppConfig {
    override val teamsWebhookUrl: String
}

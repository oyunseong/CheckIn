package yun.checkin.core.config

import yun.checkin.BuildConfig

/**
 * Android platform configuration
 * Reads configuration from BuildConfig
 */
actual object PlatformConfig : AppConfig {
    actual override val teamsWebhookUrl: String
        get() = BuildConfig.TEAMS_WEBHOOK_URL

}

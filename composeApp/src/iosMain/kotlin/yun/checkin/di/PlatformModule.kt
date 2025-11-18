package yun.checkin.di

import io.ktor.client.HttpClient
import org.koin.dsl.module
import yun.checkin.core.config.PlatformConfig
import yun.checkin.core.data.IOSNotificationManager
import yun.checkin.core.data.TeamsWebhookServiceImpl
import yun.checkin.core.data_api.NotificationManager
import yun.checkin.core.data_api.TeamsWebhookService
import yun.checkin.core.network.HttpClientFactory

val iosPlatformModule = module {
    single<HttpClient> {
        HttpClientFactory.create()
    }

    single<NotificationManager> {
        IOSNotificationManager()
    }

    single<TeamsWebhookService> {
        TeamsWebhookServiceImpl(
            webhookUrl = PlatformConfig.teamsWebhookUrl,
            httpClient = get()
        )
    }
}
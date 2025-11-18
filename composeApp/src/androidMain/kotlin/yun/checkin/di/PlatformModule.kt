package yun.checkin.di

import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import yun.checkin.core.config.PlatformConfig
import yun.checkin.core.data.AndroidNotificationManager
import yun.checkin.core.data.TeamsWebhookServiceImpl
import yun.checkin.core.data_api.NotificationManager
import yun.checkin.core.data_api.TeamsWebhookService
import yun.checkin.core.network.HttpClientFactory

val androidPlatformModule = module {
    single<HttpClient> {
        HttpClientFactory.create()
    }

    single<NotificationManager> {
        AndroidNotificationManager(androidContext())
    }

    single<TeamsWebhookService> {
        TeamsWebhookServiceImpl(
            webhookUrl = PlatformConfig.teamsWebhookUrl,
            httpClient = get()
        )
    }
}
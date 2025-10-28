package yun.checkin.di

import org.koin.dsl.module
import yun.checkin.core.data.IOSNotificationManager
import yun.checkin.core.data_api.NotificationManager

val iosPlatformModule = module {
    single<NotificationManager> { IOSNotificationManager() }
}
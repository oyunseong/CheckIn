package yun.checkin.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import yun.checkin.core.data.AndroidNotificationManager
import yun.checkin.core.data_api.NotificationManager

val androidPlatformModule = module {
    single<NotificationManager> { AndroidNotificationManager(androidContext()) }
}
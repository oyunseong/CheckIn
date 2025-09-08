package yun.checkin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CheckInApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(
            checkInAppDeclaration {
                androidContext(this@CheckInApplication)
            },
        )
    }
}

package yun.checkin

import android.app.Application
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import yun.checkin.di.androidPlatformModule

class CheckInApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        startKoin(
            checkInAppDeclaration {
                androidContext(this@CheckInApplication)
                modules(androidPlatformModule)
            },
        )
    }
}

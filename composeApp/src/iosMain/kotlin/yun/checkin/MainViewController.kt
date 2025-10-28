package yun.checkin

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.compose.KoinApplication
import yun.checkin.di.iosPlatformModule

fun MainViewController() = ComposeUIViewController {
    KoinApplication(
        application = checkInAppDeclaration {
            modules(iosPlatformModule)
        }
    ) {
        App()
    }
}
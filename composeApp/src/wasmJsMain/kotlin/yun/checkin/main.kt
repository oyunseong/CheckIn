package yun.checkin

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin(
        checkInAppDeclaration()
    )
    
    ComposeViewport(document.body!!) {
        App()
    }
}


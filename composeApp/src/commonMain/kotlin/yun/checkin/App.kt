package yun.checkin

import RootComponent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import yun.checkin.shared.ui.RootContent

@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        RootContent(root)
    }
}

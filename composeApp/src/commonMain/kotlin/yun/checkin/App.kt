package yun.checkin

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import yun.checkin.shared.root.RootComponent
import yun.checkin.ui.login.LoginScreen
import yun.checkin.ui.main.MainScreen

@Composable
fun App(root: RootComponent) {
    MaterialTheme {
        val stack by root.stack.subscribeAsState()
        Children(stack = stack) {
            when (val child = it.instance) {
                is RootComponent.Child.Login -> LoginScreen(child.component)
                is RootComponent.Child.Main -> MainScreen(child.component)
            }
        }
    }
}
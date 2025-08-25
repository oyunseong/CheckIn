package yun.checkin.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import yun.checkin.shared.main.MainComponent
import yun.checkin.ui.history.HistoryScreen
import yun.checkin.ui.home.HomeScreen

@Composable
fun MainScreen(component: MainComponent) {
    val stack by component.stack.subscribeAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = stack.active.instance is MainComponent.Child.Home,
                    onClick = component::onHomeTabClicked,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = stack.active.instance is MainComponent.Child.History,
                    onClick = component::onHistoryTabClicked,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "History"
                        )
                    },
                    label = { Text("History") }
                )
            }
        }
    ) {
        Children(
            stack = stack,
            modifier = Modifier.padding(it)
        ) {
            when (val child = it.instance) {
                is MainComponent.Child.Home -> HomeScreen(child.component)
                is MainComponent.Child.History -> HistoryScreen(child.component)
            }
        }
    }
}

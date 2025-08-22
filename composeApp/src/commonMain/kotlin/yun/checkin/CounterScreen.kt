package yun.checkin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yun.checkin.shared.counter.CounterComponent

@Composable
fun CounterScreen(component: CounterComponent) {
    Column {
        Text("Count: ${component.uiState.value.count}")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { component.increment() }) {
            Text("Increment")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = { component.onNavigateToHome() }) {
            Text("Go to Home")
        }
    }
}

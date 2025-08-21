package yun.checkin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import yun.checkin.shared.CounterViewModel

@Composable
fun App(viewModel: CounterViewModel = koinInject()) {
    MaterialTheme {
        val uiState by viewModel.uiState.collectAsState()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Count: ${uiState.count}")
            Spacer(Modifier.height(16.dp))
            Button(onClick = { viewModel.increment() }) {
                Text("Increment")
            }
        }
    }
}

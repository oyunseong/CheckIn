package yun.checkin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yun.checkin.shared.home.HomeComponent

@Composable
fun HomeScreen(component: HomeComponent) {
    Column {
        Text("Welcome Home!")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { component.onBack() }) {
            Text("Go Back")
        }
    }
}

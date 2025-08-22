package yun.checkin.shared.ui

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import yun.checkin.shared.home.HomeComponent


@Composable
fun HomeContent(component: HomeComponent, modifier: Modifier = Modifier) {

    Button(onClick = {component.onBack()}, content = {})
}
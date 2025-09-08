package yun.checkin.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import checkin.composeapp.generated.resources.Res
import checkin.composeapp.generated.resources.ic_more
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    currentTab: String?,
    onClick: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEach {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onClick(it) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_more),
                    tint = if (currentTab == it) Color.Black else Color.Gray,
                    contentDescription = it
                )
            }

//            Text(
//                modifier = Modifier.weight(1f)
//                    .clickable { onClick(it) },
//                text = it,
//                textAlign = TextAlign.Center,
//                color = if (currentTab == it) Color.Black else Color.Gray
//            )
        }
    }
}
package yun.checkin.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import checkin.composeapp.generated.resources.Res
import checkin.composeapp.generated.resources.ic_calendar
import checkin.composeapp.generated.resources.ic_exit
import checkin.composeapp.generated.resources.ic_settings
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import world.pople.oper.core.designsystem.theme.White

enum class MainTabs(val icon: DrawableResource) {
    Home(Res.drawable.ic_exit),
    History(Res.drawable.ic_calendar),
    Setting(Res.drawable.ic_settings)
}

@Composable
fun BottomNavigator(
    modifier: Modifier = Modifier,
    tabs: List<MainTabs>,
    currentTab: String?,
    onClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        tabs.forEachIndexed { i, it ->
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(White)
                    .size(58.dp)
                    .clickable { onClick(it.name) },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(it.icon),
                    tint = if (currentTab == it.name) Color.Black else Color.Gray,
                    contentDescription = it.name
                )
            }
            if (i != tabs.lastIndex) Spacer(modifier = Modifier.height(22.dp))
        }
    }
}
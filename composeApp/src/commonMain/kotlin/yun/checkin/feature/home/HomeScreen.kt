package yun.checkin.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import yun.checkin.feature.checkin.CheckInScreen
import yun.checkin.feature.history.HistoryScreen
import yun.checkin.feature.setting.SettingScreen

@Composable
fun HomeScreen(
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit,
    onLogout: () -> Unit,
) {
    HomeScreenContent(
        padding = padding,
        onShowSnackBar = onShowSnackBar,
        onLogout = onLogout,
    )
}

@Composable
fun HomeScreenContent(
    padding: PaddingValues = PaddingValues(),
    onShowSnackBar: (String) -> Unit = {},
    onLogout: () -> Unit = {},
) {
    val tabs = listOf("chkecin", "history", "setting")
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        state = pagerState,
        verticalAlignment = Alignment.Top,
    ) { page ->
        when (page) {
            0 -> CheckInScreen(
                padding = padding,
                onShowSnackBar = onShowSnackBar
            )

            1 -> HistoryScreen(
                padding = padding
            )

            2 -> SettingScreen(
                padding = padding,
                onLogout = onLogout
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent()
}

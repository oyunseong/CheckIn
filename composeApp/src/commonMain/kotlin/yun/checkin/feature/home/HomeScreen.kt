package yun.checkin.feature.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
) {
    HomeScreenContent(
        padding = padding,
        onShowSnackBar = onShowSnackBar
    )
}

@Composable
fun HomeScreenContent(
    padding: PaddingValues = PaddingValues(),
    onShowSnackBar: (String) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
//    val tabs = uiState.tabs
    val tabs = listOf("1", "2", "3")
    // 탭 관련 상태
    val pagerState = rememberPagerState(pageCount = { tabs.size })
//    val selectedTab = tabs[pagerState.currentPage]
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
                padding = padding
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent()
}

package yun.checkin.feature.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import yun.checkin.feature.auth.AuthViewModel
import yun.checkin.feature.history.HistoryScreen
import yun.checkin.feature.history.HistoryViewModel
import yun.checkin.feature.home.HomeScreen
import yun.checkin.feature.setting.SettingScreen


//@Composable
//expect fun MainScreen(modifier: Modifier = Modifier)

@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = koinViewModel(),
) {
    val mainTabs = listOf("Home", "History", "Setting")
    val snackbarHostState = remember { SnackbarHostState() }
    val currentTab = navController.currentBackStackEntryAsState()

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect {
            println("route : ${it.destination.route}")
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomBar(
                modifier = Modifier.navigationBarsPadding(),
                tabs = mainTabs,
                currentTab = currentTab.value?.destination?.route,
                onClick = {
                    if (currentTab.value?.destination?.route == it) return@BottomBar
                    navController.navigate(it) {
                        // 메인 탭들은 백스택에서 재사용하도록 설정
                        popUpTo("Home") {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Home",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
        ) {
            mainNavGraph(
                padding = innerPadding,
                navController = navController,
                authViewModel = authViewModel
            )
        }
    }
}

fun NavGraphBuilder.mainNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    authViewModel: AuthViewModel,
) {
    composable(
        route = "Home",
    ) {
        HomeScreen(
            padding = padding,
            modifier = Modifier,
        )
    }

    composable(
        route = "History",
    ) {
        HistoryScreen(
            padding = padding,
            modifier = Modifier,
            viewModel = koinViewModel<HistoryViewModel>()
        )
    }

    composable(
        route = "Setting",
//        enterTransition = verticalEnterTransition,
//        exitTransition = null,
//        popEnterTransition = null,
//        popExitTransition = verticalExitTransition,
    ) {
        SettingScreen(
            modifier = Modifier,
            padding = padding,
            viewModel = authViewModel
        )
    }
}
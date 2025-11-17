package yun.checkin.feature.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import yun.checkin.feature.auth.AuthViewModel
import yun.checkin.feature.history.HistoryScreen
import yun.checkin.feature.checkin.CheckInScreen
import yun.checkin.feature.setting.SettingScreen

@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel = koinViewModel(),
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val currentTab = navController.currentBackStackEntryAsState()
    val coroutineScope = rememberCoroutineScope()
    val onShowSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.collect {
            println("route : ${it.destination.route}")
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackBarHostState) },
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
                authViewModel = authViewModel,
                onShowSnackBar = onShowSnackBar,
            )
        }
        Box(
            modifier = Modifier.fillMaxSize().padding(end = 22.dp, bottom = 19.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            BottomNavigator(
                modifier = Modifier.navigationBarsPadding(),
                tabs = MainTabs.entries,
                currentTab = currentTab.value?.destination?.route,
                onClick = {
                    if (currentTab.value?.destination?.route == it) return@BottomNavigator
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
    }
}

fun NavGraphBuilder.mainNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
    authViewModel: AuthViewModel,
    onShowSnackBar: (String) -> Unit,
) {
    composable(
        route = "Home",
    ) {
        CheckInScreen(
            padding = padding,
            modifier = Modifier,
            onShowSnackBar = onShowSnackBar,
        )
    }

    composable(
        route = "History",
    ) {
        HistoryScreen(
            padding = padding,
            modifier = Modifier,
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
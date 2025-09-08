package yun.checkin.feature.main

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import yun.checkin.feature.home.HomeScreen


//@Composable
//expect fun MainScreen(modifier: Modifier = Modifier)

@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val mainTabs = listOf("Home", "Setting")
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
                    navController.navigate(it)
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
            )
        }
    }
}

fun NavGraphBuilder.mainNavGraph(
    padding: PaddingValues,
    navController: NavHostController,
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
        route = "Setting",
//        enterTransition = verticalEnterTransition,
//        exitTransition = null,
//        popEnterTransition = null,
//        popExitTransition = verticalExitTransition,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
                .clickable {
                    navController.navigate("Home")
                }
        ) {

        }
    }
}
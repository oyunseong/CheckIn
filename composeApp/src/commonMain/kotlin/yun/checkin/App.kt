package yun.checkin

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import yun.checkin.core.data.AuthRepositoryImpl
import yun.checkin.core.data.FakeCheckInRepositoryImpl
import yun.checkin.core.data_api.AuthRepository
import yun.checkin.core.data_api.CheckInRepository
import yun.checkin.feature.auth.AuthViewModel
import yun.checkin.feature.auth.LoginScreen
import yun.checkin.feature.auth.SignUpScreen
import yun.checkin.feature.history.HistoryViewModel
import yun.checkin.feature.home.HomeViewModel
import yun.checkin.feature.main.MainContent

@Composable
internal fun App(
    viewModel: AppViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel(),
) {
    val authUiState by authViewModel.uiState.collectAsState()
    val navController = rememberNavController()

    LaunchedEffect(authUiState.isSignedIn) {
        if (!authUiState.isSignedIn) {
            navController.navigate("login") {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    MaterialTheme {
        NavHost(
            navController = navController,
            startDestination = if (authUiState.isSignedIn) "main" else "login",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None }
        ) {
            // 로그인 화면
            composable("login") {
                LoginScreen(
                    viewModel = authViewModel,
                    onNavigateToSignUp = {
                        navController.navigate("signup")
                    },
                    onLoginSuccess = {
                        // 백스택을 모두 제거하고 메인 화면으로 이동
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            // 회원가입 화면
            composable("signup") {
                SignUpScreen(
                    viewModel = authViewModel,
                    onSignUpSuccess = {
                        // 백스택을 모두 제거하고 메인 화면으로 이동
                        navController.navigate("main") {
                            popUpTo("login") { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("main") {
                MainContent(authViewModel = authViewModel)
            }
        }
    }
}

internal val appModule = module {
    single<CheckInRepository> { FakeCheckInRepositoryImpl() }
    single<AuthRepository> { AuthRepositoryImpl() }
    viewModelOf(::HomeViewModel)
    viewModelOf(::HistoryViewModel)
    viewModelOf(::AppViewModel)
    viewModelOf(::AuthViewModel)
}

internal fun checkInAppDeclaration(
    additionalDeclaration: KoinApplication.() -> Unit = {},
): KoinAppDeclaration = {
    modules(appModule)
    additionalDeclaration()
}
package yun.checkin

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import yun.checkin.core.data_api.CheckInRepository
import yun.checkin.core.data.FakeCheckInRepositoryImpl
import yun.checkin.feature.history.HistoryViewModel
import yun.checkin.feature.home.HomeViewModel
import yun.checkin.feature.main.MainContent

@Composable
internal fun App(
    viewModel: AppViewModel = koinViewModel(),
) {
    MaterialTheme {
        MainContent()
    }
}

internal val appModule = module {
    single<CheckInRepository> { FakeCheckInRepositoryImpl() }
    viewModelOf(::HomeViewModel)
    viewModelOf(::HistoryViewModel)
    viewModelOf(::AppViewModel)

}

internal fun checkInAppDeclaration(
    additionalDeclaration: KoinApplication.() -> Unit = {},
): KoinAppDeclaration = {
    modules(appModule)
    additionalDeclaration()
}
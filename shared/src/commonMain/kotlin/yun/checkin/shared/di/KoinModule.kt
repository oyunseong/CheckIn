package yun.checkin.shared.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import yun.checkin.shared.CounterViewModel

val appModule = module {
    factory { CounterViewModel() }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(appModule)
}

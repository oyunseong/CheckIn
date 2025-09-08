package yun.checkin.di

import org.koin.dsl.module

class Greeter(private val greeting: String) {
    fun greet() = greeting
}

val appModule = module {
    single { Greeter("Hello, Koin!") }
}

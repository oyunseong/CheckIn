package yun.checkin.shared.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import yun.checkin.shared.ui.login.DefaultLoginComponent
import yun.checkin.shared.ui.login.LoginComponent
import yun.checkin.shared.main.DefaultMainComponent
import yun.checkin.shared.main.MainComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Login(val component: LoginComponent) : Child()
        data class Main(val component: MainComponent) : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Login,
            handleBackButton = true,
            childFactory = ::createChild,
        )

    private fun createChild(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.Login -> RootComponent.Child.Login(loginComponent(componentContext))
            is Config.Main -> RootComponent.Child.Main(mainComponent(componentContext))
        }

    private fun loginComponent(componentContext: ComponentContext): LoginComponent =
        DefaultLoginComponent(
            componentContext = componentContext,
            onNavigateToMain = { navigation.replaceAll(Config.Main) },
        )

    private fun mainComponent(componentContext: ComponentContext): MainComponent =
        DefaultMainComponent(
            componentContext = componentContext,
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Login : Config

        @Serializable
        data object Main : Config
    }
}

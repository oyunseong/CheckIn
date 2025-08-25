package yun.checkin.shared.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import yun.checkin.shared.ui.history.DefaultHistoryComponent
import yun.checkin.shared.ui.history.HistoryComponent
import yun.checkin.shared.ui.home.DefaultHomeComponent
import yun.checkin.shared.ui.home.HomeComponent

interface MainComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onHomeTabClicked()
    fun onHistoryTabClicked()

    sealed class Child {
        data class Home(val component: HomeComponent) : Child()
        data class History(val component: HistoryComponent) : Child()
    }
}

class DefaultMainComponent(
    componentContext: ComponentContext
) : MainComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, MainComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Home,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, componentContext: ComponentContext): MainComponent.Child {
        return when (config) {
            is Config.Home -> MainComponent.Child.Home(DefaultHomeComponent(componentContext))
            is Config.History -> MainComponent.Child.History(DefaultHistoryComponent(componentContext))
        }
    }

    override fun onHomeTabClicked() {
        navigation.replaceCurrent(Config.Home)
    }

    override fun onHistoryTabClicked() {
        navigation.replaceCurrent(Config.History)
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Home : Config

        @Serializable
        data object History : Config
    }
}

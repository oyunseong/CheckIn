package yun.checkin.shared.ui.login

import com.arkivanov.decompose.ComponentContext

interface LoginComponent {
    fun onLoginClicked()
}

class DefaultLoginComponent(
    private val componentContext: ComponentContext,
    private val onNavigateToMain: () -> Unit
) : LoginComponent, ComponentContext by componentContext {

    override fun onLoginClicked() {
        onNavigateToMain()
    }
}

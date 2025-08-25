package yun.checkin.shared.ui.home

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface HomeComponent {
    val uiState: StateFlow<HomeUiState>
    fun onButtonClicked()
}

data class HomeUiState(
    val text: String = "Hello from Home!"
)

class DefaultHomeComponent(
    componentContext: ComponentContext
) : HomeComponent, ComponentContext by componentContext {

    private val _uiState = MutableStateFlow(HomeUiState())
    override val uiState: StateFlow<HomeUiState> = _uiState

    override fun onButtonClicked() {
        _uiState.value = _uiState.value.copy(text = "Button Clicked!")
    }
}

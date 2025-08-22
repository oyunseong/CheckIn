package yun.checkin.shared.counter

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface CounterComponent {
    val uiState: StateFlow<CounterState>
    fun increment()
    fun onNavigateToHome()
}

data class CounterState(
    val count: Int = 0
)

class DefaultCounterComponent(
    private val componentContext: ComponentContext,
    private val onNavigateToHome: () -> Unit
) : CounterComponent, ComponentContext by componentContext {

    private val _uiState = MutableStateFlow(CounterState())
    override val uiState: StateFlow<CounterState> = _uiState.asStateFlow()

    override fun increment() {
        _uiState.update { it.copy(count = it.count + 1) }
    }

    override fun onNavigateToHome() {
        onNavigateToHome.invoke()
    }
}
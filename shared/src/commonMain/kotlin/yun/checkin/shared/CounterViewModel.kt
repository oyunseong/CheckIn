package yun.checkin.shared

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// MVI의 State
data class CounterState(
    val count: Int = 0
)

// MVI의 ViewModel/Store 역할
class CounterViewModel {

    private val _uiState = MutableStateFlow(CounterState())
    val uiState: StateFlow<CounterState> = _uiState.asStateFlow()

    // MVI의 Intent 처리
    fun increment() {
        _uiState.update { it.copy(count = it.count + 1) }
    }
}

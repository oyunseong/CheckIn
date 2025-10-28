package yun.checkin.feature.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yun.checkin.core.data_api.CheckInRepository
import yun.checkin.feature.history.model.HistoryUiEvent
import yun.checkin.feature.history.model.HistoryUiState

class HistoryViewModel(
    private val checkInRepository: CheckInRepository
) : ViewModel() {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        println("CoroutineExceptionHandler: $throwable")
    }

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()


    init {
        loadHistory()
    }

    fun onUiEvent(event: HistoryUiEvent) {
        when (event) {
            HistoryUiEvent.OnRefresh -> {
                viewModelScope.launch(coroutineExceptionHandler) {
                    try {
                        emitUiState { copy(isRefreshing = true) }
                        loadHistory()
                    } catch (e: Exception) {
                        println("error : ${e.message}")
                    } finally {
                        println("finally")
                        delay(10) // delay가 없으면 refresh indicator가 안 사라짐
                        emitUiState { copy(isRefreshing = false) }
                    }
                }
            }
        }

    }

    fun loadHistory() {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(isLoading = true, error = null)

            checkInRepository.getHistory()
                .onSuccess { records ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        attendanceRecords = records
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message
                    )
                }
        }
    }

    fun emitUiState(action: HistoryUiState.() -> HistoryUiState) {
        _uiState.update { action(it) }
    }


}
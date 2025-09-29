package yun.checkin.feature.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yun.checkin.core.data_api.CheckInRepository
import yun.checkin.util.getCurrentFormattedTime

class HomeViewModel(
    private val checkInRepository: CheckInRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    private val viewModelScope = CoroutineScope(Dispatchers.Default)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // 여기에 예외가 잡힙니다!
        println("exceptionHandler : " + throwable.message)

        // 필요하다면 Crashlytics 같은 곳에 추가 로깅
        // throwable.printStackTrace() // 디버깅 시 사용 가능
    }


    init {
        startClock()

        viewModelScope.launch(exceptionHandler) {
            checkInRepository.isCheckIn()
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isCheckedIn = result
                        )
                    }
                }
                .onFailure {
                    // 실패한 경우에도 isLoading은 false로, 출석 상태는 false로 처리
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isCheckedIn = false
                        )
                    }
                }
        }

    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.OnCheckInClick -> checkIn()
        }
    }

    private fun startClock() {
        viewModelScope.launch {
            while (true) {
                _state.update { it.copy(currentTime = getCurrentFormattedTime()) }
                delay(1000)
            }
        }
    }

    private fun checkIn() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            checkInRepository.checkIn()
                .onSuccess {
                    _state.update { it.copy(isLoading = false, isCheckedIn = true) }
                    _effect.emit(HomeEffect.ShowToast("출석이 완료되었습니다."))
                }
                .onFailure { error ->
                    _state.update { it.copy(isLoading = false, error = error.message) }
                    _effect.emit(HomeEffect.ShowToast(error.message ?: "알 수 없는 오류가 발생했습니다."))
                }
        }
    }
}

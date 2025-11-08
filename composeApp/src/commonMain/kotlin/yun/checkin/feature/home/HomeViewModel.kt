package yun.checkin.feature.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yun.checkin.core.data_api.CheckInRepository
import yun.checkin.core.data_api.NotificationManager
import yun.checkin.feature.home.model.HomeSideEffect
import yun.checkin.feature.home.model.HomeUiEvent
import yun.checkin.feature.home.model.HomeUiState
import yun.checkin.feature.home.model.WorkStatus
import yun.checkin.util.getCurrentFormattedTime

class HomeViewModel(
    private val checkInRepository: CheckInRepository,
    private val notificationManager: NotificationManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<HomeSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

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
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workStatus = if (result) WorkStatus.CHECKED_IN else WorkStatus.NOT_CHECKED_IN,
                            isCheckedIn = result
                        )
                    }
                }
                .onFailure {
                    // 실패한 경우에도 isLoading은 false로, 출석 상태는 false로 처리
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workStatus = WorkStatus.NOT_CHECKED_IN,
                            isCheckedIn = false
                        )
                    }
                }
        }
    }

    fun onIntent(intent: HomeUiEvent) {
        when (intent) {
            is HomeUiEvent.OnCheckInClick -> checkIn()
        }
    }

    private fun startClock() {
        viewModelScope.launch {
            while (true) {
                _uiState.update { it.copy(currentTime = getCurrentFormattedTime()) }
                delay(1000)
            }
        }
    }

    private fun checkIn() {
        viewModelScope.launch(context = Dispatchers.Main) {
            _uiState.update { it.copy(isLoading = true) }
            checkInRepository.checkIn()
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workStatus = WorkStatus.CHECKED_IN
                        )
                    }
                    _sideEffect.send(HomeSideEffect.ShowToast("출석이 완료되었습니다."))
                    // 출근 기록 성공 시 8시간 30분 후 알림 스케줄링
                    try {
                        notificationManager.scheduleWorkEndNotification(8 * 60 * 60 + 30 * 60)
                        println("Work end notification scheduled for 8.5 hours from now")
                    } catch (e: Exception) {
                        println("Failed to schedule work end notification: ${e.message}")
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workStatus =  WorkStatus.NOT_CHECKED_IN,
                            error = error.message
                        )
                    }
                    _sideEffect.send(
                        HomeSideEffect.ShowToast(
                            error.message ?: "알 수 없는 오류가 발생했습니다."
                        )
                    )
                }
        }
    }
}

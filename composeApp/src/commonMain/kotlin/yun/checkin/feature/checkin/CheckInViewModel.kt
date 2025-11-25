package yun.checkin.feature.checkin

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
import yun.checkin.core.data_api.TeamsWebhookService
import yun.checkin.core.utils.DateFormatter
import yun.checkin.feature.checkin.model.CheckInSideEffect
import yun.checkin.feature.checkin.model.CheckInUiEvent
import yun.checkin.feature.checkin.model.HomeUiState
import yun.checkin.feature.checkin.model.WorkStatus
import yun.checkin.util.getCurrentFormattedTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CheckInViewModel(
    private val checkInRepository: CheckInRepository,
    private val notificationManager: NotificationManager,
    private val teamsWebhookService: TeamsWebhookService,
    private val authRepository: yun.checkin.core.data_api.AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<CheckInSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val viewModelScope = CoroutineScope(Dispatchers.Default)

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        // 여기에 예외가 잡힙니다!
        println("exceptionHandler : " + throwable.message)

        // 필요하다면 Crashlytics 같은 곳에 추가 로깅
        // throwable.printStackTrace() // 디버깅 시 사용 가능
    }


    init {
        startClock()

        viewModelScope.launch(coroutineExceptionHandler) {
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

    fun onIntent(intent: CheckInUiEvent) {
        when (intent) {
            is CheckInUiEvent.OnCheckInClick -> {
                checkIn()
            }
            is CheckInUiEvent.OnCheckOutClick -> {
                // 퇴근 확인 다이얼로그 표시
                _uiState.update { it.copy(showCheckOutDialog = true) }
            }

            is CheckInUiEvent.OnCheckOutConfirm -> {
                // 다이얼로그 닫고 퇴근 처리
                _uiState.update { it.copy(showCheckOutDialog = false) }
                checkOut()
            }

            is CheckInUiEvent.OnCheckOutCancel -> {
                // 다이얼로그 닫기
                _uiState.update { it.copy(showCheckOutDialog = false) }
            }
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

    @OptIn(ExperimentalTime::class)
    private fun checkIn() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            checkInRepository.checkIn()
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workStatus = WorkStatus.CHECKED_IN
                        )
                    }
                    _sideEffect.send(CheckInSideEffect.ShowToast("출석이 완료되었습니다."))


                    notifyWorkEnd(time = 8 * 60 * 60 + 30 * 60)

                    val current = Clock.System.now().toEpochMilliseconds()
                    val convertTimeFormat = DateFormatter.fromEpochMillisToKoreanDateTime(current)

                    authRepository.getCurrentUser()?.let {
                        if(authRepository.isUserInGroup(it.uid)){
                            sendMessageAtTeams(
                                name = it.name ?: "Unknown",
                                text = convertTimeFormat,
                                isCheckIn = true
                            )
                        }
                    }

                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workStatus = WorkStatus.NOT_CHECKED_IN,
                            error = error.message
                        )
                    }
                    _sideEffect.send(
                        CheckInSideEffect.ShowToast(
                            error.message ?: "알 수 없는 오류가 발생했습니다."
                        )
                    )
                }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun checkOut() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _uiState.update { it.copy(isLoading = true) }
            checkInRepository.checkOut()
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            workStatus = WorkStatus.NOT_CHECKED_IN
                        )
                    }
                    _sideEffect.send(CheckInSideEffect.ShowToast("퇴근이 완료되었습니다."))

                    val current = Clock.System.now().toEpochMilliseconds()
                    val convertTimeFormat = DateFormatter.fromEpochMillisToKoreanDateTime(current)

                    authRepository.getCurrentUser()?.let {
                        if (authRepository.isUserInGroup(it.uid)) {
                            sendMessageAtTeams(
                                name = it.name ?: "Unknown",
                                text = convertTimeFormat,
                                isCheckIn = false
                            )
                        }
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message
                        )
                    }
                    _sideEffect.send(
                        CheckInSideEffect.ShowToast(
                            error.message ?: "알 수 없는 오류가 발생했습니다."
                        )
                    )
                }
        }
    }

    private suspend fun notifyWorkEnd(time: Long) {
        try {
            notificationManager.scheduleWorkEndNotification(time.toInt())
        } catch (e: Exception) {
            println("Failed to schedule work end notification: ${e.message}")
        }
    }

    // Teams 웹훅으로 메시지 전송
    private suspend fun sendMessageAtTeams(
        name: String,
        text: String,
        isCheckIn: Boolean
    ) {
        try {
            teamsWebhookService.sendMessage(
                name = name,
                text = text,
                isCheckIn = isCheckIn
            ).onSuccess {
                println("Teams notification sent successfully")
            }.onFailure { error ->
                println("Failed to send Teams notification: ${error.message}")
            }
        } catch (e: Exception) {
            println("Exception while sending Teams notification: ${e.message}")
        }
    }
}

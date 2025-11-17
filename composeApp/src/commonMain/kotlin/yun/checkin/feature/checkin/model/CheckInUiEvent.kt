package yun.checkin.feature.checkin.model

sealed interface CheckInUiEvent {
    data object OnCheckInClick : CheckInUiEvent
}
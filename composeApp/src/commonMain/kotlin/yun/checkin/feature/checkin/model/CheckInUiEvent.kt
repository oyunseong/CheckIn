package yun.checkin.feature.checkin.model

sealed interface CheckInUiEvent {
    data object OnCheckInClick : CheckInUiEvent
    data object OnCheckOutClick : CheckInUiEvent
    data object OnCheckOutConfirm : CheckInUiEvent
    data object OnCheckOutCancel : CheckInUiEvent
}
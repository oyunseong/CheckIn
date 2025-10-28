package yun.checkin.feature.home.model

sealed interface HomeUiEvent {
    data object OnCheckInClick : HomeUiEvent
}
package yun.checkin.feature.home

import yun.checkin.AppViewModel

data class HomeState(
    val isLoading: Boolean = false,
    val isCheckedIn: Boolean = false,
    val currentTime: String = "00:00:00",
    val error: String? = null
)

sealed interface HomeIntent {
    data object OnCheckInClick : HomeIntent
}

sealed interface HomeEffect {
    data class ShowToast(val message: String) : HomeEffect
}

package yun.checkin.feature.home

data class HomeState(
    val isLoading: Boolean = true,
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

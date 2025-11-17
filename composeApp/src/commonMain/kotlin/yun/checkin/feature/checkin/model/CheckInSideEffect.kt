package yun.checkin.feature.checkin.model

sealed interface CheckInSideEffect {
    data class ShowToast(val message: String) : CheckInSideEffect
}

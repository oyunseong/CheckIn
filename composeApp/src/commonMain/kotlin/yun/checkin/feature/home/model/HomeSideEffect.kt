package yun.checkin.feature.home.model

sealed interface HomeSideEffect {
    data class ShowToast(val message: String) : HomeSideEffect
}

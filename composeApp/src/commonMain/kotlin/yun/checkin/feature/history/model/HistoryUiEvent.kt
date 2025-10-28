package yun.checkin.feature.history.model

sealed interface HistoryUiEvent {
    data object OnRefresh : HistoryUiEvent
}
package yun.checkin.feature.history.model

import yun.checkin.core.data_api.AttendanceRecord

data class HistoryUiState(
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false,
    val attendanceRecords: List<AttendanceRecord> = emptyList(),
    val error: String? = null,
)
package yun.checkin.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

actual fun getCurrentFormattedTime(): String {
    return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
}

actual fun getTodayFormattedDate(): String {
    return SimpleDateFormat("yyyy년 MM월 dd일", Locale.getDefault()).format(Date())
}

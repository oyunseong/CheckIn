
package yun.checkin.util

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter

actual fun getCurrentFormattedTime(): String {
    val formatter = NSDateFormatter().apply {
        dateFormat = "HH:mm:ss"
    }
    return formatter.stringFromDate(NSDate())
}

actual fun getTodayFormattedDate(): String {
    val formatter = NSDateFormatter().apply {
        dateFormat = "yyyy년 MM월 dd일"
    }
    return formatter.stringFromDate(NSDate())
}

package yun.checkin.util

// JavaScript Date 객체 사용을 위한 external 선언
external class JsDate() {
    fun getHours(): Int
    fun getMinutes(): Int
    fun getSeconds(): Int
    fun getFullYear(): Int
    fun getMonth(): Int
    fun getDate(): Int
}

// 현재 시간을 HH:mm:ss 형식으로 반환
actual fun getCurrentFormattedTime(): String {
    val date = JsDate()
    val hour = date.getHours().toString().padStart(2, '0')
    val minute = date.getMinutes().toString().padStart(2, '0')
    val second = date.getSeconds().toString().padStart(2, '0')

    return "$hour:$minute:$second"
}

// 오늘 날짜를 yyyy년 MM월 dd일 형식으로 반환
actual fun getTodayFormattedDate(): String {
    val date = JsDate()
    val year = date.getFullYear()
    val month = (date.getMonth() + 1).toString().padStart(2, '0') // JS month는 0-based
    val day = date.getDate().toString().padStart(2, '0')

    return "${year}년 ${month}월 ${day}일"
}
package yun.checkin.core.utils

import kotlinx.datetime.LocalDateTime

/**
 * 날짜 포맷팅을 위한 유틸리티 객체
 */
object DateFormatter {

    /**
     * LocalDateTime을 "yyyy년 MM월 dd일" 포맷으로 변환
     * 예: 2025년 09월 29일
     */
    fun toKoreanDate(dateTime: LocalDateTime): String {
        val year = dateTime.year
        val month = dateTime.monthNumber.toString().padStart(2, '0')
        val day = dateTime.dayOfMonth.toString().padStart(2, '0')

        return "${year}년 ${month}월 ${day}일"
    }

    /**
     * LocalDateTime을 "yyyy년 M월 d일" 포맷으로 변환 (앞자리 0 제거)
     * 예: 2025년 9월 29일
     */
    fun toKoreanDateShort(dateTime: LocalDateTime): String {
        val year = dateTime.year
        val month = dateTime.monthNumber
        val day = dateTime.dayOfMonth

        return "${year}년 ${month}월 ${day}일"
    }

    /**
     * LocalDateTime을 "yyyy-MM-dd" 포맷으로 변환
     * 예: 2025-09-29
     */
    fun toISODate(dateTime: LocalDateTime): String {
        val year = dateTime.year
        val month = dateTime.monthNumber.toString().padStart(2, '0')
        val day = dateTime.dayOfMonth.toString().padStart(2, '0')

        return "$year-$month-$day"
    }

    /**
     * LocalDateTime을 "MM월 dd일" 포맷으로 변환
     * 예: 09월 29일
     */
    fun toKoreanMonthDay(dateTime: LocalDateTime): String {
        val month = dateTime.monthNumber.toString().padStart(2, '0')
        val day = dateTime.dayOfMonth.toString().padStart(2, '0')

        return "${month}월 ${day}일"
    }

    /**
     * LocalDateTime을 "M월 d일" 포맷으로 변환 (앞자리 0 제거)
     * 예: 9월 29일
     */
    fun toKoreanMonthDayShort(dateTime: LocalDateTime): String {
        val month = dateTime.monthNumber
        val day = dateTime.dayOfMonth

        return "${month}월 ${day}일"
    }

    /**
     * LocalDateTime을 "yyyy년 MM월 dd일 HH:mm" 포맷으로 변환
     * 예: 2025년 09월 29일 14:30
     */
    fun toKoreanDateTime(dateTime: LocalDateTime): String {
        val dateStr = toKoreanDate(dateTime)
        val hour = dateTime.hour.toString().padStart(2, '0')
        val minute = dateTime.minute.toString().padStart(2, '0')

        return "$dateStr $hour:$minute"
    }

    /**
     * LocalDateTime을 "HH:mm:ss" 포맷으로 변환
     * 예: 14:30:25
     */
    fun toTime(dateTime: LocalDateTime): String {
        val hour = dateTime.hour.toString().padStart(2, '0')
        val minute = dateTime.minute.toString().padStart(2, '0')
        val second = dateTime.second.toString().padStart(2, '0')

        return "$hour:$minute:$second"
    }

    /**
     * LocalDateTime을 "HH:mm" 포맷으로 변환
     * 예: 14:30
     */
    fun toTimeShort(dateTime: LocalDateTime): String {
        val hour = dateTime.hour.toString().padStart(2, '0')
        val minute = dateTime.minute.toString().padStart(2, '0')

        return "$hour:$minute"
    }

    /**
     * 요일을 한글로 반환
     * 예: 월요일, 화요일, ...
     */
    fun getDayOfWeekKorean(dateTime: LocalDateTime): String {
        return when (dateTime.dayOfWeek) {
            kotlinx.datetime.DayOfWeek.MONDAY -> "월요일"
            kotlinx.datetime.DayOfWeek.TUESDAY -> "화요일"
            kotlinx.datetime.DayOfWeek.WEDNESDAY -> "수요일"
            kotlinx.datetime.DayOfWeek.THURSDAY -> "목요일"
            kotlinx.datetime.DayOfWeek.FRIDAY -> "금요일"
            kotlinx.datetime.DayOfWeek.SATURDAY -> "토요일"
            kotlinx.datetime.DayOfWeek.SUNDAY -> "일요일"
        }
    }

    /**
     * 요일을 한글 단축형으로 반환
     * 예: 월, 화, 수, ...
     */
    fun getDayOfWeekKoreanShort(dateTime: LocalDateTime): String {
        return when (dateTime.dayOfWeek) {
            kotlinx.datetime.DayOfWeek.MONDAY -> "월"
            kotlinx.datetime.DayOfWeek.TUESDAY -> "화"
            kotlinx.datetime.DayOfWeek.WEDNESDAY -> "수"
            kotlinx.datetime.DayOfWeek.THURSDAY -> "목"
            kotlinx.datetime.DayOfWeek.FRIDAY -> "금"
            kotlinx.datetime.DayOfWeek.SATURDAY -> "토"
            kotlinx.datetime.DayOfWeek.SUNDAY -> "일"
        }
    }

    fun getDayOfWeekEnglish(dateTime: LocalDateTime): String {
        return when (dateTime.dayOfWeek) {
            kotlinx.datetime.DayOfWeek.MONDAY -> "Monday"
            kotlinx.datetime.DayOfWeek.TUESDAY -> "Tuesday"
            kotlinx.datetime.DayOfWeek.WEDNESDAY -> "Wednesday"
            kotlinx.datetime.DayOfWeek.THURSDAY -> "Thursday"
            kotlinx.datetime.DayOfWeek.FRIDAY -> "Friday"
            kotlinx.datetime.DayOfWeek.SATURDAY -> "Saturday"
            kotlinx.datetime.DayOfWeek.SUNDAY -> "Sunday"
        }
    }

    fun getMonthEnglish(dateTime: LocalDateTime): String {
        return when (dateTime.month) {
            kotlinx.datetime.Month.JANUARY -> "Jan"
            kotlinx.datetime.Month.FEBRUARY -> "Feb"
            kotlinx.datetime.Month.MARCH -> "Mar"
            kotlinx.datetime.Month.APRIL -> "Apr"
            kotlinx.datetime.Month.MAY -> "May"
            kotlinx.datetime.Month.JUNE -> "Jun"
            kotlinx.datetime.Month.JULY -> "Jul"
            kotlinx.datetime.Month.AUGUST -> "Aug"
            kotlinx.datetime.Month.SEPTEMBER -> "Sep"
            kotlinx.datetime.Month.OCTOBER -> "Oct"
            kotlinx.datetime.Month.NOVEMBER -> "Nov"
            kotlinx.datetime.Month.DECEMBER -> "Dec"
        }
    }

    // 요일, 월 일 포멧
    fun formatDayOfWeekMonthDay(dateTime: LocalDateTime): String {
        val dayOfWeek = getDayOfWeekEnglish(dateTime)
        val month = getMonthEnglish(dateTime)
        val day = dateTime.day

        return "$dayOfWeek, $month $day"
    }
}
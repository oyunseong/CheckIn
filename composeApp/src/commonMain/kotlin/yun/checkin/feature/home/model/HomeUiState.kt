package yun.checkin.feature.home.model

/**
 * 시간대를 나타내는 enum
 */
enum class TimeOfDay {
    NIGHT,      // 0~6시: 밤
    MORNING,    // 7~9시: 오전  
    AFTERNOON,  // 10~15시: 오후
    EVENING;    // 16~23시: 저녁

    /**
     * 시간대에 따른 한글 표시
     */
    fun toKoreanString(): String {
        return when (this) {
            NIGHT -> "밤"
            MORNING -> "오전"
            AFTERNOON -> "오후"
            EVENING -> "저녁"
        }
    }

    /**
     * 시간대에 따른 영문 표시
     */
    fun toEnglishString(): String {
        return when (this) {
            NIGHT -> "Night"
            MORNING -> "Morning"
            AFTERNOON -> "Afternoon"
            EVENING -> "Evening"
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = true,
    val isCheckedIn: Boolean = false,
    val currentTime: String = "00:00:00",
    val workStatus: WorkStatus = WorkStatus.NOT_CHECKED_IN,
//    val timeOfDay: TimeOfDay = TimeOfDay.NIGHT,
    val error: String? = null
) {
    val timeOfDay: TimeOfDay = getTimeOfDay(currentTime)

    fun getTimeOfDay(timeString: String): TimeOfDay {
        return try {
            val hour = timeString.split(":")[0].toInt()
            when (hour) {
                in 0..6 -> TimeOfDay.NIGHT       // 0~6시: 밤
                in 7..9 -> TimeOfDay.MORNING     // 7~9시: 오전
                in 10..15 -> TimeOfDay.AFTERNOON // 10~15시 (오후 3시): 오후
                in 16..23 -> TimeOfDay.EVENING   // 16~23시 (오후 4시~11시): 저녁
                else -> TimeOfDay.NIGHT
            }
        } catch (e: Exception) {
            TimeOfDay.NIGHT // 파싱 실패시 기본값
        }
    }
}

enum class WorkStatus {
    NOT_CHECKED_IN,
    CHECKED_IN,
}
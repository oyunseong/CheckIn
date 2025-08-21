package yun.checkin

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
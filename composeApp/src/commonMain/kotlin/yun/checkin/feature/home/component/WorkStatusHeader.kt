package yun.checkin.feature.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import yun.checkin.core.designsystem.Gray
import yun.checkin.core.utils.DateFormatter
import yun.checkin.feature.home.model.WorkStatus
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun WorkStatusHeader(
    modifier: Modifier = Modifier,
    workStatus: WorkStatus? = null,
    textColor: Color = Gray
) {
    val statusText = if (workStatus == null) "-" else when (workStatus) {
        WorkStatus.NOT_CHECKED_IN -> "출근 전"
        WorkStatus.CHECKED_IN -> "출근 완료"
    }
    val localDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val currentDate = DateFormatter.formatDayOfWeekMonthDay(localDateTime)

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = statusText,
            fontWeight = FontWeight.SemiBold,
            fontSize = 42.sp,
            lineHeight = (42 * 1).sp,
            letterSpacing = (-0.005).sp,
            color = textColor
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = currentDate,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = (22 * 1).sp,
            letterSpacing = (-0.005).sp,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun Preview() {
    WorkStatusHeader(
        workStatus = WorkStatus.CHECKED_IN,
        textColor = Gray
    )
}
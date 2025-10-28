package yun.checkin.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimeDisplay(
    time: String,
    textColor: Color
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Red.copy(alpha = 0.1f))
            .padding(vertical = 25.dp, horizontal = 74.dp)
    ) {
        Text(
            text = time,
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp,
            lineHeight = (36 * 1).sp,
            letterSpacing = (-0.005).sp,
            color = textColor
        )
    }
}

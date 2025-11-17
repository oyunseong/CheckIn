package yun.checkin.feature.checkin.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    val color = Color(0xFFFFEFEF).copy(alpha = 0.2f)
    Card(
        modifier = Modifier
            .clip(CircleShape),
        colors = CardColors(
            containerColor = color,
            contentColor = color,
            disabledContainerColor = color,
            disabledContentColor = color
        )
    ) {
        Text(
            modifier = Modifier.padding(vertical = 25.dp, horizontal = 74.dp),
            text = time,
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp,
            lineHeight = (36 * 1).sp,
            letterSpacing = (-0.005).sp,
            color = textColor
        )
    }
}

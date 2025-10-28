package yun.checkin.feature.home.component


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CheckInButton(
    isLoading: Boolean,
    isCheckedIn: Boolean,
    onClick: () -> Unit
) {
    val buttonEnabled = !isLoading && !isCheckedIn
    val animatedAlpha by animateFloatAsState(if (buttonEnabled) 1f else 0.5f)

    Button(
        onClick = onClick,
        enabled = buttonEnabled,
        modifier = Modifier.fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFFC107),
            contentColor = Color.Black,
            disabledContainerColor = Color.Gray.copy(alpha = 0.5f)
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = if (isCheckedIn) "출석 완료" else "출석하기",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.graphicsLayer(alpha = animatedAlpha)
            )
        }
    }
}

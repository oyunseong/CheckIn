package yun.checkin.feature.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import checkin.composeapp.generated.resources.Res
import checkin.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.compose.viewmodel.koinViewModel
import yun.checkin.AppViewModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest {
            when (it) {
                is HomeEffect.ShowToast -> {
//                    snackbarHostState.showSnackbar(it.message)
                }
            }
        }
    }

    HomeScreen(
        modifier = modifier,
        state = state,
        padding = padding,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier,
    padding: PaddingValues,
    state: HomeState,
    onIntent: (HomeIntent) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6A11CB),
                        Color(0xFF2575FC)
                    )
                )
            )
            .padding(padding)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Header()
            TimeDisplay(state.currentTime)
            CheckInStatus(state.isCheckedIn)
            CheckInButton(
                isLoading = state.isLoading,
                isCheckedIn = state.isCheckedIn,
                onClick = { onIntent(HomeIntent.OnCheckInClick) }
            )
        }
    }
}

@OptIn(ExperimentalTime::class)
@Composable
private fun Header() {
    val now = Clock.System.now()
    val localDateTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
    val dateString = "${localDateTime.year}년 ${localDateTime.month}월 ${localDateTime.day}일"

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "출석체크",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = dateString,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun TimeDisplay(time: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(240.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.1f))
            .padding(16.dp)
    ) {
        Text(
            text = time,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun CheckInStatus(isCheckedIn: Boolean) {
    val statusText = if (isCheckedIn) "오늘은 출석 완료! ✅" else "아직 출석 전입니다."
    val statusColor = if (isCheckedIn) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.9f)

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Text(
            text = statusText,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
            style = MaterialTheme.typography.titleMedium,
            color = statusColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun CheckInButton(
    isLoading: Boolean,
    isCheckedIn: Boolean,
    onClick: () -> Unit
) {
    val buttonEnabled = !isLoading && !isCheckedIn
    val animatedAlpha by animateFloatAsState(if (buttonEnabled) 1f else 0.5f)

    Button(
        onClick = onClick,
        enabled = buttonEnabled,
        modifier = Modifier
            .fillMaxWidth()
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

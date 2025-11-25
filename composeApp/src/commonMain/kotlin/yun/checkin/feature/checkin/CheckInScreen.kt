package yun.checkin.feature.checkin

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import checkin.composeapp.generated.resources.Res
import checkin.composeapp.generated.resources.ic_sun
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import yun.checkin.core.designsystem.Blue
import yun.checkin.core.designsystem.Gray
import yun.checkin.core.designsystem.Orange
import yun.checkin.core.designsystem.Red
import yun.checkin.core.designsystem.Yellow
import yun.checkin.feature.checkin.component.BuildingImage
import yun.checkin.feature.checkin.component.TimeDisplay
import yun.checkin.feature.checkin.component.WorkStatusHeader
import yun.checkin.feature.checkin.model.CheckInSideEffect
import yun.checkin.feature.checkin.model.CheckInUiEvent
import yun.checkin.feature.checkin.model.HomeUiState
import yun.checkin.feature.checkin.model.TimeOfDay
import yun.checkin.feature.checkin.model.WorkStatus

@Composable
fun CheckInScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    onShowSnackBar: (String) -> Unit,
    viewModel: CheckInViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is CheckInSideEffect.ShowToast -> {
                    onShowSnackBar.invoke(it.message)
                }
            }
        }
    }

    CheckInScreen(
        modifier = modifier,
        state = state,
        padding = padding,
        onIntent = viewModel::onIntent
    )
}

@Composable
fun CheckInScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
    state: HomeUiState = HomeUiState(),
    onIntent: (CheckInUiEvent) -> Unit = {}
) {
    val textColor = if (state.workStatus == WorkStatus.NOT_CHECKED_IN) Gray else getTextColor(
        state.timeOfDay
    )
    // 화면 사이즈

    val bottomPadding = animateDpAsState(
        targetValue = if (state.workStatus == WorkStatus.CHECKED_IN) 150.dp else 0.dp,
        animationSpec = tween(500)
    ).value

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = getBackgroundColor(timeOfDay = state.timeOfDay)
            )
            .padding(padding)
    ) {
        Column() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(94.dp))
                WorkStatusHeader(
                    workStatus = state.workStatus,
                    textColor = textColor
                )
                Spacer(modifier = Modifier.height(38.dp))
                TimeDisplay(
                    time = state.currentTime,
                    textColor = textColor
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_sun),
                contentDescription = null,
                modifier = Modifier
                    .size(186.dp)
                    .clip(CircleShape)
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            getSunColor(state.timeOfDay),
                            blendMode = BlendMode.SrcAtop
                        )
                    }
                    .clickable {
                        if (!state.isLoading) {
                            when (state.workStatus) {
                                WorkStatus.NOT_CHECKED_IN -> {
                                    onIntent(CheckInUiEvent.OnCheckInClick)
                                }

                                WorkStatus.CHECKED_IN -> {
                                    onIntent(CheckInUiEvent.OnCheckOutClick)
                                }
                            }
                        }
                    }
            )
            Spacer(
                modifier = Modifier.height(bottomPadding + 81.dp)
            )
        }
        BuildingImage(
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        // 퇴근 확인 다이얼로그
        if (state.showCheckOutDialog) {
            AlertDialog(
                onDismissRequest = { onIntent(CheckInUiEvent.OnCheckOutCancel) },
                title = {
                    Text(text = "퇴근 확인")
                },
                text = {
                    Text(text = "퇴근하시겠습니까?")
                },
                confirmButton = {
                    TextButton(
                        onClick = { onIntent(CheckInUiEvent.OnCheckOutConfirm) }
                    ) {
                        Text("확인")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { onIntent(CheckInUiEvent.OnCheckOutCancel) }
                    ) {
                        Text("취소")
                    }
                }
            )
        }
    }
}

private fun getBackgroundColor(timeOfDay: TimeOfDay): Brush {
    return when (timeOfDay) {
        TimeOfDay.NIGHT -> {
            Brush.verticalGradient(
                0f to Color(0xFF202528),
                1f to Color(0xFF202528),
            )
        }

        else -> {
            Brush.verticalGradient(
                colorStops = arrayOf(
                    0.0f to Color(0xFFB3D1FF),
                    0.8f to Color(0xFFFFE5DD),
                    1f to Color(0xFFFFE5DD)
                )
            )
        }
    }
}


fun getTextColor(timeOfDay: TimeOfDay): Color {
    return when (timeOfDay) {
        TimeOfDay.NIGHT -> Yellow
        TimeOfDay.MORNING -> Red
        TimeOfDay.AFTERNOON -> Orange
        TimeOfDay.EVENING -> Blue
    }
}

fun getSunColor(timeOfDay: TimeOfDay): Brush {
    return when (timeOfDay) {
        TimeOfDay.NIGHT -> Brush.verticalGradient(
            colorStops = arrayOf(
                0f to Color(0xFFFFEFB4),       // 나머지는 그레이
                0.2f to Color(0xFFFFDB59),      // 20% 지점에서 그레이로 변경
                1f to Color(0xFF816D27)      // 상단 색상
            ),
            startY = 0f,
        )

        TimeOfDay.MORNING -> {
            Brush.verticalGradient(
                colorStops = arrayOf(
                    0.0f to Red,
                    1f to Red
                ),
                startY = 0f,
            )
        }

        TimeOfDay.AFTERNOON -> {
            Brush.verticalGradient(
                colorStops = arrayOf(
                    0.0f to Orange,
                    1f to Orange
                ),
                startY = 0f,
            )
        }

        TimeOfDay.EVENING -> {
            Brush.verticalGradient(
                colorStops = arrayOf(
                    0.0f to Orange,
                    1f to Orange
                ),
                startY = 0f,
            )
        }
    }
}


@Preview()
@Composable
private fun Preview() {
    MaterialTheme {
        CheckInScreen()
    }
}
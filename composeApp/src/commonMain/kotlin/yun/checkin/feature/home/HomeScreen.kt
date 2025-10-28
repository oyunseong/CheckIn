package yun.checkin.feature.home

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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalViewConfiguration
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
import yun.checkin.feature.home.component.BuildingImage
import yun.checkin.feature.home.component.TimeDisplay
import yun.checkin.feature.home.component.WorkStatusHeader
import yun.checkin.feature.home.model.HomeSideEffect
import yun.checkin.feature.home.model.HomeUiEvent
import yun.checkin.feature.home.model.HomeUiState
import yun.checkin.feature.home.model.TimeOfDay
import yun.checkin.feature.home.model.WorkStatus

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest {
            when (it) {
                is HomeSideEffect.ShowToast -> {
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
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(),
    state: HomeUiState = HomeUiState(),
    onIntent: (HomeUiEvent) -> Unit = {}
) {
    val textColor = if (state.workStatus == WorkStatus.NOT_CHECKED_IN) Gray else getTextColor(
        state.timeOfDay
    )
    // 화면 사이즈

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colorStops = arrayOf(
                        0.0f to Color(0xFFccdaec),
                        1f to Color(0xFFeaeaea)
                    )
                )
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
            // TODO 화면 사이즈로 가변 처리
            val bottomPadding = if (state.workStatus == WorkStatus.CHECKED_IN) 100.dp else 0.dp
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
                        onIntent(HomeUiEvent.OnCheckInClick)
                    }
            )
            Spacer(
                modifier = Modifier.height(bottomPadding + 81.dp)
            )
        }
        BuildingImage(
            modifier = Modifier.align(Alignment.BottomCenter)
        )
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
        HomeScreen()
    }
}
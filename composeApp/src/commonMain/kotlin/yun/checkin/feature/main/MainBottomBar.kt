//package yun.checkin.feature.main
//
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.animation.fadeIn
//import androidx.compose.animation.fadeOut
//import androidx.compose.animation.slideIn
//import androidx.compose.animation.slideOut
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScope
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.RowScope
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.selection.selectable
//import androidx.compose.foundation.selection.selectableGroup
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.semantics.Role
//import androidx.compose.ui.unit.IntOffset
//import androidx.compose.ui.unit.dp
//import org.jetbrains.compose.resources.painterResource
//import org.jetbrains.compose.ui.tooling.preview.Preview
//
//@Composable
//internal fun BoxScope.MainBottomBar(
//    visible: Boolean,
//    currentTab: MainTab?,
//    onTabSelected: (MainTab) -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    AnimatedVisibility(
//        modifier = Modifier
//            .align(Alignment.BottomCenter)
//            .navigationBarsPadding(),
//        visible = visible,
//        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
//        exit = fadeOut() + slideOut { IntOffset(0, it.height) },
//    ) {
//        Surface(
//            modifier = modifier
//                .padding(8.dp)
//                .fillMaxWidth()
//                .height(56.dp),
//            shape = CircleShape,
//            border = BorderStroke(1.dp, Color.Red),
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 28.dp)
//                    .selectableGroup(),
//                horizontalArrangement = Arrangement.spacedBy(8.dp),
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                MainTab.entries.forEach { tab ->
//                    MainBottomBarItem(
//                        tab = tab,
//                        selected = tab == currentTab,
//                        onClick = { onTabSelected(tab) },
//                    )
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun RowScope.MainBottomBarItem(
//    tab: MainTab,
//    selected: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    Box(
//        modifier = modifier
//            .weight(1f)
//            .fillMaxHeight()
//            .selectable(
//                selected = selected,
//                role = Role.Tab,
//                onClick = onClick,
//            ),
//        contentAlignment = Alignment.Center,
//    ) {
//        Icon(
//            painter = painterResource(tab.iconResId),
//            contentDescription = tab.contentDescription,
//            tint = if (selected) {
//                Color.Black
//
//            } else {
//                Color.Blue
//
//            },
//            modifier = Modifier.size(32.dp),
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun MainBottomBarPreview() {
//    MaterialTheme {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//        ) {
//            var show by remember { mutableStateOf(true) }
//
//            Button(
//                onClick = { show = !show },
//                modifier = Modifier.align(Alignment.Center),
//                content = { Text("show/hide") }
//            )
//            MainBottomBar(
//                visible = show,
//                currentTab = MainTab.HOME,
//                onTabSelected = { },
//            )
//        }
//    }
//}

package yun.checkin.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import checkin.composeapp.generated.resources.Res
import checkin.composeapp.generated.resources.img_building_01
import checkin.composeapp.generated.resources.img_building_02
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun BuildingImage(
    modifier: Modifier = Modifier
) {
    val color = Color(0xFF8D8D8D)
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column() {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(Res.drawable.img_building_01),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(88.dp))
        }

        Column() {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(Res.drawable.img_building_02),
                contentDescription = null,
            )
            Box(modifier = Modifier.fillMaxWidth().height(22.dp).background(color))
        }
    }
}

@Preview
@Composable
private fun Preview() {
    BuildingImage()
}
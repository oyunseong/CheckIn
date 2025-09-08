//package yun.checkin.feature.main
//
//import androidx.compose.runtime.Composable
//import checkin.composeapp.generated.resources.Res
//import checkin.composeapp.generated.resources.compose_multiplatform
//import com.droidknights.app.core.navigation.MainTabRoute
//import com.droidknights.app.core.navigation.Route
//import org.jetbrains.compose.resources.DrawableResource
//
//internal enum class MainTab(
//    val iconResId: DrawableResource,
//    internal val contentDescription: String,
//    val route: MainTabRoute,
//) {
//    SETTING(
//        iconResId = Res.drawable.compose_multiplatform,
//        contentDescription = "설정",
//        MainTabRoute.Setting,
//    ),
//    HOME(
//        iconResId = Res.drawable.compose_multiplatform,
//        contentDescription = "홈",
//        MainTabRoute.Home,
//    ),
//    ;
//
//    companion object {
//        @Composable
//        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
//            return entries.find { predicate(it.route) }
//        }
//
//        @Composable
//        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
//            return entries.map { it.route }.any { predicate(it) }
//        }
//    }
//}

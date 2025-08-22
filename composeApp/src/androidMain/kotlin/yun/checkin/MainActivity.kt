package yun.checkin

import DefaultRootComponent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import yun.checkin.shared.di.initKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initKoin()
        val root =
            DefaultRootComponent(
                componentContext = defaultComponentContext()
            )

        setContent {
            App(root)
        }
    }
}
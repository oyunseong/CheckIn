package yun.checkin.shared.home

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value

interface HomeComponent {
    fun onBack()
}

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val item : String,
    private val onBack: () -> Unit
) : HomeComponent {

    override fun onBack() {
        onBack.invoke()
    }
}
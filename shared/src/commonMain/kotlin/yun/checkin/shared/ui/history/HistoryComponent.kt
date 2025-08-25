package yun.checkin.shared.ui.history

import com.arkivanov.decompose.ComponentContext

interface HistoryComponent

class DefaultHistoryComponent(
    componentContext: ComponentContext
) : HistoryComponent, ComponentContext by componentContext

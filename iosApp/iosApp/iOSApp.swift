import SwiftUI
import shared

@main
struct iOSApp: App {
    private let lifecycle = LifecycleRegistry()
    private let root: RootComponent

    init() {
        KoinModuleKt.doInitKoin()

        root = DefaultRootComponent(
            componentContext: DefaultComponentContext(
                lifecycle: lifecycle
            )
        )
    }

    var body: some Scene {
        WindowGroup {
            ContentView(root: root)
        }
    }
}
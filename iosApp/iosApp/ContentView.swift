import UIKit
import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    private let root: RootComponent

    init(root: RootComponent) {
        self.root = root
    }

    func makeUIViewController(context: Context) -> UIViewController {
        return AppKt.App(root: root)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    private let root: RootComponent

    init(root: RootComponent) {
        self.root = root
    }

    var body: some View {
        ComposeView(root: root)
            .ignoresSafeArea(.all)
    }
}
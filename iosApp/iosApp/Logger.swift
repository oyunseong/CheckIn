//
// Created by yunseong on 2025. 9. 26..
//

import Foundation

@objc(Logger)
@objcMembers
public class Logger: NSObject {

    @objc public static func log(_ message: String) {
        print("🍎 [SwiftLog] \(message)")
    }

    // 추가적인 초기화 메서드 (Objective-C 호환성을 위해)
    @objc public override init() {
        super.init()
    }
}
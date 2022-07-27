import Foundation

@objc public class backgroundstep: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}

enum Direction: CaseIterable {
    case up, down, left, right

    func isVertical() -> Bool {
        return self == .up || self == .down
    }
}

struct Vector2 {
    var x: Int
    var y: Int

    init(_ x: Int, _ y: Int) {
        self.x = x
        self.y = y
    }

    init(_ direction: Direction) {
        switch direction {
            case .up:
                self.x = 0
                self.y = -1
            case .down:
                self.x = 0
                self.y = 1
            case .left:
                self.x = -1
                self.y = 0
            case .right:
                self.x = 1
                self.y = 0
        }
    }

    static func * (left: Vector2, right: Int) -> Vector2 {
        return Vector2(left.x * right,
                        left.y * right)
    }

    static func / (left: Vector2, right: Int) -> Vector2 {
        return Vector2(left.x / right,
                        left.y / right)
    }

    static func % (left: Vector2, right: Vector2) -> Vector2 {
        return Vector2(left.x % right.x,
                       left.y % right.y)
    }

    static func + (left: Vector2, right: Vector2) -> Vector2 {
        return Vector2(left.x + right.x,
                        left.y + right.y)
    }

    static func - (left: Vector2, right: Vector2) -> Vector2 {
        return Vector2(left.x - right.x,
                        left.y - right.y)
    }

    static func += (left: inout Vector2, right: Vector2) {
        left = left + right
    }

    static func -= (left: inout Vector2, right: Vector2) {
        left = left - right
    }

    static func + (left: Vector2, right: Direction) -> Vector2 {
        return left + Vector2(right)
    }
}
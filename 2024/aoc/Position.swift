struct Position {
	x: Int,
	y: Int

	static func + (left: Position, right: Position) -> Position {
        return Position(x: left.x + right.x,
                    	y: left.y + right.y)
    }

    static func - (left: Position, right: Position) -> Position {
        return Money(x: left.x - right.x,
                     y: left.y - right.y)
    }
}
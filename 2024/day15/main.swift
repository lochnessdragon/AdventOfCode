import Foundation

// part 1 grid (normal sized blocks)
class Grid: CustomStringConvertible {
	var layout: [[Character]]
	var robotPosition: Vector2

	init(layout: [[Character]]) {
		self.layout = layout
		robotPosition = Vector2(0, 0)
		for row in 0..<layout.count {
			for column in 0..<layout[row].count {
				if layout[row][column] == "@" {
					robotPosition = Vector2(column, row)
					break
				}
			}
		}
	}

	func tryRobotMove(going: Direction) {
		if tryMove(position: robotPosition, going: going) {
			robotPosition += Vector2(going)
		}
	}

	func tryMove(position: Vector2, going: Direction) -> Bool {
		let newPosition = position + going
		var canMove = false
		switch layout[newPosition.y][newPosition.x] {
			case ".":
				canMove = true
			case "O":
				canMove = tryMove(position: newPosition, going: going)
			default:
				break
		}

		if canMove {
			layout[newPosition.y][newPosition.x] = layout[position.y][position.x]
			layout[position.y][position.x] = "."
		}

		return canMove
	}

	func sumGPS() -> UInt {
		var sum: UInt = 0
		for row in 0..<layout.count {
			for column in 0..<layout[row].count {
				if layout[row][column] == "O" {
					sum += UInt((100 * row) + column)
				}
			}
		}

		return sum
	}

	public var description: String { return layout.map{ String($0) }.joined(separator: "\n") }
}

// part 2 grid (double sized blocks)
class DoubleSpacedGrid: CustomStringConvertible {
	var layout: [[Character]]
	var robotPosition: Vector2

	init(layout: [[Character]]) {
		self.layout = Array(repeating: Array(repeating: ".", count: layout[0].count * 2), count: layout.count)

		robotPosition = Vector2(0, 0)
		for row in 0..<layout.count {
			for column in 0..<layout[row].count {
				switch layout[row][column] {
					case "@":
						robotPosition = Vector2(column * 2, row)
						self.layout[row][column * 2] = "@"
					case "#":
						self.layout[row][column * 2] = "#"
						self.layout[row][(column * 2) + 1] = "#"
					case "O":
						self.layout[row][column * 2] = "["
						self.layout[row][(column * 2) + 1] = "]"
					default:
						break
				}
			}
		}
	}

	func tryRobotMove(going: Direction) {
		if canMove(position: robotPosition, going: going) {
			move(position: robotPosition, going: going)
			robotPosition += Vector2(going)
		}
	}

	func canMove(position: Vector2, going: Direction) -> Bool {
		let newPosition = position + going
		switch layout[newPosition.y][newPosition.x] {
			case ".":
				return true
			case "[":
				return canMove(position: newPosition, going: going) && (going.isVertical() ? canMove(position: newPosition + Vector2(1, 0), going: going) : true)
			case "]":
				return canMove(position: newPosition, going: going) && (going.isVertical() ? canMove(position: newPosition - Vector2(1, 0), going: going) : true)
			default:
				return false
		}
	}

	func move(position: Vector2, going: Direction) {
		let newPosition = position + going
		
		if layout[newPosition.y][newPosition.x] == "[" || layout[newPosition.y][newPosition.x] == "]" {
			if going.isVertical() {
				if layout[newPosition.y][newPosition.x] == "[" {
					move(position: newPosition + Vector2(1, 0), going: going) // move the attached "]"
				} else if layout[newPosition.y][newPosition.x] == "]" {
					move(position: newPosition - Vector2(1, 0), going: going) // move the attached "["
				}
			}

			move(position: newPosition, going: going)
		}

		layout[newPosition.y][newPosition.x] = layout[position.y][position.x]
		layout[position.y][position.x] = "."
	}

	func sumGPS() -> UInt {
		var sum: UInt = 0
		for row in 0..<layout.count {
			for column in 0..<layout[row].count {
				if layout[row][column] == "[" {
					sum += UInt((100 * row) + column)
				}
			}
		}
		return sum
	}

	public var description: String { return layout.map{ String($0) }.joined(separator: "\n") }
}

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)
let sections = contents.components(separatedBy: "\r\n\r\n")
let characterMap = sections[0].components(separatedBy: "\r\n").map {Array($0)}

var grid = Grid(layout: characterMap)
var doubleGrid = DoubleSpacedGrid(layout: characterMap)

for command in sections[1] {
	switch command {
		case "^":
			grid.tryRobotMove(going: Direction.up)
			doubleGrid.tryRobotMove(going: Direction.up)
		case "v":
			grid.tryRobotMove(going: Direction.down)
			doubleGrid.tryRobotMove(going: Direction.down)
		case "<":
			grid.tryRobotMove(going: Direction.left)
			doubleGrid.tryRobotMove(going: Direction.left)
		case ">":
			grid.tryRobotMove(going: Direction.right)
			doubleGrid.tryRobotMove(going: Direction.right)
		default:
			break
	}
}

print("Part 1: \(grid.sumGPS())")
print("Part 2: \(doubleGrid.sumGPS())")
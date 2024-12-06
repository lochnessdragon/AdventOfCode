import Foundation

// part 2: 1484 too low (really too low)

enum Direction {
	case up, down, left, right

	mutating func rotate() {
        switch self {
        case .up:
            self = .right
        case .right:
            self = .down
        case .down:
            self = .left
        case .left:
        	self = .up
        }
    }

    func copy_rotate() -> Direction {
    	var new = self
    	new.rotate()
    	return new
    }

    func get_bit() -> Int {
    	switch self {
    		case .up:
    			1
    		case .down:
    			2
    		case .left:
    			4
    		case .right:
    			8
    	}
    }
}

struct Position : Hashable {
	var x: Int
	var y: Int

	func next(direction: Direction) -> Position {
		switch direction {
			case .up:
				Position(x: x, y: y - 1)
			case .right:
				Position(x: x + 1, y: y)
			case .down:
				Position(x: x, y: y + 1)
			case .left:
				Position(x: x - 1, y: y)
		}
	}

	mutating func move(direction: Direction) {
		switch direction {
			case .up:
				y -= 1
			case .right:
				x += 1
			case .down:
				y += 1
			case .left:
				x -= 1
		}
	}

	mutating func back(direction: Direction) {
		switch direction {
			case .up:
				y += 1
			case .right:
				x -= 1
			case .down:
				y -= 1
			case .left:
				x += 1
		}
	}
}

func print_map(extent: (Int, Int), blocks: Set<Position>, visited: Set<Position>, new_obstacle: Position = Position(x: -1, y: -1)) {
	for y in 0..<extent.1 {
		for x in 0..<extent.0 {
			if blocks.contains(Position(x: x, y: y)) {
				print("#", terminator: "")
			} else if visited.contains(Position(x: x, y: y)) {
				print("X", terminator: "")
			} else if x == new_obstacle.x && y == new_obstacle.y {
				print("O", terminator: "")
			} else {
				print(".", terminator: "")
			}
		}
		print("")
	}
}

func in_bounds(_ pos: Position, extent: (Int, Int)) -> Bool {
	pos.x >= 0 && pos.y >= 0 && pos.x < extent.0 && pos.y < extent.1
}

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)
let lines = contents.components(separatedBy: "\r\n")

// 2d list of blocks and positions?

var guard_start: Position = Position(x: 0, y: 0)
var guard_direction: Direction = .up
var extent = (lines[0].count, lines.count)

var blocks : Set<Position> = []

for i in 0..<lines.count {
	let line = Array(lines[i])
	for j in 0..<line.count {
		if line[j] == "^" {
			guard_start = Position(x: j, y: i)
		} else if line[j] == "#" {
			blocks.insert(Position(x: j, y: i))
		}
	}
}
 
print("Size: \(extent.0) x \(extent.1)")

var guard_pos = guard_start

// maps the visited positions with their first met directions (used by part 2)
var visited: Dictionary<Position, Direction> = [:]

while in_bounds(guard_pos, extent: extent) {
	visited[guard_pos] = (visited[guard_pos] ?? guard_direction)

	if blocks.contains(guard_pos.next(direction: guard_direction)) {
		guard_direction.rotate()
	} else {
		guard_pos.move(direction: guard_direction)
	}
}

print("Part 1: \(visited.count)")

//print_map(extent: extent, blocks: blocks, visited: visited)

// part 2 = 1586
// 
// new obstacle positions can only come from the set of visited tiles
// put another way: obstacle positions must intersect already defined paths
// 
// 


/* // Brute-force method: (too slow)
// part 2, keep track of all the locations that the guard visits and the direction he visits them in
// if we revisit a tile heading in the same direction, we must be in a loop
// if we exit, we must not be
// try this on every visited tile (not including the start)
// optimizations:
// - only need to simulate from the tile directly before this one (if I knew which tile that was)

var part_2 = 0
var i = 0

for (potential_obstacle, start_direction) in visited {
	// skip the starting position
	if potential_obstacle == guard_start {
		continue
	}

	var part_2_visited : Dictionary<Position, Int> = [:]
	var guard_pos = potential_obstacle
	guard_pos.back(direction: start_direction)
	var guard_direction : Direction = start_direction
	let blocks = blocks + [potential_obstacle]
	var loop = false

	while in_bounds(guard_pos, extent: extent) {
		// check if we've already visited this tile in the same direction
		if let direction_flags = part_2_visited[guard_pos] {
			if guard_direction.get_bit() & direction_flags != 0 {
				loop = true
				break
			}
		}

		// add our pos/direction to the dict
		part_2_visited[guard_pos] = (part_2_visited[guard_pos] ?? 0) | guard_direction.get_bit() 

		// regular simulation
		if blocks.contains(guard_pos.next(direction: guard_direction)) {
			guard_direction.rotate()
		} else {
			guard_pos.move(direction: guard_direction)
		}
	}

	if loop {
		print(i)
		part_2 += 1
	}

	i += 1
}

print("Part 2: \(part_2)")
*/
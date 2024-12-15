import Foundation

func mod(_ left: Int, _ right: Int) -> Int {
	var result = left % right
	result += result < 0 ? right : 0
	return result
}

struct Vector2 {
	var x: Int
	var y: Int

	init(_ x: Int, _ y: Int) {
		self.x = x
		self.y = y
	}

	mutating func mod(_ right: Vector2) {
		self.x = main.mod(self.x, right.x)
		self.y = main.mod(self.y, right.y)
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
}

class Robot : Hashable {
	var position: Vector2
	var startPosition: Vector2
	var velocity: Vector2

	init(startAt position: Vector2, withSpeed velocity: Vector2) {
		self.position = position
		self.startPosition = position
		self.velocity = velocity
	}

	func simulate(timestep: Int, grid_size: Vector2) {
		self.position += self.velocity * timestep
		self.position.mod(grid_size)
	}

	func quadrant(grid_size: Vector2) -> Int {
		let half_grid_size = grid_size / 2
		if self.position.x > half_grid_size.x && self.position.y > half_grid_size.y {
			return 1
		} else if self.position.x < half_grid_size.x && self.position.y > half_grid_size.y {
			return 2
		} else if self.position.x < half_grid_size.x && self.position.y < half_grid_size.y {
			return 3
		} else if self.position.x > half_grid_size.x && self.position.y < half_grid_size.y {
			return 4
		} else {
			return 0 // no quadrant
		}
	}

	func reset() {
		self.position = self.startPosition
	}

	static func == (left: Robot, right: Robot) -> Bool {
		return left.position.x == right.position.x && left.position.y == right.position.y
	}

	func hash(into hasher: inout Hasher) {
        hasher.combine(position.x)
        hasher.combine(position.y)
    }
}

func printGrid(robots: [Robot], grid_size: Vector2) {
	for y in 0..<grid_size.y {
		for x in 0..<grid_size.x {
			let count = robots.reduce(0, { accum, robot in
				accum + (robot.position.x == x && robot.position.y == y ? 1 : 0)
			})

			if count == 0 {
				print(".", terminator: "")
			} else {
				print("\(count)", terminator: "")
			}
		}
		print("")
	}
}

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)
let robot_regex = try Regex("p=(-?[0-9]+),(-?[0-9]+) v=(-?[0-9]+),(-?[0-9]+)")

var robots : [Robot] = []
let grid_size = Vector2(101, 103) // Vector2(11, 7) for example

for line in contents.components(separatedBy: "\r\n") {
	let match = line.firstMatch(of: robot_regex)!
	robots.append(Robot(startAt: Vector2(Int(match[1].substring!)!, Int(match[2].substring!)!), withSpeed: Vector2(Int(match[3].substring!)!, Int(match[4].substring!)!)))
}

printGrid(robots: robots, grid_size: grid_size)

var quadrant_1 = 0
var quadrant_2 = 0
var quadrant_3 = 0
var quadrant_4 = 0
for robot in robots {
	robot.simulate(timestep: 100, grid_size: grid_size)
	
	switch robot.quadrant(grid_size: grid_size) {
		case 1:
			quadrant_1 += 1
		case 2:
			quadrant_2 += 1
		case 3:
			quadrant_3 += 1
		case 4:
			quadrant_4 += 1
		default:
			break
	}

	robot.reset()
}

//printGrid(robots: robots, grid_size: grid_size)

var part_1 = quadrant_1 * quadrant_2 * quadrant_3 * quadrant_4

print("Part 1: \(part_1)")
// 2024 is too low
var secondsElapsed = 0
while true {
	secondsElapsed += 1
	for robot in robots {
		robot.simulate(timestep: 1, grid_size: grid_size)
	}

	let robotSet = Set<Robot>(robots)
	if robots.count == robotSet.count {
		break
	}
}

print("Part 2: \(secondsElapsed)")
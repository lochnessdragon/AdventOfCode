import Foundation

// represents the eight possible directions on a 2d square grid
enum Direction: CaseIterable {
	case north, northeast, east, southeast, south, southwest, west, northwest
}

// increases an x and y value in a direction (see the direction enum)
func incrementInDirection(x: inout Int, y: inout Int, direction: Direction) {
	switch direction {
		case .north, .northeast, .northwest:
			y -= 1
		case .south, .southeast, .southwest:
			y += 1
		default:
			break
	}

	switch direction {
		case .northeast, .east, .southeast:
			x -= 1
		case .northwest, .west, .southwest:
			x += 1
		default:
			break
	}
}

// poorly named, basically a recursive function to query "MAS" in a direction
func countCharIn(_ lines: [[Character]], _ x: Int, _ y: Int, _ direction: Direction, _ char: Character, fixed_grid: inout [[Character]]) -> Int {
	var new_x = x
	var new_y = y
	incrementInDirection(x: &new_x, y: &new_y, direction: direction)

	if new_x < 0 || new_y < 0 || new_y >= lines.count || new_x >= lines[new_y].count {
		return 0
	}


	if (lines[new_y][new_x] == char) {
		let count = switch char {
			case "M":
				countCharIn(lines, new_x, new_y, direction, "A", fixed_grid: &fixed_grid)
			case "A":
				countCharIn(lines, new_x, new_y, direction, "S", fixed_grid: &fixed_grid)
			case "S":
				1
			default:
				0
		}

		if count > 0 {
			fixed_grid[new_y][new_x] = grid[new_y][new_x]
		}

		return count
	}

	return 0
}

// is there an xmas starting at this position?
func countXMASAt(_ lines: [[Character]], _ x: Int, _ y: Int, fixed_grid: inout [[Character]]) -> Int {
	// XMAS
	// X - M - A - S
	var sum = 0

	for direction in Direction.allCases {
		sum += countCharIn(lines, x, y, direction, "M", fixed_grid: &fixed_grid)
	}

	if sum > 0 {
		fixed_grid[y][x] = lines[y][x]
	}

	return sum
}

// checks a char at a pos (x, y) returning true if that character exists and is equal to char, false otherwise
func checkChar(_ grid: [[Character]], _ x: Int, _ y: Int, _ char: Character) -> Bool {
	return x >= 0  && y >= 0 && y < grid.count && x < grid[y].count && grid[y][x] == char
}

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)
let lines = contents.components(separatedBy: "\r\n")
let grid : [[Character]] = lines.map { Array($0) }
var fixed_grid : [[Character]] = Array(repeating: Array(repeating: ".", count: grid[0].count), count: grid.count)

var part_1 = 0
var part_2 = 0

// loop through every possible position
for y in 0..<grid.count {
	for x in 0..<grid[y].count {
		if grid[y][x] == "X" {
			// check for xmas's around the position
			part_1 += countXMASAt(grid, x, y, fixed_grid: &fixed_grid)
		} else if grid[y][x] == "A" {
			// check for X-MAS's around the position (see part 2)
			part_2 += ((checkChar(grid, x - 1, y - 1, "M") && checkChar(grid, x + 1, y + 1, "S")) || (checkChar(grid, x - 1, y - 1, "S") && checkChar(grid, x + 1, y + 1, "M"))) && 		// top left to bottom right
					  ((checkChar(grid, x - 1, y + 1, "M") && checkChar(grid, x + 1, y - 1, "S")) || (checkChar(grid, x - 1, y + 1, "S") && checkChar(grid, x + 1, y - 1, "M"))) ? 1 : 0	// bottom left to top right
		}
	}
}

print("Part 1: \(part_1)")
print("Part 2: \(part_2)")

for line in fixed_grid {
	print(String(line))
}
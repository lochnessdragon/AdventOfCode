import Foundation

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)
let topographicMap = contents.components(separatedBy: "\r\n").map{Array($0).map{Int(String($0)) ?? -1}}
// print (topographicMap)

// one of these days I'll figure out how to use the aoc lib
struct Position : Hashable {
	var x: Int
	var y: Int

	static func + (left: Position, right: Position) -> Position {
        return Position(x: left.x + right.x,
                    	y: left.y + right.y)
    }
}

// safe extension to collection
extension Collection {
    // Returns the element at the specified index if it is within bounds, otherwise nil.
    subscript(safe index: Index) -> Element? {
        indices.contains(index) ? self[index] : nil
    }
}

// recursive flood fill at each 0?
func countTrails(startAt location: Position, on topographicMap: [[Int]], found: inout Set<Position>) -> Int {
	if topographicMap[safe: location.y]?[safe: location.x] == 9 && !found.contains(Position(x: location.x, y: location.y)) {
		found.insert(Position(x: location.x, y: location.y))
		return 1
	} else {
		var subcount = 0

		for direction in [Position(x: -1, y: 0), Position(x: 1, y: 0), Position(x: 0, y: -1), Position(x: 0, y: 1)] {
			let nextLocation = location + direction
			if (topographicMap[safe: nextLocation.y]?[safe: nextLocation.x] ?? 0) - 1 == topographicMap[safe: location.y]?[safe: location.x] {
				subcount += countTrails(startAt: nextLocation, on: topographicMap, found: &found)
			}
		}

		return subcount
	}
}

func countRatings(startAt location: Position, on topographicMap: [[Int]]) -> Int {
	if topographicMap[safe: location.y]?[safe: location.x] == 9 {
		return 1
	} else {
		var subcount = 0

		for direction in [Position(x: -1, y: 0), Position(x: 1, y: 0), Position(x: 0, y: -1), Position(x: 0, y: 1)] {
			let nextLocation = location + direction
			if (topographicMap[safe: nextLocation.y]?[safe: nextLocation.x] ?? 0) - 1 == topographicMap[safe: location.y]?[safe: location.x] {
				subcount += countRatings(startAt: nextLocation, on: topographicMap)
			}
		}

		return subcount
	}
}

var part_1 = 0
var part_2 = 0
for row in 0..<topographicMap.count {
	for column in 0..<topographicMap[row].count {
		if topographicMap[row][column] == 0 {
			// trailhead!
			// heya swifties, do ya like this naming convention?
			var found: Set<Position> = []
			part_1 += countTrails(startAt: Position(x: column, y: row), on: topographicMap, found: &found)
			part_2 += countRatings(startAt: Position(x: column, y: row), on: topographicMap)
		}
	}
}

print("Part 1: \(part_1)")
print("Part 2: \(part_2)")
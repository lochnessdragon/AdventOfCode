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
func countTrailsAndRatings(startAt location: Position, on topographicMap: [[Int]], found: inout Set<Position>) -> (Int, Int) {
	if topographicMap[safe: location.y]?[safe: location.x] == 9 {
		var trailsAndRatings = (0, 1)
		if !found.contains(Position(x: location.x, y: location.y)) {
			// trails increase
			found.insert(Position(x: location.x, y: location.y))
			trailsAndRatings.0 += 1
		}

		return trailsAndRatings
	} else {
		var subTrailsAndRatings = (0, 0)

		for direction in [Position(x: -1, y: 0), Position(x: 1, y: 0), Position(x: 0, y: -1), Position(x: 0, y: 1)] {
			let nextLocation = location + direction
			if (topographicMap[safe: nextLocation.y]?[safe: nextLocation.x] ?? 0) - 1 == topographicMap[safe: location.y]?[safe: location.x] {
				let (trails, ratings) = countTrailsAndRatings(startAt: nextLocation, on: topographicMap, found: &found)
				subTrailsAndRatings.0 += trails
				subTrailsAndRatings.1 += ratings
			}
		}

		return subTrailsAndRatings
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
			let (trails, ratings) = countTrailsAndRatings(startAt: Position(x: column, y: row), on: topographicMap, found: &found)
			part_1 += trails
			part_2 += ratings
		}
	}
}

print("Part 1: \(part_1)")
print("Part 2: \(part_2)")
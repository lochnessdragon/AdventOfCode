import Foundation

struct Position : Hashable {
	var x: Int
	var y: Int

	static func + (left: Position, right: Position) -> Position {
        return Position(x: left.x + right.x,
                    	y: left.y + right.y)
    }

    static func - (left: Position, right: Position) -> Position {
        return Position(x: left.x - right.x,
                     y: left.y - right.y)
    }

    static func += (left: inout Position, right: Position) {
        left = left + right
    }

    static func -= (left: inout Position, right: Position) {
        left = left - right
    }
}

func inbounds(_ pos: Position, _ extent: Position) -> Bool {
	pos.x >= 0 && pos.y >= 0 && pos.x < extent.x && pos.y < extent.y
}

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)
let lines = contents.components(separatedBy: "\r\n")

// store a hash map of frequency + position for the nodes
var nodes: Dictionary<Character, [Position]> = [:]

var extent = Position(x: lines[0].count, y: lines.count)

for y in 0..<lines.count {
	let line = Array(lines[y])
	for x in 0..<line.count {
		if line[x] != "." {
			if nodes[line[x]] == nil {
				nodes[line[x]] = []
			}

			nodes[line[x]]!.append(Position(x: x, y: y))
		}
	}
}

// calculate the antinodes based on the hashmap of frequency + position
// storing in a hashset
var part_1_antinodes: Set<Position> = []
var part_2_antinodes: Set<Position> = []

for (_, nodes) in nodes {
	for i in 0..<nodes.count {
		for j in (i + 1)..<nodes.count {
			// calculate the two antinodes (for part 1)
			let difference = nodes[i] - nodes[j]
			let one = nodes[i] + difference
			if inbounds(one, extent) {
				part_1_antinodes.insert(one)
			}

			let two = nodes[j] - difference
			if inbounds(two, extent) {
				part_1_antinodes.insert(two)
			}

			// calculate the antinodes for part 2
			// extend out from node 1
			var node_1_extend = nodes[i]
			while inbounds(node_1_extend, extent) {
				part_2_antinodes.insert(node_1_extend)
				node_1_extend += difference
			}

			var node_2_extend = nodes[j]
			while inbounds(node_2_extend, extent) {
				part_2_antinodes.insert(node_2_extend)
				node_2_extend -= difference
			}
		}
	}
}

print("Part 1: \(part_1_antinodes.count)")
print("Part 2: \(part_2_antinodes.count)")
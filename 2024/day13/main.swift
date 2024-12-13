import Foundation

struct Vector2 {
	var x: Int
	var y: Int

	init(_ x: Int, _ y: Int) {
		self.x = x
		self.y = y
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

class Machine {
	var a: Vector2
	var b: Vector2
	var result: Vector2

	init (a: Vector2, b: Vector2, result: Vector2) {
		self.a = a
		self.b = b
		self.result = result
	}

	func pointCost() -> Int {
		let b_count = Double(result.y * a.x - result.x * a.y) / Double(b.y * a.x - b.x * a.y)
		let a_count = Double(Double(result.x) - b_count * Double(b.x)) / Double(a.x)

		if a_count.truncatingRemainder(dividingBy: 1) == 0 && b_count.truncatingRemainder(dividingBy: 1) == 0 {
			return 3 * Int(a_count) + Int(b_count)
		}

		return 0
	}
}

let machineRegex = try Regex("Button A: X\\+([0-9]+), Y\\+([0-9]+)[\r\n|\r|\n]Button B: X\\+([0-9]+), Y\\+([0-9]+)[\r\n|\r|\n]Prize: X=([0-9]+), Y=([0-9]+)")

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)
let machines: [Machine] = contents.components(separatedBy: "\r\n\r\n").map {
	let machineMatch = $0.firstMatch(of: machineRegex)!
	return Machine(a: Vector2(Int(machineMatch[1].substring!)!, Int(machineMatch[2].substring!)!), 
				   b: Vector2(Int(machineMatch[3].substring!)!, Int(machineMatch[4].substring!)!), 
				   result: Vector2(Int(machineMatch[5].substring!)!, Int(machineMatch[6].substring!)!))
}

var part_1 = 0
var part_2 = 0

for machine in machines {
	// solve the system of equations
	part_1 += machine.pointCost()
	machine.result += Vector2(10000000000000, 10000000000000)
	part_2 += machine.pointCost()
}

print("Part 1: \(part_1)")
print("Part 2: \(part_2)")
import Foundation

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)
let lines = contents.components(separatedBy: "\r\n")

// only + and *
// can we make the test values?

class Equation {
	var target: Int = 0
	var nums: [Int] = []

	init(inputLine: String) {
		let parts = inputLine.components(separatedBy:": ")
		target = Int(parts[0])!

		for num in parts[1].components(separatedBy: " ") {
			nums.append(Int(num)!)
		}
	}

	// part 1 functions
	func isValidPart1() -> Bool {
		// does inserting + or * at random positions throughout this equation make it valid?
		return recursiveTestPart1(i: 0, curr: 0)
	}

	func recursiveTestPart1(i: Int, curr: Int) -> Bool {
		if i == nums.count {
			return curr == target
		} else {
			return recursiveTestPart1(i: i + 1, curr: curr + nums[i]) || recursiveTestPart1(i: i + 1, curr: curr * nums[i])
		}
	}

	// part 2 functions
	func isValidPart2() -> Bool {
		// does inserting +, *, or || randomly throughout this equation make it valid?
		return recursiveTestPart2(i: 0, curr: 0)
	}

	func recursiveTestPart2(i: Int, curr: Int) -> Bool {
		if i == nums.count {
			return curr == target
		} else {
			return recursiveTestPart2(i: i + 1, curr: curr + nums[i]) || recursiveTestPart2(i: i + 1, curr: curr * nums[i]) || recursiveTestPart2(i: i + 1, curr: concatentaion(curr, nums[i]))
		}
	}

	// used by part 2
	func concatentaion(_ a: Int, _ b: Int) -> Int {
		return Int(String(a) + String(b))!
	}
}

var equations : [Equation] = []

for line in lines {
	equations.append(Equation(inputLine: line))
}

var part_1 = 0
var part_2 = 0

for equation in equations {
	if equation.isValidPart1() {
		part_1 += equation.target
		part_2 += equation.target
	} else if equation.isValidPart2() {
		part_2 += equation.target
	}
}

print("Part 1: \(part_1)")
print("Part 2: \(part_2)")
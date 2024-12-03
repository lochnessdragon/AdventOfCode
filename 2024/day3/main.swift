import Foundation

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)

let regex = try Regex("mul\\((?<op1>[0-9]{1,3}),(?<op2>[0-9]{1,3})\\)|do\\(\\)|don't\\(\\)")

var part_1 = 0
var part_2 = 0

var enabled = true

for match in contents.matches(of: regex) {
	// check for do or don't first
	if match.0 == "do()" {
		enabled = true
	} else if match.0 == "don't()" {
		enabled = false
	} else {
		let op1 : Int = Int(match[1].substring!)!
		let op2 : Int = Int(match[2].substring!)!
		part_1 += op1 * op2
		if enabled {
			part_2 += op1 * op2	
		}
	}
}

print("Part 1: \(part_1)")
print("Part 2: \(part_2)")
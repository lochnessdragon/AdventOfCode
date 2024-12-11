import Foundation

var stones: [UInt64] = [0] /*{
	let file_url = URL(fileURLWithPath: "data.txt")
	let contents = try String(contentsOf: file_url)
	return contents.components(separatedBy:" ").map { UInt64($0)! }
}*/

/// returns the number of digits in an integer
func digits(_ a: UInt64) -> UInt {
	UInt(floor(log10(Double(a)))) + 1
}

// 220677 is not the right answer, but its close
// 220722 is the right answer for part 1

// if it is a zero, change to a 1
// if the number of digits is even, split the number in half
// else, multiply the number by 2024

// # optimization
// - memoize addedStones(num: Int, steps: Int) -> Int

/// returns the number of stones that will exist after steps steps
func addedStones(num: Int, steps: UInt) -> Int {
	1
}

//print(addedStones(num: 0, steps: 1))

for _ in 0..<25 {
	var i = 0
	while i < stones.count {

		if stones[i] == 0 {
			stones[i] = 1
		} else if digits(stones[i]) % 2 == 0 {
			// split the integer
			let stone_str = String(stones[i])
			let halfway = stone_str.index(stone_str.startIndex, offsetBy: stone_str.count / 2)
			let left = UInt64(stone_str[stone_str.startIndex..<halfway])!
			let right = UInt64(stone_str[halfway..<stone_str.endIndex])!
			//print("\(stone_str) splits into \(left) and \(right)")
			stones[i] = left
			stones.insert(right, at: i + 1)
			i += 1
		} else {
			stones[i] *= 2024 
		}
		i += 1
	}

	//print("\(stones) - \(stones.count)")
}

print("Part 1: \(stones.count)")
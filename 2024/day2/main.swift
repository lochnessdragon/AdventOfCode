import Foundation

// Part 1: 202
// Part 2: 271

// isSafe: returns true if the list of nums is safe
func isSafe(_ nums: [Int]) -> Bool {
	var counter = 0
	var last = nums[0]
	var large_jump = false
	for i in 1..<nums.count {
		let diff = nums[i] - last
		
		large_jump = abs(diff) > 3 || large_jump

		counter += diff.signum()

		last = nums[i]
	}

	return (abs(counter) == (nums.count - 1)) && !large_jump
}

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)
let lines = contents.components(separatedBy: "\r\n") // need to find a better (ending-dependent) way to do this other than crlf

var safe_reports = 0 // part 1
var safe_reports_dampened = 0 // part 2
/*
func safeWhenDropped(diffs in_diffs: [Int], dropIdx: Int) -> Bool {
	var diffs = in_diffs

	if dropIdx != 0 && dropIdx != (diffs.count - 1) {
		// merge towards the larger absolute value
		if abs(diffs[dropIdx - 1]) > abs(diffs[dropIdx + 1]) {
			diffs[dropIdx - 1] = diffs[dropIdx - 1] + diffs[dropIdx]
		} else {
			diffs[dropIdx + 1] = diffs[dropIdx] + diffs[dropIdx + 1]
		}
	}

	diffs.remove(at: dropIdx)

	print("\(in_diffs): Testing adjusted: \(diffs)")

	var counter = 0
	for i in 0..<diffs.count {
		counter += diffs[i].signum()
		if abs(diffs[i]) > 3 || abs(diffs[i]) == 0 {
			return false
		}
	}

	if abs(counter) != diffs.count {
		return false
	}

	return true
} */

for i in 0..<lines.count {
	let parts = lines[i].split(separator: " ").map { Int($0)! }
	if isSafe(parts) {
		safe_reports += 1
		safe_reports_dampened += 1
	} else {
		// brute-force method (correct)
		var found_safe = false
		for i in 0..<parts.count {
			var parts_copy = parts
			parts_copy.remove(at: i)
			if (isSafe(parts_copy)) {
				safe_reports_dampened += 1
				found_safe = true
				break;
			}
		}
	}

	/*var last = parts[0]
	var inc_dec = (parts[1] - parts[0]).signum()
	var up_or_down = 0
	var diffs : [Int] = []
	var safe = true

	for j in 1..<parts.count {
		diffs.append(parts[j] - last)

		let absolute_value = abs(parts[j] - last)

		up_or_down += (parts[j] - last).signum()

		if absolute_value > 3 || absolute_value == 0 || (parts[j] - last).signum() != inc_dec {
			safe = false
		}

		last = parts[j]
	}

	if !safe {
		safe_reports -= 1

		// can dampen save if:
		// * only one 0 in the array (and no other issues present)
		// - or -
		// * a difference that goes against the grain plus the one before matches the grain
		if abs(up_or_down) == (diffs.count - 1) && diffs.contains(0) {
			print("Zed Testing: \(parts)")
			if safeWhenDropped(diffs: diffs, dropIdx: diffs.firstIndex(where: {$0 == 0})!) {
				safe_reports_dampened += 1
				print("[\(i)] \(parts) - safe!")
			}
		} else if abs(up_or_down) == (diffs.count - 2) && !diffs.contains(0) {
			print("Diff Testing: \(parts)")
			if up_or_down > 0 {
				let neg_idx = diffs.firstIndex(where: {$0 < 0})!
				if safeWhenDropped(diffs: diffs, dropIdx: neg_idx) {
					safe_reports_dampened += 1
					print("[\(i)] \(parts) - safe!")
				}
			} else if up_or_down < 0 {
				let pos_idx = diffs.firstIndex(where: {$0 > 0})!
				if safeWhenDropped(diffs: diffs, dropIdx: pos_idx) {
					safe_reports_dampened += 1
					print("[\(i)] \(parts) - safe!")
				}
			}
		}

	} else {
		safe_reports_dampened += 1
	}*/
}

print ("Part 1: \(safe_reports)")
print ("Part 2: \(safe_reports_dampened)")
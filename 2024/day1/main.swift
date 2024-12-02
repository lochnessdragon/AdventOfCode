import Foundation

let url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: url)
let lines = contents.components(separatedBy: "\r\n")
print(lines)
var group1 : [Int] = []
var group2: [Int] = []

for i in 0..<lines.count {
	let parts = lines[i].split(separator: " ")
	group1.append(Int(parts[0])!)
	group2.append(Int(parts[1])!)
}

group1.sort()
group2.sort()
print(group1)
print(group2)

print ("Collecting difference")
var difference_sum = 0
var similarity_sum = 0

let counts = group2.reduce(into: [:]) { $0[$1, default: 0] += 1 }

for i in 0..<group1.count {
	difference_sum += abs(group1[i] - group2[i])
	similarity_sum += group1[i] * (counts[group1[i]] ?? 0)
}

print ("Part 1: \(difference_sum)")
print ("Part 2: \(similarity_sum)")
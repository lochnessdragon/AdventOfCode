import Foundation

let file_url = URL(fileURLWithPath: "test.txt")
let contents = try String(contentsOf: file_url)
let sections = contents.components(separatedBy: "\r\n\r\n")

// parse into dependency tree?
// bidi lookup table
// a|b
// set by dependency i.e. b
// lookup by dependent i.e. a

// parse the dependencies
class DependencyTree {
	var dependencies: [(Int, Int)] = []
	var markedIndices: [Int] = []

	init(dependencyStr: String) {
		for dependency in dependencyStr.components(separatedBy: "\r\n") {
			let parts = dependency.components(separatedBy: "|")
			dependencies.append((Int(parts[0])!, Int(parts[1])!))
		}
	}

	func markByDependent(dependent: Int) {
		for i in 0..<dependencies.count {
			let dependency = dependencies[i]
			if dependency.1 == dependent {
				markedIndices.append(i)
			}
		}
	}

	func lookupByDependency(dependencyNum: Int) -> Bool {

		for i in 0..<dependencies.count {
			let dependency = dependencies[i]
			if dependency.0 == dependencyNum {
				if markedIndices.contains(i) {
					return true
				}
			}
		}

		return false // has not been flagged
	}

	// returns true if a is lower down the tree than b, false if otherwise
	func compare(a: Int, b: Int) -> Bool {
		// bfs this bad boy
		for dependency in dependencies {
			if dependency.1 == b {
				if dependency.0 == a {
					return true
				} else if compare(a: a, b: dependency.0) {
					return true
				}
			}
		}
		return false
	}

	func reset() {
		markedIndices = []
	}
}

print("Creating tree")
var tree = DependencyTree(dependencyStr: sections[0])

print("Parsing updates")
// parse the updates
var updates: [[Int]] = []

for update in sections[1].components(separatedBy: "\r\n") {
	updates.append(update.components(separatedBy: ",").map {Int($0)!})
}

print("Meaty section")

var part_1 = 0
var part_2 = 0

for update in updates {
	var good = true
	for page in update {
		if tree.lookupByDependency(dependencyNum: page) {
			good = false
			break // bad
		}

		tree.markByDependent(dependent: page)
	}

	if good {
		part_1 += update[update.count / 2]
	} else {
		print("Attempting to sort: \(update)")
		// correctly order the pages
		var correctly_sorted = update
		
		correctly_sorted.sort {
			tree.compare(a: $0, b: $1)
		}

		print(correctly_sorted)
		
		// find the middle
		part_2 += correctly_sorted[correctly_sorted.count / 2]
	}

	tree.reset()
}

print("Part 1: \(part_1)")
print("Part 2: \(part_2)")
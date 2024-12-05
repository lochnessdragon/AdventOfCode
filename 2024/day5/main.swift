import Foundation

let file_url = URL(fileURLWithPath: "data.txt")
let contents = try String(contentsOf: file_url)
let sections = contents.components(separatedBy: "\r\n\r\n")

// parse into dependency tree?
// bidi lookup table
// a|b
// set by dependency i.e. b
// lookup by dependent i.e. a

// parse the dependencies
class DependencyTree {
	// these are the edges of the tree
	var dependencies: [(Int, Int)] = []
	// these are all the edges that are required by the current update list
	var markedIndices: [Int] = []

	// initialize from the list of edges
	// "12|24
	//  24|56
	//  . . ."
	init(dependencyStr: String) {
		for dependency in dependencyStr.components(separatedBy: "\r\n") {
			let parts = dependency.components(separatedBy: "|")
			dependencies.append((Int(parts[0])!, Int(parts[1])!))
		}
	}

	// adds the indices of edges based on their dependency
	func markByDependent(dependent: Int) {
		for i in 0..<dependencies.count {
			let dependency = dependencies[i]
			if dependency.1 == dependent {
				markedIndices.append(i)
			}
		}
	}

	// looks up whether an edge has already been marked by its depdencent
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
	// this function stack overflows on the main data
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

	// a much dumber function for a much dumber sorting algo
	// returns 1 if a is directly above b
	// returns -1 if b is directly above a
	// returns 0 if they are separated by more than 1
	func upOrDown(a: Int, b: Int) -> Int {
		for dependency in dependencies {
			if dependency.1 == b && dependency.0 == a {
				return -1 // down
			} else if dependency.1 == a && dependency.0 == b {
				return 1 // up
			}
		}

		return 0 // unknown
	}

	func reset() {
		markedIndices = []
	}
}

var tree = DependencyTree(dependencyStr: sections[0])

// parse the updates
var updates: [[Int]] = []

for update in sections[1].components(separatedBy: "\r\n") {
	updates.append(update.components(separatedBy: ",").map {Int($0)!})
}

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
		// correctly order the pages
		var left_to_sort = update
		var sorted = [left_to_sort.popLast()!]
		
		while left_to_sort.count > 0 {
			for i in 0..<left_to_sort.count {
				var found = false
				for j in 0..<sorted.count {
					let relative_pos = tree.upOrDown(a: left_to_sort[i], b: sorted[j])
					if relative_pos == -1 {
						sorted.insert(left_to_sort.remove(at: i), at: j)
						found = true
						break
					} else if relative_pos == 1 {
						// move up the list until relative pos == -1 or the end of the list is reached
						for k in (j+1)..<sorted.count {
							if tree.upOrDown(a: left_to_sort[i], b: sorted[k]) == -1 {
								sorted.insert(left_to_sort.remove(at: i), at: k)
								found = true
								break
							}
						}

						// nothing above it, add it to the end
						if !found {
							sorted.insert(left_to_sort.remove(at: i), at: sorted.endIndex)
							found = true
						}

						break
					}
				}
				if found {
					break
				}
			}
		}
		
		// find the middle
		part_2 += sorted[sorted.count / 2]
	}

	tree.reset()
}

print("Part 1: \(part_1)")
print("Part 2: \(part_2)")
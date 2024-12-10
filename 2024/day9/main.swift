import Foundation

// 4202387060905 isn't right, but its close

// examples:
// 12345 = 022111222 = 60
// 90909 = 000000000111111111222222222 = 513
// 2333133121414131402 = 1928

func get_int(_ c: String.Element) -> Int {
	Int(String(c))!
}

let file_url = URL(fileURLWithPath: "data.txt")
let contents = Array(try String(contentsOf: file_url))

let max_id = (contents.count) / 2
print("Max Id: \(max_id)")

var part_1: UInt64 = 0

var contents_idx = 0

// used to fill in empty space
var last_available_id = max_id
var end_block_idx = contents.endIndex - (contents.endIndex % 2)
var end_written_blocks = 0

// used to spread out file blocks
var current_file_id = 0
var written_blocks = 0

// the current index in the hard drive
// ex: 302
// 00011
//    ^
// hd_idx == 3
var hd_idx = 0

while contents_idx < end_block_idx {
	if contents_idx % 2 == 0 {
		// we are on a file block, part_1 += current_file_id * hd_idx
		if written_blocks == get_int(contents[contents_idx]) {
			contents_idx += 1
			written_blocks = 0
			current_file_id += 1
			continue
		}

		part_1 += UInt64(current_file_id * hd_idx)
		written_blocks += 1
		//print(current_file_id, terminator: "")
	} else {
		// we are at empty space, fill in with numbers at the end of the list
		if written_blocks == get_int(contents[contents_idx]) {
			written_blocks = 0
			contents_idx += 1
			continue
		}

		part_1 += UInt64(last_available_id * hd_idx)
		end_written_blocks += 1
		written_blocks += 1
		//print(last_available_id, terminator: "")

		// move onto the next end block
		if end_written_blocks == get_int(contents[end_block_idx]) {
			end_written_blocks = 0
			end_block_idx -= 2
			last_available_id -= 1
		}
	}

	hd_idx += 1
}

if contents_idx == end_block_idx {
	for _ in end_written_blocks..<get_int(contents[end_block_idx]) {
		part_1 += UInt64(current_file_id * hd_idx)
		//print(current_file_id, terminator: "")
		hd_idx += 1
	}
}

//print("")
//print("0099811188827773336446555566..............")

print("Part 1: \(part_1)")

// part 2
// 294 = 
// File(id: Int, span: Int)
// Empty(span: Int)
// hd: [HarddiskSpace]

enum HarddiskSpace {
	// id, span
	case file(Int, Int)
	// span
	case empty(Int)
}

func checksum(hd: [HarddiskSpace]) -> Int {
	var index = 0
	var checksum = 0

	for space in hd {
		switch space {
			case .file(let id, let span):
				for i in 0..<span {
					checksum += (index + i) * id
				}
				//print(String(repeating: "\(id)", count: span), terminator: "")
				index += span
			case .empty(let span):
				//print(String(repeating: ".", count: span), terminator: "")
				index += span
		}
	}

	//print("")

	return checksum
}

// safe extension to collection
extension Collection {
    // Returns the element at the specified index if it is within bounds, otherwise nil.
    subscript(safe index: Index) -> Element? {
        indices.contains(index) ? self[index] : nil
    }
}

// create the hard drive
var hd : [HarddiskSpace] = []

for (index, block_size_char) in contents.enumerated() {
	let block_size = get_int(block_size_char)
	if block_size > 0 {
		if index % 2 == 0 {
			// file
			hd.append(.file(index / 2, block_size))
		} else {
			// empty space
			hd.append(.empty(block_size))
		}
	} else {
		// all files should have a block size greater than 0
		assert(index % 2 != 0)
	}
}

// move the blocks in the hard drive
for id in (0...max_id).reversed() {
	// search for the block backwards from the end of the list
	// these should never be nil, but if they are that's an early error
	var file_index: Int? = nil
	var file_size: Int? = nil

	for i in (0..<hd.count).reversed() {
		if case let .file(potential_id, potential_file_size) = hd[i] {
			if potential_id == id {
				file_index = i
				file_size = potential_file_size
				break
			}
		}
	}

	assert(file_index != nil)
	assert(file_size != nil)

	// search for empty space that fits it from the front of the list (stopping before we hit the index of the block)
	var empty_index: Int? = nil
	var empty_size: Int? = nil
	for i in 0..<file_index! {
		if case let .empty(empty_space) = hd[i] {
			if file_size! <= empty_space {
				empty_index = i
				empty_size = empty_space
				break
			}
		}
	}

	// pop the index at its current location, pushing it at the empty space location, adjusting the empty space (either reducing it's size or dropping it entirely)
	if let empty_space_index = empty_index {
		// remove the moved file
		let file = hd.remove(at: file_index!)

		// expand the empty space to the left or right of the moved file
		// if none can be found, create an empty space at the file index
		if case var .empty(left_empty) = hd[safe: file_index! - 1], case let .empty(right_empty) = hd[safe: file_index!] {
			left_empty += file_size! + right_empty // combine all the empties in a row
			hd.remove(at: file_index!) // drop the ending (duplicate) empty entry
			hd[file_index! - 1] = .empty(left_empty)
			if empty_space_index == file_index! - 1 {
				empty_size = left_empty 
			}
		} else if case var .empty(left_empty) = hd[safe: file_index! - 1] {
			left_empty += file_size!
			hd[file_index! - 1] = .empty(left_empty)
			if empty_space_index == file_index! - 1 {
				empty_size = left_empty 
			}
		} else if case var .empty(right_empty) = hd[safe: file_index!] {
			right_empty += file_size!
			hd[file_index!] = .empty(right_empty)
		} else {
			// nothing to the left or right, place it right here
			hd.insert(.empty(file_size!), at: file_index!)
		}

		if file_size! == empty_size! {
			hd.remove(at: empty_space_index)
		} else {
			hd[empty_space_index] = .empty(empty_size! - file_size!)
		}

		hd.insert(file, at: empty_space_index)
	}
}

// 6423236616653 too low

print("Part 2: \(checksum(hd: hd))")
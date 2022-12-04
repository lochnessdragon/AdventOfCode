use std::fs;
use std::io;
use std::io::BufRead;

fn main() {
    // read file
    let file = fs::File::open("test.txt").expect("failed to open file");
    let reader = io::BufReader::new(file);
    let mut ranges: Vec<[u64; 4]> = Vec::new();
    for wrapped_line in reader.lines() {
        let line = wrapped_line.expect("Failed to read line");
        let parts: Vec<&str> = line.split(",").collect();
        let rangeAParts: Vec<u64> = parts[0]
            .split("-")
            .map(|value| value.parse::<u64>().unwrap())
            .collect();
        let rangeBParts: Vec<u64> = parts[1]
            .split("-")
            .map(|value| value.parse::<u64>().unwrap())
            .collect();
        ranges.push([rangeAParts[0], rangeAParts[1], rangeBParts[0], rangeBParts[1]]);
    }

    // part 1
    {
        let mut full_containments = 0;
        let ranges_ref: &Vec<[u64; 4]> = ranges.as_ref();
        for range in ranges_ref {
            let mut full = false;

            if range[0] <= range[2] && range[1] >= range[3] {
                full = true;
            }

            if range[0] >= range[2] && range[1] <= range[3] {
                full = true;
            }

            if full {
                full_containments += 1;
            }
        }

        println!("Part 1: Pairs where one fully contains the other: {full_containments}");
    }

    // part 2
    {
        let mut overlap: u64 = 0;
        for range in ranges {
            if range[0] <= range[2] && range[2] <= range[1] {
                overlap += 1;
                continue;
            }

            if range[2] <= range[0] && range[0] <= range[3] {
                overlap += 1;
                continue;
            }
        }
        println!("Part 2: Overlapping Pairs: {overlap}");
    }
}

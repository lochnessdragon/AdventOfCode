use std::env;
use std::fs;
use std::io;
use std::io::BufRead;

fn main() {
    //let file_contents = fs::read_to_string("test.txt").expect("File not found.");
    //let mut string = String::new();
    let file = fs::File::open("input.txt").expect("File not found.");
    let mut reader = io::BufReader::new(file);
    // part 1
    let mut part1_score: u32 = 0;
    for line in reader.lines() {
        let input = line.expect("unable to read line");
        let parts: Vec<&str> = input.split(' ').collect();
        
        if parts[1] == "X" {
            part1_score += 1;
        }
        if parts[1] == "Y" {
            part1_score += 2;
        }
        if parts[1] == "Z" {
            part1_score += 3;
        }

        let opponent: i16 = (parts[0].as_bytes()[0] - "A".as_bytes()[0]).into();
        let me: i16 = (parts[1].as_bytes()[0] - ("X".as_bytes()[0])).into();

        if opponent == me - 1 || me == opponent - 2 {
            part1_score += 6;
        }
        if opponent == me {
            part1_score += 3;
        }
        if opponent == me + 1 || me - 2 == opponent {
            part1_score += 0;
        }
    }

    // part 2
    let part2_score: u32 = 0;

    println!("Part 1: Score={part1_score}")
}

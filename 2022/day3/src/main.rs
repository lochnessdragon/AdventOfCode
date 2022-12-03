use std::fs;
use std::io;
use std::io::BufRead;
use std::io::Seek;

fn get_duplicate(a: &str, b: &str) -> char {
    for c in a.chars() {
        if b.contains(c) {
            return c;
        }
    }

    'A'
}

fn get_duplicate3(a: &str, b: &str, c: &str) -> char {
    for character in a.chars() {
        if b.contains(character) && c.contains(character){
            return character;
        }
    }

    'A'
}

fn get_priority(c: char) -> u8 {
    let mut ascii_buf = [0; 1];
    let ascii_str = c.encode_utf8(&mut ascii_buf);
    let ascii_val = ascii_buf[0];

    let ascii_A: u8 = "A".as_bytes()[0];
    let ascii_a: u8 = "a".as_bytes()[0];
    let ascii_Z: u8 = "Z".as_bytes()[0];
    if ascii_val >= ascii_a {
        return ascii_val - (ascii_a - 1);
    }

    if ascii_val <= ascii_Z {
        return (ascii_val - (ascii_A - 1)) + 26;
    }

    0
}

fn main() {
    let file = fs::File::open("input.txt").expect("File not found.");
    let mut reader = io::BufReader::new(file);

    // part 1
    {
        let mut priority_sum1: u64 = 0;
        let reader_ref = &mut reader;
        for wrapped_line in reader_ref.lines() {
            let line = wrapped_line.expect("Failed to read line.");
            let (part1, part2) = line.split_at(line.len() / 2);
            let duplicate = get_duplicate(part1, part2);
            // translate duplicate to priority
            priority_sum1 += get_priority(duplicate) as u64;
        }

        println!("Part 1: Priority Sum: {priority_sum1}");
    }

    // part 2
    {
        let mut priority_sum2: u64 = 0;
        reader.seek(io::SeekFrom::Start(0)).expect("Should be able to seek to start of file.");
        let reader_ref = &mut reader;
        let lines: Vec<_> = reader_ref.lines().collect();
        let total = lines.len();

        for x in 0..(total / 3) {
            let line1 = lines[x * 3].as_ref().expect("Failed to read line");
            let line2 = lines[(x*3) + 1].as_ref().expect("Failed to read line");
            let line3 = lines[(x*3) + 2].as_ref().expect("Failed to read line");

            let duplicate = get_duplicate3(line1, line2, line3);
            let priority = get_priority(duplicate) as u64;
            //println!("Char: {duplicate} Priority: {priority}");
            priority_sum2 += priority;
        }

        println!("Part 2: Priority Sum: {priority_sum2}");
    }
}

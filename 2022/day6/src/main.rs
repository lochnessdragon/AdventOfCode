use std::fs;
use std::io;
use std::io::BufRead;
use std::io::Write;

fn check_duplicates(buf: &[u8]) -> bool {
    for i in 0..buf.len() {
        let c = buf[i];
        for j in 0..buf.len() {
            if j == i {
                continue;
            }

            if c == buf[j] {
                return true;
            }
        }
    }

    return false;
}

fn main() {
    let file = fs::File::open("input.txt").expect("Failed to find input file");
    let mut reader = io::BufReader::new(file);

    let mut buffer = String::new();
    reader.read_line(&mut buffer).expect("Failed to read line");

    {
        // part 1
        let bytes = buffer.as_bytes();
        for x in 0..(bytes.len() - 3) {
            let mut out = std::io::stdout();
            out.write_all(&bytes[x..(x + 4)]).unwrap();
            println!();
            if !check_duplicates(&bytes[x..(x + 4)]) {
                println!("Part 1: First Start of Packet Marker after {} bytes", x + 4);
                break;
            }
        }
    }

    {
        // part 2
        let bytes = buffer.as_bytes();
        for x in 0..(bytes.len() - 13) {
            let mut out = std::io::stdout();
            out.write_all(&bytes[x..(x + 14)]).unwrap();
            println!();
            if !check_duplicates(&bytes[x..(x + 14)]) {
                println!("Part 2: First Start of Message Marker after {} bytes", x + 14);
                break;
            }
        }
    }
}

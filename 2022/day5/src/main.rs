use std::fs;
use std::io;
use std::io::BufRead;
use std::io::Read;
use std::io::Seek;
use std::io::Write;
use std::str;

fn main() {
    let file = fs::File::open("input.txt").expect("Failed to open file.");
    let mut reader = io::BufReader::new(file);

    // read in the stacks
    let stack_count: u32;

    let mut stack_buffer: Vec<u8> = Vec::new();

    reader
        .read_until('1' as u8, &mut stack_buffer)
        .expect("Improperly formatted file.");

    let mut stack_size_str = String::new();
    reader
        .read_line(&mut stack_size_str)
        .expect("Failed to get the size of the stack");
    stack_size_str.pop(); // remove newline
    stack_size_str.pop(); // remove space
    stack_count = stack_size_str
        .pop()
        .expect("Failed to get the last number")
        .to_digit(10)
        .expect("Failed to parse the str to a digit");

    println!("Stack size: {stack_count}");

    let mut stacks: Vec<Vec<char>> = Vec::new();
    for _ in 0..stack_count {
        stacks.push(Vec::new());
    }

    reader.seek(io::SeekFrom::Start(0)).expect("Failed to seek");
    let stack_height = stack_buffer
        .iter()
        .filter(|&x| *x == "\n".as_bytes()[0])
        .count();
    println!("Tallest stack: {stack_height}");
    for _ in 0..stack_height {
        for i in 0..stack_count {
            let mut buf: [u8; 3] = [0; 3];
            reader.read(&mut buf).expect("Failed to read line chunk");
            let mut out = std::io::stdout();
            out.write_all(&buf).unwrap();
            println!();

            if buf[0] == "[".as_bytes()[0] {
                // its a char add it to the vector
                let c = buf[1] as char;
                println!("Appending {c} to buffer {i}");
                stacks
                    .get_mut(i as usize)
                    .expect("Failed to unwrap vec")
                    .insert(0, buf[1] as char);
            }

            // read either the space or newline that denotes a new chunk
            let mut throwaway_buf: [u8; 1] = [0];
            reader
                .read(&mut throwaway_buf)
                .expect("Need to read space between boxes");
        }
    }

    // execute commands on the stacks array
    reader
        .seek(io::SeekFrom::Start(
            (stack_buffer.len() + stack_size_str.len() + 2)
                .try_into()
                .unwrap(),
        ))
        .expect("Failed to seek");

    let mut throwaway_buffer = String::new();
    reader
        .read_line(&mut throwaway_buffer)
        .expect("Failed to get the start of instructions");
    // part 1
    for unwrapped_line in reader.lines() {
        let line = unwrapped_line.expect("Failed to read line");
        println!("{line}");
        let parts: Vec<&str> = line.split(" ").collect();
        if parts.len() == 1 {
            continue;
        }

        let count = parts[1].parse::<u32>().expect("Failed to parse int");
        let src_stack = parts[3].parse::<u32>().expect("Failed to parse int") - 1; // get the stack # and convert it to an index
        let dest_stack = parts[5].parse::<u32>().expect("Failed to parse int") - 1; // get the stack # and convert it to an index
        println!("Moving {count} boxes from stack {src_stack} to stack {dest_stack}.");

        // // would be part 1
        // for _ in 0..count {
        //     let c = stacks
        //         .get_mut(src_stack as usize)
        //         .expect("Failed to get src stack")
        //         .pop()
        //         .expect("Failed to get char");
        //     stacks
        //         .get_mut(dest_stack as usize)
        //         .expect("Failed to get dest stack")
        //         .push(c);
        // }

        // part 2
        let dest_stack_end = stacks
            .get_mut(dest_stack as usize)
            .expect("failed to get dest stack")
            .len();
        for _ in 0..count {
            let c = stacks
                .get_mut(src_stack as usize)
                .expect("Failed to get src stack")
                .pop()
                .expect("Failed to get char");
            stacks
                .get_mut(dest_stack as usize)
                .expect("Failed to get dest stack")
                .insert(dest_stack_end, c);
        }
    }

    // part 1
    for i in 0..stack_count {
        let c: &char = stacks
            .get_mut(i as usize)
            .expect("Failed to get stack")
            .last()
            .expect("Failed to get the last element");
        print!("{c}");
    }
    println!();
}

use std::io::BufRead;
use std::ops::Div;
use std::{fs, io};


fn main() {
    let file = fs::File::open("input.txt").expect("Failed to open file");
    let mut reader = io::BufReader::new(file);

    let mut regX: i32 = 1;

    let mut running_total = 0;

    let mut skip_cycles = 0;
    let mut inc_x = 0;

    let mut screen: [[bool; 40]; 6] = [[false; 40]; 6];

    for cycle in 1..241 {
        if skip_cycles == 0 {
            let mut line = String::new();
            reader.read_line(&mut line).unwrap();

            if line.starts_with("addx") {
                inc_x = line.split(" ").collect::<Vec<&str>>()[1]
                    .trim()
                    .parse::<i32>()
                    .unwrap();
                skip_cycles = 2;
            }
        } 

        // check x value (part 1)
        if (cycle - 20) % 40 == 0 {
            // println!("Cycle: {} Reg X: {} Sig Str: {}", cycle, regX, regX * cycle);
            running_total += regX * cycle;
        }

        // update crt (part 2)
        let crt_x = (cycle - 1) % 40;
        let sprite_pos = regX;// - 1;
        if sprite_pos - 1 <= crt_x && sprite_pos + 1 >= crt_x {
            // let crt_y = math::round::ceil(cycle as f32 / 40 as f32, 0);
            let crt_y = (cycle - 1) as usize / 40 as usize;
            println!("{cycle}: {crt_y} x: {regX}");
            screen[crt_y as usize][crt_x as usize] = true;
        }

        if cycle >= 198 && cycle <= 201 {
            println!("{cycle}: x = {regX} CrtX={crt_x} skip_cycles={skip_cycles}");
        }
        
        if skip_cycles > 0 {
            skip_cycles -= 1;
            if skip_cycles == 0 {
                regX += inc_x;
            }
        }
    }

    println!("Part 1: {running_total}");

    // print screen
    println!("Part 2:");
    for y in 0..screen.len() {
        for x in 0..screen[y].len() {
            if screen[y][x] {
                print!("#");
            } else {
                print!(".");
            }
        }
        println!();
    }
}

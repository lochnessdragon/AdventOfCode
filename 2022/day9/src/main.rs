use std::collections::HashSet;
use std::fs;
use std::io;
use std::io::BufRead;

#[derive(PartialEq, Eq, Hash, Clone, Copy, Debug)]
struct Vec2 {
    x: i32,
    y: i32,
}

fn print_positions(head_pos: &Vec2, tail_pos: &Vec2) {
    for y in 0..6 {
        for x in 0..6 {
            if head_pos.x == x && head_pos.y == 5 - y {
                print!("H ");
            } else if tail_pos.x == x && tail_pos.y == 5 - y {
                print!("T ");
            } else {
                print!(". ");
            }
        }
        println!();
    }
    println!();
}

fn main() {
    let file = fs::File::open("input.txt").expect("Failed to open file");
    let reader = io::BufReader::new(file);

    // let mut head_pos = Vec2 { x: 0, y: 0 };
    // let mut tail_pos = Vec2 { x: 0, y: 0 };

    let mut positions: [Vec2; 10] = [Vec2 { x: 0, y: 0 }; 10];

    let mut tail_pos_cache: HashSet<Vec2> = HashSet::new();

    for wrapped_line in reader.lines() {
        let line = wrapped_line.unwrap();
        let parts: Vec<&str> = line.split(" ").collect();
        for _ in 0..parts[1].parse::<u32>().unwrap() {
            if parts[0] == "U" {
                positions[0].y += 1;
            } else if parts[0] == "D" {
                positions[0].y -= 1;
            } else if parts[0] == "L" {
                positions[0].x -= 1;
            } else if parts[0] == "R" {
                positions[0].x += 1;
            } else {
                println!("Didn't move");
            }

            // update knot positions
            for i in 0..positions.len() - 1 {
                if positions[i].x.abs_diff(positions[i+1].x) > 1 || positions[i].y.abs_diff(positions[i+1].y) > 1 {
                    positions[i+1].x += (positions[i].x - positions[i+1].x).signum();
                    positions[i+1].y += (positions[i].y - positions[i+1].y).signum();
                }
            }

            // print_positions(&head_pos, &tail_pos);

            tail_pos_cache.insert(positions[positions.len() - 1]);
        }
    }

    // for pos in tail_pos_cache {
    //     println!("pos = {:?}", pos);
    // }

    let visited_once = tail_pos_cache.len();
    println!("Part 1/2: Locations visited by the tail at least once: {visited_once}");
}

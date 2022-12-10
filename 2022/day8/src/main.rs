use std::fs;
use std::io;
use std::io::BufRead;

fn main() {
    let file = fs::File::open("test.txt").expect("Failed to open file");
    let reader = io::BufReader::new(file);

    let mut grid: Vec<Vec<u8>> = vec![];

    for wrapped_line in reader.lines() {
        let line = wrapped_line.unwrap();
        let mut row: Vec<u8> = vec![];
        for c in line.as_bytes() {
            let num = c - ('0' as u8);
            // println!("{num}");
            row.push(num);
        }

        grid.push(row);
    }

    {
        // part 1
        let mut total_visible: u64 = 0;

        for y in 0..grid.len() {
            for x in 0..grid[y].len() {
                let mut offset = 1;
                let tree_height = grid[y][x];
                let mut check_pos_x = true;
                let mut check_neg_x = true;
                let mut check_pos_y = true;
                let mut check_neg_y = true;
                while check_pos_x || check_neg_x || check_pos_y || check_neg_y {
                    // check positive x
                    if check_pos_x {
                        let pos_x = x + offset;
                        if pos_x >= grid[y].len() {
                            total_visible += 1;
                            break;
                        }

                        if grid[y][pos_x] >= tree_height {
                            check_pos_x = false;
                        }
                    }

                    // check negative x
                    if check_neg_x {
                        let neg_x = x as i32 - offset as i32;
                        if neg_x < 0 {
                            total_visible += 1;
                            break;
                        }

                        if grid[y][neg_x as usize] >= tree_height {
                            check_neg_x = false;
                        }
                    }

                    // check positive y
                    if check_pos_y {
                        let pos_y = y + offset;
                        if pos_y >= grid.len() {
                            total_visible += 1;
                            break;
                        }

                        if grid[pos_y][x] >= tree_height {
                            check_pos_y = false;
                        }
                    }

                    // check negative y
                    if check_neg_y {
                        let neg_y = y as i32 - offset as i32;
                        if neg_y < 0 {
                            total_visible += 1;
                            break;
                        }

                        if grid[neg_y as usize][x] >= tree_height {
                            check_neg_y = false;
                        }
                    }

                    offset += 1;
                }
            }
        }

        println!("Part 1: Total trees visible: {total_visible}");
    }

    {
        // part 2
        let mut highest_score = 0;

        for y in 0..grid.len() {
            for x in 0..grid[y].len() {
                let mut scenic_score = 1;
                let tree_height = grid[y][x];

                { // positive x
                    let mut check = true;
                    let mut offset = 1;
                    while check {
                        let pos_x = x + offset;

                        if pos_x >= grid[y].len() {
                            break;
                        }

                        if grid[y][pos_x] >= tree_height {
                            check = false;
                        }

                        offset += 1;
                    }

                    scenic_score *= offset - 1;
                }

                { // negative x
                    let mut check = true;
                    let mut offset = 1;
                    while check {
                        let neg_x = x as i32 - offset as i32;

                        if neg_x < 0 {
                            break;
                        }

                        if grid[y][neg_x as usize] >= tree_height {
                            check = false;
                        }

                        offset += 1;
                    }

                    scenic_score *= offset - 1;
                }

                { // positive x\y
                    let mut check = true;
                    let mut offset = 1;
                    while check {
                        let pos_y = y + offset;

                        if pos_y >= grid.len() {
                            break;
                        }

                        if grid[pos_y][x] >= tree_height {
                            check = false;
                        }

                        offset += 1;
                    }

                    scenic_score *= offset - 1;
                }

                { // negative y
                    let mut check = true;
                    let mut offset = 1;
                    while check {
                        let neg_y = y as i32 - offset as i32;

                        if neg_y < 0 {
                            break;
                        }

                        if grid[neg_y as usize][x] >= tree_height {
                            check = false;
                        }

                        offset += 1;
                    }

                    scenic_score *= offset - 1;
                }

                if scenic_score > highest_score {
                    highest_score = scenic_score;
                }
            }
        }

        println!("Part 2: Highest Scenic score: {highest_score}");
    }
}

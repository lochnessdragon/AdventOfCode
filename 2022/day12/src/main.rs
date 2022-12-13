use std::collections::{HashMap, HashSet};
use std::fs;
use std::io;
use std::io::BufRead;

#[derive(Debug, PartialEq, Eq, Hash, Copy, Clone)]
struct Vec2i {
    x: i32,
    y: i32,
}

fn calculate_distances(distances: &mut HashMap<Vec2i, u32>, map: &Vec<Vec<u8>>, position: Vec2i) {
    let height = map[position.y as usize][position.x as usize] as i8;
    let distance = *distances.get(&position).unwrap();

    // println!(
    //     "Position: {:?} Distance: {distance} Height: {height}",
    //     position
    // );
    let new_distance = distance + 1;

    // calculate all the children's positions (if able)
    let mut x_inc: i32 = -1;
    while x_inc <= 1 {
        let new_position = Vec2i {
            x: position.x + x_inc,
            y: position.y,
        };
        if new_position.x >= 0
            && new_position.x < map[position.y as usize].len() as i32
            && map[new_position.y as usize][new_position.x as usize] as i8 >= (height - 1) // part 1 has the reverse
        // make sure the new position is valid
        {
            if *distances.get(&new_position).unwrap() > new_distance {
                distances.insert(new_position, new_distance);
                calculate_distances(distances, map, new_position);
            }
        }

        x_inc += 2;
    }

    let mut y_inc: i32 = -1;
    while y_inc <= 1 {
        let new_position = Vec2i {
            x: position.x,
            y: position.y + y_inc,
        };
        if new_position.y >= 0
            && new_position.y < map.len() as i32
            && map[new_position.y as usize][new_position.x as usize] as i8 >= (height - 1) // part 1 has the reverse (i.e.) <= +1
        // make sure the new position is valid
        {
            if *distances.get(&new_position).unwrap() > new_distance {
                distances.insert(new_position, new_distance);
                calculate_distances(distances, map, new_position);
            }
        }

        y_inc += 2;
    }
}

fn main() {
    let file = fs::File::open("input.txt").expect("Failed to open file");
    let reader = io::BufReader::new(file);

    let mut map: Vec<Vec<u8>> = vec![];
    let mut start_pos: Vec2i = Vec2i { x: 0, y: 0 };
    let mut end_pos: Vec2i = Vec2i { x: 0, y: 0 };

    let mut y = 0;
    for wrapped_line in reader.lines() {
        map.push(vec![]);

        let mut x = 0;
        let line = wrapped_line.unwrap();
        for c in line.as_bytes() {
            if *c == 'S' as u8 {
                // start position, lowest height
                start_pos = Vec2i { x: x, y: y };
                map[y as usize].push(0);
            } else if *c == 'E' as u8 {
                // end position, highest height
                end_pos = Vec2i { x: x, y: y };
                map[y as usize].push('z' as u8 - 'a' as u8);
            } else if *c == '\n' as u8 {
                break;
            } else {
                let height = c - 'a' as u8;
                map[y as usize].push(height);
            }
            x += 1;
        }
        y += 1;
    }

    println!("start_pos={:?} end_pos={:?}", start_pos, end_pos);

    // use djikstra's algorithm to find the shortest path
    let mut distances: HashMap<Vec2i, u32> = HashMap::new();
    for y in 0..map.len() {
        for x in 0..map[y].len() {
            distances.insert(
                Vec2i {
                    x: x as i32,
                    y: y as i32,
                },
                u32::MAX,
            );
        }
    }

    // // part 1, start at the start position
    // distances.insert(start_pos, 0);

    // calculate_distances(&mut distances, &map, start_pos);

    // let shortest_route = distances.get(&end_pos).unwrap();

    // println!("Part 1: Shortest route from start to finish: {shortest_route}");

    // part 2, start at the end position
    distances.insert(end_pos, 0);

    calculate_distances(&mut distances, &map, end_pos);
    let mut shortest_scenic_route = u32::MAX;

    for y in 0..map.len() {
        for x in 0..map[y].len() {
            if map[y][x] == 0 {
                let new_distance = *distances
                    .get(&Vec2i {
                        x: x as i32,
                        y: y as i32,
                    })
                    .unwrap();
                if new_distance < shortest_scenic_route {
                    shortest_scenic_route = new_distance;
                }
            }
        }
    }

    println!("Shortest route from the lowest point: {shortest_scenic_route}");
}

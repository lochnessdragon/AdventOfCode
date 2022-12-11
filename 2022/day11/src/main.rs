use std::fs;
use std::io;
use std::io::BufRead;
use std::io::Read;

#[derive(PartialEq, Debug)]
enum OperationType {
    MULTIPLICATION,
    ADDITION,
    MULTIPLY_BY_SELF,
}

#[derive(Debug)]
struct Monkey {
    items: Vec<u128>,
    operation_type: OperationType,
    operation_value: u128,
    test_value: u128,
    test_je: usize,
    test_jne: usize,
    total_inspections: u128,
}

impl Monkey {
    // looks at the first item in the list
    fn inspect(&mut self, part2Mod: u128) -> (usize, u128) {
        if self.operation_type == OperationType::MULTIPLY_BY_SELF {
            self.items[0] *= self.items[0];
        } else if self.operation_type == OperationType::MULTIPLICATION {
            self.items[0] *= self.operation_value;
        } else {
            self.items[0] += self.operation_value;
        }

        // self.items[0] = self.items[0] / 3; only part 1
        self.items[0] %= part2Mod;

        let monkey_item = if self.items[0] % self.test_value == 0 {
            // it is divisible
            // throw to test_je
            (self.test_je, self.items[0])
        } else {
            // throw to test_jne
            (self.test_jne, self.items[0])
        };

        // we threw the item, remove it
        self.items.remove(0);
        // increment the count
        self.total_inspections += 1;

        return monkey_item;
    }

    fn round(&mut self, part2Mod: u128) -> Vec<(usize, u128)> {
        let mut monkey_item_group: Vec<(usize, u128)> = vec![];

        for _ in 0..self.items.len() {
            monkey_item_group.push(self.inspect(part2Mod));
        }

        return monkey_item_group;
    }
}

fn main() {
    let file = fs::File::open("input.txt").expect("Failed to open file.");
    let mut reader = io::BufReader::new(file);

    let mut monkeys: Vec<Monkey> = vec![];

    // load monkeys
    let mut buffer = String::new();
    reader.read_to_string(&mut buffer).unwrap();
    let mut chunks: Vec<&str> = buffer.split("\n\n").collect();

    for chunk in chunks {
        if chunk == "" {
            break;
        }
        // println!("{chunk}");
        let lines: Vec<&str> = chunk.split("\n").collect();

        // starting items
        let starting_items_str: Vec<&str> = lines[1][18..].split(", ").collect();
        let mut starting_items: Vec<u128> = vec![];
        for starting_item_str in starting_items_str {
            starting_items.push(starting_item_str.trim().parse::<u128>().unwrap());
        }

        // operation
        let operation_parts: Vec<&str> = lines[2][23..].split(" ").collect();
        let mut operation_type = if operation_parts[0] == "*" {
            OperationType::MULTIPLICATION
        } else {
            OperationType::ADDITION
        };

        let operation_value = if operation_parts[1].trim() == "old" {
            operation_type = OperationType::MULTIPLY_BY_SELF;
            0
        } else {
            operation_parts[1].parse::<u128>().unwrap()
        };

        // test
        let test_value = lines[3][21..].parse::<u128>().unwrap();
        let test_je = lines[4][29..].parse::<usize>().unwrap();
        let test_jne = lines[5][30..].parse::<usize>().unwrap();

        let monkey = Monkey {
            items: starting_items,
            operation_type: operation_type,
            operation_value: operation_value,
            test_value: test_value,
            test_je: test_je,
            test_jne: test_jne,
            total_inspections: 0,
        };
        println!("{:?}", monkey);
        monkeys.push(monkey);
    }

    let part2Mod = monkeys
        .iter()
        .map(|monkey| monkey.test_value)
        .reduce(|accum, item| if accum > 0 { accum * item } else { item }).unwrap();

    // run rounds
    for round in 0..10000 {
        for index in 0..monkeys.len() {
            let monkey_item_group = monkeys[index].round(part2Mod);

            for (monkey, item) in monkey_item_group {
                monkeys[monkey].items.push(item);
            }
        }
    }

    // part 1
    for index in 0..monkeys.len() {
        println!(
            "Monkey {index} inspected items {} times",
            monkeys[index].total_inspections
        );
    }

    monkeys.sort_by_key(|monkey| monkey.total_inspections);
    let monkey_business =
        monkeys[monkeys.len() - 1].total_inspections * monkeys[monkeys.len() - 2].total_inspections;
    println!("Part 1: Monkey business: {monkey_business}");
}

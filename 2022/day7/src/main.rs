use std::fs;
use std::io;
use std::io::BufRead;

#[derive(PartialEq)]
enum FileType {
    DIRECTORY,
    FILE,
}

#[derive(PartialEq)]
struct FileInfo {
    name: String,
    size: u64,
    file_type: FileType,
}

#[derive(Debug, Default)]
struct ArenaTree<T>
where
    T: PartialEq,
{
    arena: Vec<TreeNode<T>>,
}

#[derive(Debug)]
struct TreeNode<T>
where
    T: PartialEq,
{
    idx: usize,
    val: T,
    parent: Option<usize>,
    children: Vec<usize>,
}

impl<T> TreeNode<T>
where
    T: PartialEq,
{
    fn new(idx: usize, val: T) -> Self {
        Self {
            idx,
            val,
            parent: None,
            children: vec![],
        }
    }

    // fn add_child(&mut self, child: &mut TreeNode<T>) {
    //     &self.children.push(child.idx);
    //     child.parent = Some(self.idx);
    // }
}

impl<T> ArenaTree<T>
where
    T: PartialEq,
{
    fn node(&mut self, val: T) -> usize {
        // first see if it exists
        // for node in &self.arena {
        //     if node.val == val {
        //         println!("Duplicate val");
        //         return node.idx;
        //     }
        // }
        // Otherwise, add new node
        let idx = self.arena.len();
        self.arena.push(TreeNode::new(idx, val));
        idx
    }

    // fn recurse(&self, file: usize) {
    //     for child in &self.arena[file].children {
    //        self.recurse(*child);
    //     }
    // }
}

// fn set_file_size_sum(directory: usize, arena_tree: &ArenaTree<FileInfo>) {
//     let current_node = &arena_tree.arena[directory];
//     for child in &current_node.children {
//         if arena_tree.arena[*child].val.file_type == FileType::DIRECTORY {
//             set_file_size_sum(*child, arena_tree);
//         }
//         arena_tree.arena[directory].val.size += arena_tree.arena[*child].val.size;
//     }
// }

fn print_file_structure(file: usize, arena_tree: &ArenaTree<FileInfo>, depth: u32) {
    println!(
        "{:\t<1$}- {2}: {3}",
        "", depth as usize, arena_tree.arena[file].val.name, arena_tree.arena[file].val.size
    );
    for child in &arena_tree.arena[file].children {
        print_file_structure(*child, arena_tree, depth + 1);
    }
}

fn main() {
    let file = fs::File::open("input.txt").expect("File not found");
    let reader = io::BufReader::new(file);

    let mut tree: ArenaTree<FileInfo> = ArenaTree::<FileInfo> { arena: vec![] };
    let root_dir = tree.node(FileInfo {
        name: String::from("/"),
        size: 0,
        file_type: FileType::DIRECTORY,
    });
    let mut current_dir = root_dir;

    for wrapped_line in reader.lines() {
        let line = wrapped_line.unwrap();
        if line.as_bytes()[0] == '$' as u8 {
            if line.starts_with("$ cd") {
                let directory = line[5..].trim();
                println!("Changing to directory: {directory}");
                if directory == "/" {
                    println!("Ignoring root directory");
                } else if directory == ".." {
                    println!("Moving to parent directory");
                    current_dir = tree.arena[current_dir].parent.unwrap();
                } else {
                    println!("Creating a new directory");
                    let node_idx = tree.node(FileInfo {
                        name: directory.to_owned(),
                        size: 0,
                        file_type: FileType::DIRECTORY,
                    });

                    tree.arena[current_dir].children.push(node_idx);
                    tree.arena[node_idx].parent = Some(current_dir);

                    current_dir = node_idx;
                }
            }
        } else {
            if line.starts_with("dir") {
                // ignore
                let directory = &line[4..];
                println!("Directory: {}", { directory })
            } else {
                let line_parts: Vec<&str> = line.split(' ').collect();
                let file_name = line_parts[1];
                let file_size = line_parts[0]
                    .parse::<u64>()
                    .expect("Failed to parse file size");
                println!("{file_name} : {file_size} b");

                let file_node_idx = tree.node(FileInfo {
                    name: file_name.to_owned(),
                    size: file_size,
                    file_type: FileType::FILE,
                });

                tree.arena[current_dir].children.push(file_node_idx);
                tree.arena[file_node_idx].parent = Some(current_dir);

                // when creating a new file, we want to update the sizes of all the directories above the file
                let mut file = tree.arena[file_node_idx].parent;
                
                while file.is_some() {
                    tree.arena[file.unwrap()].val.size += file_size;
                    file = tree.arena[file.unwrap()].parent;
                }
            }
        }
    }

    let file_count = tree.arena.len();
    println!("{file_count}");

    // debug
    // print_file_structure(root_dir, &mut tree, 0);

    // part 1
    // find all the directories that are less than 100000 in size
    let total_size: u64 = tree
        .arena
        .iter()
        .filter(|x| x.val.file_type == FileType::DIRECTORY && x.val.size <= 100000)
        .map(|x| x.val.size)
        .sum();

    println!("Part 1: Total Size of dirs <= 100000: {total_size}");

    // part 2
    let total_disk_size = 70000000;
    let update_size = 30000000;

    let unused_space = total_disk_size - tree.arena[root_dir].val.size;
    let necessary_to_delete = update_size - unused_space;

    let mut smallest_previous_size = total_disk_size;
    let mut solution_idx = 0;

    for idx in 0..tree.arena.len() {
        if tree.arena[idx].val.file_type == FileType::DIRECTORY 
            && tree.arena[idx].val.size >= necessary_to_delete 
            && tree.arena[idx].val.size < smallest_previous_size {
            solution_idx = idx;
            smallest_previous_size = tree.arena[idx].val.size;
        }
    }

    println!("Part 2: Smallest file that will leave space for update: {} Total size: {}", tree.arena[solution_idx].val.name, tree.arena[solution_idx].val.size);
}

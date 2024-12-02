package day14;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;

// 90982
// hash collision on Arrays.deepHashCode
// oof
public class Day14 {

	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day14/data.txt");
		
		// part 1
		{
			char[][] grid = new char[lines.size()][];
			for (int i = 0; i < lines.size(); i++) {
				grid[i] = lines.get(i).toCharArray();
			}
			
			System.out.println("Rocks: " + countRocks(grid));
		
			rollNorth(grid);
		
			showGrid(grid);
		
			System.out.println("Part 1: " + totalWeight(grid));
			System.out.println();
		}
		
		// part 2
		{
			char[][] grid = new char[lines.size()][];
			for (int i = 0; i < lines.size(); i++) {
				grid[i] = lines.get(i).toCharArray();
			}
			
			Map<Integer, Integer> cache = new HashMap<Integer, Integer>();
			boolean found = false;
			for (int i = 0; i < 1000000000; i++) {
				// 1000000000 cycles
				rollNorth(grid);
				//showGrid(grid);
				//System.out.println();
				rollWest(grid);
				//showGrid(grid);
				//System.out.println();
				rollSouth(grid);
				//showGrid(grid);
				//System.out.println();
				rollEast(grid);
				
				System.out.println("After " + (i + 1) + " cycles:");
				//showGrid(grid);
				System.out.println(totalWeight(grid));
				// check if we've already seen this config, if we have, we are good to go.
				if (cache.containsKey(Arrays.deepHashCode(grid)) && !found) {
					int pastCycle = cache.get(Arrays.deepHashCode(grid));
					System.out.println("Cycle: " + i + " matches with cycle: " + pastCycle);
					int difference = pastCycle - i;
					int leftOver = 1000000000 % difference;
					System.out.println(leftOver + " cycles left over.");
					i = 1000000000 - (leftOver - pastCycle);
					found = true;
				}
				if (!found)
					cache.put(Arrays.deepHashCode(grid), i);
			}
			System.out.println("Part 2: " + totalWeight(grid));
		}

	}
	
	public int hash(char[][] grid) {
		/**
		 * Had to write a custom and janky hash function because Arrays.deepHashCode was colliding
		 */
		int hash = 0;
		int hash_part = 0;
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[0].length; x++) {
				if (grid[y][x] == '#') {
					if (hash_part > 0) {
						// add one to total length of this strip
					} else {
						//setup hash part
						
					}
				} else {
					// add to the hash value.
				}
			}
		}
		
		return hash;
	}
	
	public static String[] convertStrArr(char[][] buff) {
		String[] result = new String[buff.length];
		
		for (int i = 0; i < buff.length; i++) {
			result[i] = new String(buff[i]);
		}
		
		return result;
	}
	
	public static void rollNorth(char[][] grid) {
		int[] nextAvailNorth = new int[grid[0].length];
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (grid[y][x] == 'O') {
					// roll up
					int nextPos = nextAvailNorth[x];
					grid[y][x] = '.';
					grid[nextPos][x] = 'O';
					nextAvailNorth[x] = nextPos + 1;
				} else if (grid[y][x] == '#') {
					nextAvailNorth[x] = y + 1;
				}
			}
		}
	}
	
	public static void rollWest(char[][] grid) {
		int[] nextAvailWest = new int[grid.length];
		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				if (grid[y][x] == 'O') {
					// roll left
					int nextX = nextAvailWest[y];
					grid[y][x] = '.';
					grid[y][nextX] = 'O';
					nextAvailWest[y] = nextX + 1;
				} else if (grid[y][x] == '#') {
					nextAvailWest[y] = x + 1;
				}
			}
		}
	}
	
	public static void rollSouth(char[][] grid) {
		int[] nextAvailSouth = new int[grid[0].length];
		for (int i = 0; i < nextAvailSouth.length; i++) {
			nextAvailSouth[i] = grid.length - 1;
		}
		for (int y = grid.length - 1; y >= 0; y--) {
			for (int x = 0; x < grid[y].length; x++) {
				if (grid[y][x] == 'O') {
					// roll down
					int nextY = nextAvailSouth[x];
					grid[y][x] = '.';
					grid[nextY][x] = 'O';
					nextAvailSouth[x] = nextY - 1;
				} else if (grid[y][x] == '#') {
					nextAvailSouth[x] = y - 1;
				}
			}
		}
	}
	
	public static void rollEast(char[][] grid) {
		int[] nextAvailEast = new int[grid.length];
		for (int i = 0; i < nextAvailEast.length; i++) {
			nextAvailEast[i] = grid[0].length - 1;
		}
		for (int y = 0; y < grid.length; y++) {
			for (int x = grid[y].length - 1; x >= 0; x--) {
				if (grid[y][x] == 'O') {
					// roll right
					int nextX = nextAvailEast[y];
					grid[y][x] = '.';
					grid[y][nextX] = 'O';
					nextAvailEast[y] = nextX - 1;
				} else if (grid[y][x] == '#') {
					nextAvailEast[y] = x - 1;
				}
			} 
		}
	}
	
	public static int countRocks(char[][] grid) {
		int rocks = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 'O') {
					rocks++;
				}
			}
		}
		
		return rocks;
	}
	
	public static int totalWeight(char[][] grid) {
		int total = 0;
		for (int i = 0; i < grid.length; i++) {
			char[] line = grid[i];
			for (char c : line) {
				if (c == 'O') {
					total += grid.length - i;
				}
			}
		}
		
		return total;
	}
	
	public static void showGrid(char[][] grid) {
		for (char[] line : grid) {
			System.out.println(line);
		}
	}

}

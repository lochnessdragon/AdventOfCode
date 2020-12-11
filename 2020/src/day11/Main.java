package day11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource input = new Resource("src/day11/input.txt");

		List<String> lines = input.read();

		// part 1
		int[][] seatArray = new int[lines.size()][lines.get(0).length()];

		for (int y = 0; y < lines.size(); y++) {
			// Map<Integer, Integer> batch = new HashMap<Integer, Integer>();

			char[] chars = lines.get(y).toCharArray();

			for (int x = 0; x < chars.length; x++) {
				seatArray[y][x] = convertToInt(chars[x]);
			}
		}

//		printSeating(seatArray);

		boolean changing = true;
		int loops = 1;

		while (changing) {
			int[][] tempArray = new int[seatArray.length][seatArray[0].length];
			for (int y = 0; y < seatArray.length; y++) {
				tempArray[y] = seatArray[y].clone();
			}

			int ruleCount = 0;
			for (int y = 0; y < seatArray.length; y++) {
				for (int x = 0; x < seatArray[y].length; x++) {
					int countNext = getOccupiedSeats(x, y, seatArray);
					if (countNext == 0 && seatArray[y][x] == 1) {
						tempArray[y][x] = 2;
						ruleCount++;
					} else if (countNext >= 4 && seatArray[y][x] == 2) {
						tempArray[y][x] = 1;
						ruleCount++;
					}
					// printSeating(seatArray);
				}
			}

			if (ruleCount == 0)
				changing = false;

			seatArray = tempArray;
//			System.out.println("\nAfter " + loops + " loops:");
//			printSeating(seatArray);
			loops++;
		}

		System.out.println("[Part 1]: The number of occupied seats is: " + getNumberOfOccupiedSeats(seatArray));

		// part 2 (reset list and start again)
		// part 1
		seatArray = new int[lines.size()][lines.get(0).length()];

		for (int y = 0; y < lines.size(); y++) {
			// Map<Integer, Integer> batch = new HashMap<Integer, Integer>();

			char[] chars = lines.get(y).toCharArray();

			for (int x = 0; x < chars.length; x++) {
				seatArray[y][x] = convertToInt(chars[x]);
			}
		}

//		printSeating(seatArray);

		changing = true;
		loops = 1;

		while (changing) {
			int[][] tempArray = new int[seatArray.length][seatArray[0].length];
			for (int y = 0; y < seatArray.length; y++) {
				tempArray[y] = seatArray[y].clone();
			}

			int ruleCount = 0;
			for (int y = 0; y < seatArray.length; y++) {
				for (int x = 0; x < seatArray[y].length; x++) {
					int countNext = getOccupiedSeatsVectorized(x, y, seatArray);
					if (countNext == 0 && seatArray[y][x] == 1) {
						tempArray[y][x] = 2;
						ruleCount++;
					} else if (countNext >= 5 && seatArray[y][x] == 2) {
						tempArray[y][x] = 1;
						ruleCount++;
					}
					// printSeating(seatArray);
				}
			}

			if (ruleCount == 0)
				changing = false;

			seatArray = tempArray;
//			System.out.println("\nAfter " + loops + " loops:");
//			printSeating(seatArray);
			loops++;
		}

		System.out.println("[Part 2]: The number of occupied seats is: " + getNumberOfOccupiedSeats(seatArray));

	}

	private static int getOccupiedSeatsVectorized(int x, int y, int[][] seatArray) {
		int number = 0;
		
		int[][] vectors = {{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};
		
		for(int[] vector : vectors) {
			int x1 = x;
			int y1 = y;
			
			boolean looping = true;
			while(looping) {
				x1 += vector[0];
				y1 += vector[1];
				
				if(x1 < 0 || y1 < 0 || y1 >= seatArray.length || x1 >= seatArray[y].length)
					break;
				
				int value = seatArray[y1][x1];
				switch(value) {
				case 1:
					looping = false;
					break;
				case 2:
					looping = false;
					number++;
					break;
				default:
					break;
				}
			}
		}
		
		return number;
	}

	private static int getNumberOfOccupiedSeats(int[][] seatArray) {
		int value = 0;

		for (int y = 0; y < seatArray.length; y++) {
			for (int x = 0; x < seatArray[y].length; x++) {
				if (seatArray[y][x] == 2) {
					value++;
				}
			}
		}

		return value;
	}

	private static int getOccupiedSeats(int x, int y, int[][] seatArray) {
		int count = 0;

		for (int y1 = (y - 1); y1 < (y + 2); y1++) {
			for (int x1 = (x - 1); x1 < (x + 2); x1++) {
				if ((y1 == y && x1 == x) || (y1 < 0 || x1 < 0 || y1 >= seatArray.length || x1 >= seatArray[y].length))
					continue;
				if (seatArray[y1][x1] == 2) {
					count++;
				}
			}
		}

		return count;
	}

	private static void printSeating(int[][] cols) {
		for (int y = 0; y < cols.length; y++) {
			for (int x = 0; x < cols[y].length; x++) {
				System.out.print(convertToChar(cols[y][x]));
			}
			System.out.println();
		}
	}

	private static int convertToInt(char c) {
		int value = 0;
		switch (c) {
		case 'L':
			value = 1;
			break;
		case '#':
			value = 2;
			break;
		default:
			break;
		}

		return value;
	}

	private static char convertToChar(int i) {
		char value = '.';
		switch (i) {
		case 1:
			value = 'L';
			break;
		case 2:
			value = '#';
			break;
		default:
			break;
		}

		return value;
	}

}

package day1;

import java.util.List;

import utils.Utils;

// wrong anser part 2:
// 54871
public class Day1 {
	
	private static String[] NUM_NAMES = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
	
	private static String fixPart2Str(String line) {
		String result = "";
		
		String tokBuff = "";
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			char potentialNum = (char) (c - '0');
			if (potentialNum >= 0 && potentialNum <= 9) {
				// that's a number, append to the result buffer
				result += c;
			}
			
			tokBuff += c;
			
			for (int j = 0; j < NUM_NAMES.length; j++) {
				if(tokBuff.endsWith(NUM_NAMES[j])) {
					result += Integer.toString(j);
					break;
				}
			}
		}
		
		return result;
	}

	public static void main(String[] args) {
		System.out.println("AOC Day 1");
		
		List<String> lines = Utils.readFile("src/day1/data.txt");
		
//		int runningTotal1 = 0;
		int runningTotal2 = 0;
		for (String line : lines) {
			// part 1
//			String numberOnlyPart1 = line.replaceAll("[^0-9]", "");
//			int value1 = Integer.parseInt(numberOnlyPart1.substring(0, 1) + numberOnlyPart1.substring(numberOnlyPart1.length() - 1, numberOnlyPart1.length()));
//			System.out.println("Value (Part 1): " + value1);
//			runningTotal1 += value1;
			
			// part 2
			String numberOnlyPart2 = fixPart2Str(line);
			int part2Len = numberOnlyPart2.length();
			int value2 = Integer.parseInt(numberOnlyPart2.substring(0,1) + numberOnlyPart2.substring(part2Len - 1, part2Len));
			System.out.println("Number's Only: " + numberOnlyPart2 + " Value (Part 2): " + value2);
			runningTotal2 += value2;
		}
		
//		System.out.println("Day 1: Part 1: Total: " + runningTotal1);
		System.out.println("Day 1: Part 2: Total: " + runningTotal2);
	}

}

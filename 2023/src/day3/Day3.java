package day3;

import java.util.ArrayList;
import java.util.List;

import utils.Utils;

// incorrect answers:
// 605118
// 534001
public class Day3 {

	public static boolean checkValue(String buffer, List<String> lines, int currX, int currY) {
		boolean isValid = false;
		if (buffer.length() > 0) {
			// check the digit and clear the buffer
			int startY = currY - 1;
			int endY = currY + 1;
			int startX = currX - (buffer.length() + 1);
			int endX = currX;

			for (int checkY = startY; checkY <= endY; checkY++) {
				// skip if out of bounds
				if (checkY < 0 || checkY >= lines.size())
					continue;
				for (int checkX = startX; checkX <= endX; checkX++) {
					// skip if out of bounds
					if (checkX < 0 || checkX >= lines.get(checkY).length())
						continue;
					char checkChar = lines.get(checkY).charAt(checkX);
					if (checkChar != '.' && !Character.isDigit(checkChar)) {
						isValid = true;
					}
				}
			}
		}
		
		return isValid;
	}
	
	public static int checkGear(List<String> lines, int gearX, int gearY) {
		// search for numbers
		List<Integer> surroundingNums = new ArrayList<Integer>();
		int result = 0;
		
		for (int checkY = gearY - 1; checkY <= gearY + 1; checkY++) {
			if(checkY < 0 || checkY == lines.size()) 
				continue; // avoid out of bounds
			
			String digitLine = lines.get(checkY);
			boolean middleHadNum = false; // variable to store whether a digit is in the same column of the asterisk (could be bridging problem)
			for (int checkX = gearX - 1; checkX <= gearX + 1; checkX++) {
				if (middleHadNum)
					break;
				
				if(checkX < 0 || checkX == digitLine.length())
					continue;
				
				if(Character.isDigit(digitLine.charAt(checkX))) {
					// we have a number
					// keep going backwards to the start of the number
					int startIdx = checkX;
					while(startIdx >= 0 && Character.isDigit(digitLine.charAt(startIdx))) {
						startIdx--;
					}
					startIdx++; // undo previous (incorrect) decrement
					
					String buf = "";
					while(startIdx < digitLine.length() && Character.isDigit(digitLine.charAt(startIdx))) {
						buf += digitLine.charAt(startIdx);
						if(startIdx == gearX) 
							middleHadNum = true;
						startIdx++;
					}
					
					surroundingNums.add(Integer.parseInt(buf));
				}
			}
		}
		
		if (surroundingNums.size() == 2) {
			System.out.println("Found gear with 2 nums: " + surroundingNums.get(0) + " and " + surroundingNums.get(1));
			result = (surroundingNums.get(0) * surroundingNums.get(1));
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day3/data.txt");
		
		// part 1
		int part1 = 0;
		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			String buf = "";
			for (int x = 0; x < line.length(); x++) {
				char c = line.charAt(x);
				if (Character.isDigit(c)) {
					// add it to the buffer
					buf += c;
				} else {
					boolean isValid = checkValue(buf, lines, x, y);
					if(isValid) {
						int value = Integer.parseInt(buf);
						System.out.println("Found value: " + value);
						part1 += value;
					}
					buf = "";
				}
			}
			
			// check before next line
			if(checkValue(buf, lines, line.length(), y)) {
				int value = Integer.parseInt(buf);
				System.out.println("Found value: " + value);
				part1 += value;
			}
			buf = "";
		}
		
		// part 2
		int part2 = 0;
		for(int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			for (int x = 0; x < line.length(); x++) {
				if(line.charAt(x) == '*') {
					System.out.println("Asterisk @ (" + x + ", " + y + ")");
					
					part2 += checkGear(lines, x, y);
				}
			}
		}

		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}

}

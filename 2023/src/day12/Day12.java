package day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import utils.Utils;

public class Day12 {

	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day12/test.txt");
		
		int total_arrangements = 0;
		
		for (String line : lines) {
			String[] parts = line.split(" ");
			String[] nums_strs = parts[1].split(",");
			
			int[] nums = new int[nums_strs.length];
			for (int i = 0; i < nums_strs.length; i++) {
				nums[i] = Integer.parseInt(nums_strs[i]);
			}
			
			int numBroken = Utils.countCharOccurences(parts[0], '#');
			int arrangements = countAcceptable(parts[0].toCharArray(), nums, numBroken, IntStream.of(nums).sum(), 0);
			
			System.out.println("Line: " + line + " has " + arrangements + " different permutations.");
			
			total_arrangements += arrangements;
		}
		
		System.out.println("Part 1: " + total_arrangements);
		
		int part2_total = 0;
		for (String line : lines) {
			String[] parts = line.split(" ");
			String[] nums_strs = parts[1].split(",");
			
			int[] nums = new int[nums_strs.length * 5];
			for (int i = 0; i < nums_strs.length; i++) {
				for (int j = 0; j < 5; j++) {
					nums[i + (j * nums_strs.length)] = Integer.parseInt(nums_strs[i]);
				}
			}
			
			String fullLine = parts[0] + '?' + parts[0] + '?' + parts[0] + '?' + parts[0] + '?' + parts[0];
			int numBroken = Utils.countCharOccurences(fullLine, '#');
			int arrangements = countAcceptable(fullLine.toCharArray(), nums, numBroken, IntStream.of(nums).sum(), 0);
			
			System.out.println("Line: " + line + " has " + arrangements + " different permutations.");
			
			part2_total += arrangements;
		}
		
		System.out.println("Part 2: " + part2_total);
	}

	
	public static int countAcceptable(char[] state, int[] required, int numBroken, int maxBroken, int i) {
		int acceptable = 0;
		//System.out.println(state);
		if (i < state.length) {
			if (state[i] == '?') {
				// this one is modifiable
				if (numBroken < maxBroken) {
					acceptable += countAcceptable(Utils.copyAndReplaceChar(state, i, '#'), required, numBroken + 1, maxBroken, i + 1);
				}
				
				acceptable += countAcceptable(Utils.copyAndReplaceChar(state, i, '.'), required, numBroken, maxBroken, i + 1);
			} else {
				// skip until it's modifiable
				int j = i;
				while (j < state.length && state[j] != '?') {
					j++;
				}
				
				if (j == state.length) {
					return countAcceptable(state, required, numBroken, maxBroken, j);
				} else {
					// this one is modifiable
					if (numBroken < maxBroken) {
						acceptable += countAcceptable(Utils.copyAndReplaceChar(state, j, '#'), required, numBroken + 1, maxBroken, j + 1);
					}
					
					acceptable += countAcceptable(Utils.copyAndReplaceChar(state, j, '.'), required, numBroken, maxBroken, j + 1);
				}
			}
		} else {
			if (checkAcceptable(state, required)) {
				acceptable = 1;
			}
		}
		
		return acceptable;
	}
	
	public static boolean checkAcceptable(char[] state, int[] required) {
		
		List<Integer> found = new ArrayList<Integer>();
		int currRun = 0;
		for (char c : state) {
			if (c == '#') {
				currRun++;
			} else {
				if (currRun > 0)
					found.add(currRun);
				currRun = 0;
			}
		}
		
		if (currRun > 0)
			found.add(currRun);
		
		//System.out.println("Checking: " + found + " against " + Arrays.toString(required));
		
		if (found.size() != required.length) {
			return false;
		}
		
		for (int i = 0; i < found.size(); i++) {
			if (found.get(i) != required[i])
				return false;
		}
		
		return true;
	}
}

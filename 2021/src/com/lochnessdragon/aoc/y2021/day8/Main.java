package com.lochnessdragon.aoc.y2021.day8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lochnessdragon.aoc.y2021.utils.StringTranslator;
import com.lochnessdragon.aoc.y2021.utils.Utils;

public class Main {
  public static void main(String[] args) {
    String data = Utils.readFile("../src/com/lochnessdragon/aoc/y2021/day8/test.txt");

    String[] lines = data.split("\n");

		List<String[]> sections = new ArrayList<String[]>();

		for(String line : lines) {
			String[] section = line.split("\\|");
			sections.add(section);
		}

		{
			// part 1
			int digitCount = 0;
			for(String[] section : sections) {
				String[] digits = section[1].split(" ");
				for(String digit : digits) {
					digit = digit.trim();
					int digitLen = digit.length();
					if(digitLen == 2 || digitLen == 4 || digitLen == 7 || digitLen == 3) {
						digitCount++;
					}
				}
			}

			System.out.println("[ Part 1 ]: Digit Count: " + digitCount);
		}

		{
			// part 2
			// decode the digits 
			long sum = 0;

			List<String> permutations = Utils.getAllPermutationsOfStr("abcdefg");
			
			for(String[] section : sections) {
				// decode the sections 

				// for every permutation of abcdefg mapping to abcdefg, check if all the values are valid.
				for(String permutation : permutations) {
					StringTranslator translator = new StringTranslator(permutation, "abcdefg");
					
					
				}

				// convert digits to numbers
				String fullNumberStr = "";
				for(String digit : section) {
					digit = digit.trim();
					int number = convertSegmentDisplay(digit);
					System.out.println(digit + " converted to: " + number);
					fullNumberStr += number;
				}

				int fullNumber = Integer.parseInt(fullNumberStr);

				// add numbers to the sum
				sum += fullNumber;
			}

			System.out.println("[ Part 2 ]: Sum: " + sum);
		}

  }

	public static int convertSegmentDisplay(String segments) {
		char[] arr = segments.toCharArray();
		Arrays.sort(arr);
		
		String parse = new String(arr);

		int result = -1;

		switch (parse) {
		case "abcefg":
			result = 0;
			break;
		case "cf":
			result = 1;
			break;
		case "acdeg":
			result = 2;
			break;
		case "acdfg":
			result = 3;
			break;
		case "bcdf":
			result = 4;
			break;
		case "abdfg":
			result = 5;
			break;
		case "abdefg":
			result = 6;
			break;
		case "acf":
			result = 7;
			break;
		case "abcdefg":
			result = 8;
			break;
		case "abcdfg":
			result = 9;
			break;
		default:
			break;
		}
		if( result == -1)
			throw new NullPointerException();
		
		return result;
	}
}
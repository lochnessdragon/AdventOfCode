package com.lochnessdragon.aoc.y2021.day3;

import java.util.ArrayList;
import java.util.List;

import com.lochnessdragon.aoc.y2021.utils.Utils;

public class Main {

	public static void main(String[] args) {
		String data = Utils.readFile("src/com/lochnessdragon/aoc/y2021/day3/input.txt");
		String[] lines = data.split("\n");
		
		// part 1
		{
			List<Integer[]> colCount = new ArrayList<Integer[]>(); // list of tuples with the first element being the number of zeros and second element being the number of ones
			
			for (String line : lines) {
				char[] chars = line.toCharArray();
				for(int x = 0; x < chars.length; x++) {
					if(colCount.size() < (x + 1)) {
						Integer array[] = {0, 0};
						colCount.add(array);
					}
					
					if(chars[x] == '0') {
						colCount.get(x)[0] += 1;
					} else if (chars[x] == '1') {
						colCount.get(x)[1] += 1;
					}
				}
			}
			
			String gammaRateStr = "";
			String epsilonRateStr = "";
			for(Integer[] counts : colCount) {
				if(counts[0] > counts[1]) {
					gammaRateStr += "0";
					epsilonRateStr += "1";
				} else if (counts[1] > counts[0]) {
					gammaRateStr += "1";
					epsilonRateStr += "0";
				} else {
					System.out.println("Fatal error");
				}
			}
			
			int gammaRate = Integer.parseInt(gammaRateStr, 2);
			int epsilonRate = Integer.parseInt(epsilonRateStr, 2);
			
			System.out.println("[ Part 1 ]: Gamma Rate: " + gammaRateStr + " Epsilon Rate: " + epsilonRateStr + " Multiplied: " + epsilonRate * gammaRate);
		}
		
		// Part 2
		{
			int oGenRating = 0;
			int co2GenRating = 0;
			
			List<String> oGenNumbers = new ArrayList<String>();
			List<String> co2GenNumbers = new ArrayList<String>();
			
			for(String line : lines) {
				oGenNumbers.add(line);
				co2GenNumbers.add(line);
			}
			
			// cycle through both lists to find the correct values
			int index = 0;
			while(oGenNumbers.size() > 1) {
				System.out.println("---Str Index: " + index + "---");
				char keepOGenVal = getMostCommonNumber(index, oGenNumbers);
				
				final int strIndex = index;
				
				oGenNumbers.removeIf((candidate) -> {
					if(candidate.toCharArray()[strIndex] != keepOGenVal) {
						System.out.println("Discarding OGen Number: " + candidate);
						return true;
					}
					return false;
				});
				
				index ++;
			}
			
			index = 0;
			while(co2GenNumbers.size() > 1) {
				char keepCO2Val = getLeastCommonNumber(index, co2GenNumbers);
				
				final int strIndex = index;
				
				co2GenNumbers.removeIf((candidate) -> {
					if(candidate.toCharArray()[strIndex] != keepCO2Val) {
						System.out.println("Discarding CO2 Number: " + candidate);
						return true;
					}
					return false;
				});
				
				index ++;
			}
			
			oGenRating = Integer.parseInt(oGenNumbers.get(0), 2);
			co2GenRating = Integer.parseInt(co2GenNumbers.get(0), 2);
			
			System.out.println("[ Part 2 ]: Oxygen Generator Rating: " + oGenRating + " CO2 Scrubber Rating: " + co2GenRating + " Multipled: " +  oGenRating * co2GenRating);
		}
	}

	private static char getMostCommonNumber(int index, List<String> numbersList) {
		int zeroCount = 0;
		int oneCount = 0;
		
		for(String number : numbersList) {
			if(number.toCharArray()[index] == '0') {
				zeroCount++;
			} else if(number.toCharArray()[index] == '1') {
				oneCount++;
			}
		}
		return oneCount >= zeroCount ? '1' : '0';
	}
	
	private static char getLeastCommonNumber(int index, List<String> numbersList) {
		int zeroCount = 0;
		int oneCount = 0;
		
		for(String number : numbersList) {
			if(number.toCharArray()[index] == '0') {
				zeroCount++;
			} else if(number.toCharArray()[index] == '1') {
				oneCount++;
			}
		}
		return oneCount >= zeroCount ? '0' : '1';
	}

}

package day10;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource input = new Resource("src/day10/input.txt");
		List<Integer> sortedVoltConverters = input.read().stream().mapToInt(Integer::parseInt).sorted().boxed().collect(Collectors.toList());
		
		// part 1
		int startingVoltage = 0;
		int diff1 = 0;
		int diff3 = 0;
		
		for(Integer amplifier : sortedVoltConverters) {
			System.out.println(amplifier);
			int difference = amplifier - startingVoltage;
			
			if(difference == 1) {
				diff1++;
			}
			
			if(difference == 3) {
				diff3++;
			}
			
			startingVoltage = amplifier;
		}
		
		diff3++;
		
		System.out.println("[Part 1]: Differences of 1: " + diff1 + " Differences of 3: " + diff3 + " Multiplied: " + (diff1 * diff3));
		
		// part 2
		int max = Collections.max(sortedVoltConverters);
		System.out.println("[Part 2]: Max: " + max);
		int numberOfPermutations = recurseThroughList(-1, max, sortedVoltConverters); 
		
		System.out.println("[Part 2]: Number of Permutations: " + numberOfPermutations);
	}

	private static int recurseThroughList(int index, int max, List<Integer> sortedVoltConverters) {
		int converter = 0;
		int total = 0;
		
		if(index > -1) {
			converter = sortedVoltConverters.get(index);
		}
		
		if(converter == max) {
			return 1;
		}
		
		// get the numbers around the converter
		for(int i = 1; i < 4; i++) {
			if((converter + i) > max) {
				//total -= 1;
				continue;
			}
			if(sortedVoltConverters.contains(converter+i)) {
				total += recurseThroughList(sortedVoltConverters.indexOf(converter+i), max, sortedVoltConverters); 
			}
		}
		
//		if(total == 0) {
//			total -= 1;
//		}
				
		return total;
	}

}

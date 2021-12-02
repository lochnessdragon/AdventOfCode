package day15;

import java.util.HashMap;
import java.util.Map;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource input = new Resource("src/day15/test.txt");
		
		String line = input.read().get(0);
		String[] parts = line.split(",");
		int[] beginningNumbers = new int[parts.length];
		for(int i = 0; i < parts.length; i++) {
			beginningNumbers[i] = Integer.parseInt(parts[i]);
		}
		
		// part 1
		
		System.out.println("Part 1 Starting!");
		
		// stores a map of numbers to the last turn it was used.
		Map<Integer, Integer> valueMap = new HashMap<Integer, Integer>();
		
		int latestNumber = 0;
		int lastNumber = 0;
		for(int i = 0; i < 30000000; i++) {
			if(i > 1) {
				// put the new number in the hash map
				valueMap.put(latestNumber, i - 2);
			}
			
			latestNumber = lastNumber;
			
			if(i < beginningNumbers.length) {
				lastNumber = beginningNumbers[i];
			} else {
				// onto new numbers
				if(valueMap.keySet().contains(lastNumber)) {
					// it existed before, so output the number
					int timeSinceLastNumber = (i-1) - valueMap.get(lastNumber);
					
					if(timeSinceLastNumber > 0) {
						// if it was more than one turn ago.
						lastNumber = timeSinceLastNumber;
					} else {
						lastNumber = 0;
					}
				} else {
					// output zero becuase its a unique value
					lastNumber = 0;
				}
			}
			
//			System.out.println((i+1) + ": " + lastNumber); // + " HashMap Keys: " + valueMap.entrySet());
			
		}
		
		System.out.println(lastNumber);
	}
	
}

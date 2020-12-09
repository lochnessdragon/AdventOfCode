package day9;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource file = new Resource("src/day9/input.txt");
		List<Long> data = file.read().stream().mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
		
		// part 1
		int preambleSize = 25;
		
		long badNumber = 0;
		
		for(int i = 0; i < data.size(); i++) {
//			System.out.println(data.get(i));
			
			if(i >= preambleSize) {
				boolean found = false;
				
				// calculate whether or not the number is in the last 5 numbers
				List<Long> preamble = data.subList(i-preambleSize, i);
				for(Long preNumber : preamble) {
					long otherNumber = data.get(i) - preNumber;
					if(otherNumber != preNumber && preamble.contains(otherNumber)) {
						found = true;
						//System.out.println(data.get(i) + " is a sum of: " + preNumber + " and " + otherNumber);
					}
				}
				
				if(!found) {
					System.out.println("[Part 1]: " + data.get(i) + " is not a sum of the previous " + preambleSize + " numbers.");
					badNumber = data.get(i);
				}
			}
		}
		
		// part 2
		
		List<Long> sumNumbers = new ArrayList<Long>();
		
		
		for(int i = 0; i < data.size(); i++) {
			
			long difference = badNumber;
			
			List<Long> sublist = data.subList(i, data.size());
			
			int index = 0;
			while(difference > 0 && index < sublist.size()) {
				if(sublist.get(index) == badNumber) {
					break;
				}
				
				difference -= sublist.get(index);
				sumNumbers.add(sublist.get(index));
				
				index++;
			}
			
			if(difference == 0) {
				System.out.print("[Part 2]: The numbers are: ");
				for(Long value : sumNumbers) {
					System.out.print(value + " ");
				}
				System.out.println();
				
				long min = sumNumbers.stream().mapToLong((value) -> {return value;}).min().getAsLong();
				long max = sumNumbers.stream().mapToLong((value) -> {return value;}).max().getAsLong();
				
				System.out.println("[Part 2]: Min: " + min + " Max: " + max + " Sum: " + (min + max));
			}
			
			sumNumbers.clear();
		}
		
	}

}

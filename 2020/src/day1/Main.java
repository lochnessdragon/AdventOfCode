package day1;

import java.util.ArrayList;
import java.util.List;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource resource = new Resource("src/day1/input.txt");
		
		List<String> lines = resource.read();
		
		List<Integer> numbers = new ArrayList<Integer>();
		
		for(String line : lines) {
			numbers.add(Integer.parseInt(line));
		}
		
		// part 1
		for(Integer number : numbers) {
			if(number > 2020) {
				continue;
			}
			
			int diff = 2020 - number;
			if(numbers.contains(diff)) {
				System.out.println("[Part 1] Number 1: " + number + " Number 2: " + diff + " Multiplied: " + (number*diff));
				break;
			}
		} 
		
		// part 2
		for(int i = 0; i < numbers.size(); i++) {
			int number1 = numbers.get(i);
			
			if(number1 > 2020)
				continue;
			
			for(int j = 0; j < numbers.size(); j++) {
				if(j == i)
					continue;
				
				int number2 = numbers.get(j);
				
				int bigValue = number1 + number2;
				
				if(bigValue > 2020)
					continue;
				
				int number3 = 2020 - bigValue;
				if(numbers.contains(number3)) {
					System.out.println("[Part 2] Number 1: " + number1 + " Number 2: " + number2 + " Number 3: " + number3 + " Multiplied: " + (number1 * number2 * number3));
				}
			}
		}
		
	}

}

package com.lochnessdragon.aoc.y2021.day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lochnessdragon.aoc.y2021.utils.Utils;

public class Main {

	public static void main(String[] args) {
		String data = Utils.readFile("src/com/lochnessdragon/aoc/y2021/day7/input.txt");
		data = data.trim();
		
		String[] numberStrs = data.split(",");
		
		List<Integer> numbers = new ArrayList<Integer>();
		
		for(String numberStr : numberStrs) {
			numbers.add(Integer.parseInt(numberStr));
		}
		
		{
			// part 1
			// find the average of all the fuels (probably the cheapest)
			int min = Collections.min(numbers);
			int max = Collections.max(numbers);
			
			int lowest = Integer.MAX_VALUE;
			int bestPosition = 0;
					
			for(int i = min; i <= max; i++) {
				int potential = calculateFuelToStepPart2(numbers, i);
				if(potential < lowest) {
					lowest = potential;
					bestPosition = i;
				}
			}
			
			System.out.println("[ Part 1 ]: Least Amount of Fuel Required to Align: " + lowest + " at the position: " + bestPosition);
		}

	}

	public static int calculateFuelToStepPart1(List<Integer> numbers, int position) {
		int fuel = 0;
		
		for(int number : numbers) {
			fuel += Math.abs(number - position);
		}
		
		return fuel;
	}
	
	public static int calculateFuelToStepPart2(List<Integer> numbers, int position) {
		int fuel = 0;
		
		for(int number : numbers) {
			int difference = Math.abs(number - position);
			while(difference > 0) {
				fuel += difference;
				difference--;
			}
		}
		
		return fuel;
	}
}

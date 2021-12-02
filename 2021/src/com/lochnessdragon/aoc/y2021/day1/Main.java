package com.lochnessdragon.aoc.y2021.day1;

import java.util.ArrayList;
import java.util.List;

import com.lochnessdragon.aoc.y2021.utils.Utils;

public class Main {
	public static void main(String args[]) {
		String input = Utils.readFile("src/com/lochnessdragon/aoc/y2021/day1/input.txt");
		
		List<Integer> numbers = new ArrayList<Integer>();
		
		for(String numberStr : input.split("\n")) {
			numbers.add(Integer.parseInt(numberStr));
		}
		
		{
			int prev = numbers.get(0);
			int count = 0;
			
			for(int x : numbers) {
				if (x > prev) {
					count++;
				}
				
				prev = x;
			}
			
			System.out.println("Part 1 Count: " + count);
		}
		
		{
			int first = numbers.get(0);
			int second = numbers.get(1);
			int prevTotal = first + second + numbers.get(2);
			
			int count = 0;
			
			for(int x : numbers.subList(2, numbers.size())) {
				int total = first + second + x;
				
				if(prevTotal < total) {
					count++;
				}
				
				first = second;
				second = x;
				prevTotal = total;
			}
			
			System.out.println("Part 2 Count: " + count);
		}
	}
	
}

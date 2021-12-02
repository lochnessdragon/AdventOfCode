package com.lochnessdragon.aoc.y2021.day2;

import com.lochnessdragon.aoc.y2021.utils.Utils;

public class Main {

	public static void main(String[] args) {
		String data = Utils.readFile("src/com/lochnessdragon/aoc/y2021/day2/input.txt");
		
		// part 1
		{
			String[] lines = data.split("\n");
			
			int depth = 0;
			int distance = 0;
			
			for(String line : lines) {
				String[] parts = line.split(" ");
				
				int arg1 = Integer.parseInt(parts[1]);
				
				switch(parts[0]) {
				case "forward":
					distance += arg1;
					break;
				case "up":
					depth -= arg1;
					break;
				case "down":
					depth += arg1;
					break;
				default:
					break;
				}
			}
			
			System.out.println("Depth: " + depth + " Distance: " + distance + " Multiplied: " + (depth * distance));
		}
		
		// part 2
		{
			String[] lines = data.split("\n");
			
			int depth = 0;
			int distance = 0;
			int aim = 0;
			
			for(String line : lines) {
				String[] parts = line.split(" ");
				
				int arg1 = Integer.parseInt(parts[1]);
				
				switch(parts[0]) {
				case "forward":
					distance += arg1;
					depth += arg1 * aim;
					break;
				case "up":
					aim -= arg1;
					break;
				case "down":
					aim += arg1;
					break;
				default:
					break;
				}
			}
			
			System.out.println("Depth: " + depth + " Distance: " + distance + " Multiplied: " + (depth * distance));
		}
	}

}

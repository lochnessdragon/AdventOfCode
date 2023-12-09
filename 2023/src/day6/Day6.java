package day6;

import java.util.ArrayList;
import java.util.List;

import utils.Utils;

public class Day6 {
	// Part 1: 633080
	// Part 2: 20048741
	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day6/data.txt");
		
		// part 1
		List<Integer> times = getIntList(lines.get(0));
		List<Integer> distances = getIntList(lines.get(1));
		
		int part1 = 1;
		
		for (int i = 0; i < times.size(); i++) {
			int time = times.get(i);
			int distance = distances.get(i);
			int wins = 0;
			for (int t_charging = 0; t_charging < time; t_charging++) {
				int t_moving = time - t_charging;
				int distance_travelled = t_moving * t_charging;
				if (distance_travelled > distance)
					wins++;
			}
			part1 *= wins;
		}
		
		System.out.println("Part 1: " + part1);
		
		// part 2
		int part2 = 0;
		{
		long time = getInt(lines.get(0));
		long distance = getInt(lines.get(1));
		for (long t_charging = 0; t_charging < time; t_charging++) {
			long t_moving = time - t_charging;
			long distance_travelled = t_moving * t_charging;
			if (distance_travelled > distance)
				part2++;
		}
		}
		
		System.out.println("Part 2: " + part2);
		
		part1 = 1;
		
		for (int i = 0; i < times.size(); i++) {
			int time = times.get(i);
			int distance = distances.get(i);
			long[] zeros = quadEq(-1, time, -distance);
			if (zeros[0] > zeros[1])
				part1 *= (zeros[0] - zeros[1]);
			else {
				part1 *= (zeros[1] - zeros[0]);
			}
		}
		
		System.out.println("Part 1: " + part1);
		
		// part 2
		long time = getInt(lines.get(0));
		long distance = getInt(lines.get(1));
		part2 = 0;
		long[] zeros = quadEq(-1, time, -distance);
		if (zeros[0] > zeros[1])
			part2 = (int) (zeros[0] - zeros[1]);
		else
			part2 = (int) (zeros[1] - zeros[0]);
		
		System.out.println("Part 2: " + part2);
	}
	
	static long[] quadEq(long a, long b, long c) {
		double intermediateValue = Math.sqrt(Math.pow(b, 2) - (4 * a * c));
		
		double result1 = (-b + intermediateValue) / (2 * a);
		double result2 = (-b - intermediateValue) / (2 * a);
		
		System.out.println("Quadratic Formula: (" + result1 + ", " + result2 + ")");
		
		long[] result = new long[2];
		result[0] = (long) Math.ceil(result1);
		result[1] = (long) Math.floor(result2) + ((result2 % 1) > 0 ? 1 : -1);
		
		return result;
	}
	
	static long getInt(String line) {
		String input = line.split(":")[1];
		String buf = "";
		for (char c : input.toCharArray()) {
			if (c != ' ') {
				buf += c;
			}
		}
		
		return Long.parseLong(buf);
	}
	
	static List<Integer> getIntList(String line) {
		String input = line.split(":")[1];
		List<Integer> result = new ArrayList<Integer>();
		
		String buf = "";
		for (char c : input.toCharArray()) {
			if (c == ' ') {
				// flush buf
				
				if (!buf.equals("")) {
					result.add(Integer.parseInt(buf));
				}
				
				buf = "";
			} else {
				buf += c;
			}
		}
		
		if (!buf.equals("")) {
			result.add(Integer.parseInt(buf));
		}
		
		return result;
	}
}

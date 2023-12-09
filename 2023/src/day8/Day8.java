package day8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;

public class Day8 {

	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day8/data.txt");
		
		String directions = lines.get(0);
		
		// part 2 starting directions
		List<String> startingIds = new ArrayList<String>();
		
		Map<String, Choice> nodes = new HashMap<String, Choice>();
		
		for (int i = 2; i < lines.size(); i++) {
			String line = lines.get(i);
			String[] parts = line.split("=");
			String nodeId = parts[0].trim();
			
			if (nodeId.endsWith("A")) {
				startingIds.add(nodeId);
			}
			
			int nextNodeStart = parts[1].indexOf("(");
			int nextNodeEnd = parts[1].indexOf(")");
			
			String nextNodeIds = parts[1].substring(nextNodeStart + 1, nextNodeEnd);
			String[] nextNodes = nextNodeIds.split(",");
			
			nodes.put(nodeId, new Choice(nextNodes[0].trim(), nextNodes[1].trim()));
		}
		
		System.out.println("Starting keys: " + startingIds);
		System.out.println("No. Nodes: " + nodes.size());
		System.out.println("Direction length: " + directions.length());
		
		//int part1 = stepsToCompletePart1("AAA", nodes, directions);
		
		// part 2
		long part2 = 1; // steps till all are on ending z
		List<Integer> steps = new ArrayList<Integer>();
		for (String startingId : startingIds) {
			steps.add(stepsToCompletePart2(startingId, nodes, directions));
		}
		
		for (int step : steps) {
			part2 = lcm(part2, step);
		}
		
		System.out.println("Steps to complete each key of part 2: " + steps);
		
		//System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}
	
	public static int stepsToCompletePart1(String startingNode, Map<String, Choice> nodes, String directions) {
		String currentNode = startingNode;
		int steps = 0;
		int step_idx = 0;
		while (!currentNode.equals("ZZZ")) {
			Choice choice = nodes.get(currentNode);
			char direction = directions.charAt(step_idx);
			
			switch(direction) {
				case 'L':
					currentNode = choice.left;
					break;
				case 'R':
					currentNode = choice.right;
					break;
				default:
					System.out.println("Unknown direction hit!");
			}
			
			steps++;
			step_idx++;
			if(step_idx >= directions.length())
				step_idx = 0;
		}
		
		return steps;
	}
	
	public static int stepsToCompletePart2(String startingNode, Map<String, Choice> nodes, String directions) {
		String currentNode = startingNode;
		int steps = 0;
		int step_idx = 0;
		while (!currentNode.endsWith("Z")) {
			Choice choice = nodes.get(currentNode);
			char direction = directions.charAt(step_idx);
			
			switch(direction) {
				case 'L':
					currentNode = choice.left;
					break;
				case 'R':
					currentNode = choice.right;
					break;
				default:
					System.out.println("Unknown direction hit!");
			}
			
			steps++;
			step_idx++;
			if(step_idx >= directions.length())
				step_idx = 0;
		}
		
		return steps;
	}
	
	public static long lcm(long a, long b) {
		if (a == 0 || b == 0) {
	        return 0;
	    }

	    long higher = Math.max(a, b);
	    long lower = Math.min(a, b);
	    long lcm = higher;
	    while (lcm % lower != 0) {
	        lcm += higher;
	    }
	    return lcm;
	}

}

class Choice {
	public String left;
	public String right;
	
	public Choice(String left, String right) {
		this.left = left;
		this.right = right;
	}
}

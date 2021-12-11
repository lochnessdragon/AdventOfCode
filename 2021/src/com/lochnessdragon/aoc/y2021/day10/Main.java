package com.lochnessdragon.aoc.y2021.day10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.lochnessdragon.aoc.y2021.utils.Utils;

public class Main {
	public static void main(String[] args) {
		String data = Utils.readFile("src/com/lochnessdragon/aoc/y2021/day10/input.txt");
	
		List<String> lines = Stream.of(data.split("\n")).collect(Collectors.toList());
		
		{
			Map<Character, Integer> scores = new HashMap<Character, Integer>();
			scores.put(')', 3);
			scores.put(']', 57);
			scores.put('}', 1197);
			scores.put('>', 25137);
			
			// part 1
			Stack<Character> charStack = new Stack<Character>();
			List<String> toRemove = new ArrayList<String>();
			int total = 0;
			
			for(int i = 0; i < lines.size(); i++) {
				char[] chars = lines.get(i).toCharArray();
				
				for(int j = 0; j < chars.length; j++) {
					char c = chars[j];
					
					if(c == '(' || c == '[' || c == '{' || c == '<') {
						charStack.push(c);
					} else {
						// we want to pop it and compare.
						char compareTo = inverse(charStack.pop());
						
						if(compareTo != c) {
							// its bad!
							System.out.println(lines.get(i) + " - Expected: " + compareTo + " but found: " + c + " instead.");
							total += scores.get(c);
							toRemove.add(lines.get(i));
							break;
						}
					}
				}
				
				charStack.clear();
			}
			
			System.out.println(lines.removeAll(toRemove));
			
			System.out.println("[ Part 1 ]: Total: " + total);
			
		}
		
		{
			// part 2
			List<Long> scores = new ArrayList<Long>();
			Map<Character, Integer> scoringTable = new HashMap<Character, Integer>();
			
			scoringTable.put('(', 1);
			scoringTable.put('[', 2);
			scoringTable.put('{', 3);
			scoringTable.put('<', 4);
			
			for(String line : lines) {
				Stack<Character> charStack = new Stack<Character>();
				
				char[] chars = line.toCharArray();
				for(char c : chars) {
					if(c == '(' || c == '[' || c == '{' || c == '<') {
						charStack.push(c);
					} else {
						charStack.pop(); // pop it off the stack
					}
				}
				
				// finally, calculate the score from whats on the stack
				int stackSize = charStack.size();
				long score = 0;
				
				for(int i = 0; i < stackSize; i++) {
					char c = charStack.pop();
					
					score *= 5;
					score += scoringTable.get(c);
				}
				
				scores.add(score);
			}
			
			scores = scores.stream().sorted().collect(Collectors.toList());
			
			System.out.println("[ Part 2 ]: Score: " + scores.get((scores.size() / 2)));
		}
	}

	private static char inverse(char c) {
		char result = '0';
		
		switch(c) {
		case '(':
			result = ')';
			break;
		case '[':
			result = ']';
			break;
		case '{':
			result = '}';
			break;
		case '<':
			result = '>';
			break;
		default:
			throw new NullPointerException();
		}
		
		return result;
	}
}

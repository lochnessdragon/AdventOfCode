package day6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource input = new Resource("src/day6/input.txt");

		List<String> lines = input.read();
		
		Map<String, Boolean> questionMap = new HashMap<String, Boolean>();
		int total = 0;
		for(String line : lines) {
			if(line.equals("")) {
//				System.out.println("clearing map");
				for(String key : questionMap.keySet()) {
					total++;
				}
				questionMap.clear();
			}
			
			for(char c : line.toCharArray()) {
				System.out.println(c);
				questionMap.put(Character.toString(c), true);
			}
			
		}
		
		for(String key : questionMap.keySet()) {
			total++;
		}
		questionMap.clear();
		
		System.out.println("Total: " + total);
		
		// part 2
		int total2 = 0;
		int groupCount = 0;
		List<String> answers = new ArrayList<String>();
		
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		
		for(String line : lines) {
			if(line.equals("")) {
				for(int i = 0; i < alphabet.length; i++) {
					int count = 0;
					
					for(String answer : answers) {
						if(answer.charAt(0) == alphabet[i]) {
							count++;
						}
					}
					
					if(count == groupCount) {
						total2++;
					}
					
				}
				
				answers.clear();
				groupCount = 0;
			} else {
				for(char c : line.toCharArray()) {
//					System.out.println(c);
					answers.add(Character.toString(c));
				}
				groupCount++;
			}
		}
		
		for(int i = 0; i < alphabet.length; i++) {
			int count = 0;
			
			for(String answer : answers) {
				if(answer.charAt(0) == alphabet[i]) {
					count++;
				}
			}
			
			if(count == groupCount) {
				total2++;
			}
			
		}
		
		answers.clear();
		groupCount = 0;
		
		System.out.println("Part 2: " + total2);
		
	}

}

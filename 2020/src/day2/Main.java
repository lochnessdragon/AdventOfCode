package day2;

import java.util.List;

import utils.Resource;

public class Main {
	
	public static void main(String[] args) {
		Resource file = new Resource("src/day2/input.txt");
		
		List<String> lines = file.read();
		
		int validPasswords = 0;
		
		for(String line : lines) {
			String[] parts = line.split(" ");
			int characterOccurences = countOccurencesInStr(parts[2], parts[1].charAt(0));
			
			String[] bounds = parts[0].split("-");
			if((Integer.parseInt(bounds[0])) <= characterOccurences && characterOccurences <= (Integer.parseInt(bounds[1]))) {
				System.out.println("[Part 1]: Password: " + parts[2] + " is valid for rule: " + parts[0] + " " + parts[1].charAt(0));
				validPasswords++;
			}
		}
		
		System.out.println("[Part 1]: Valid Passwords: " + validPasswords);
		
		validPasswords = 0;
		
		for(String line : lines) {
			String[] parts = line.split(" ");
			
			String[] bounds = parts[0].split("-");
			int bound1 = Integer.parseInt(bounds[0]);
			int bound2 = Integer.parseInt(bounds[1]);
			
			boolean pos1 = parts[2].charAt(bound1-1) == parts[1].charAt(0);
			boolean pos2 = parts[2].charAt(bound2-1) == parts[1].charAt(0);
			
			if(pos1 ^ pos2) {
				System.out.println("[Part 2]: Password: " + parts[2] + " is valid for rule: " + parts[0] + " " + parts[1].charAt(0));
				validPasswords++;
			}
		}
		
		System.out.println("[Part 2]: Valid Passwords: " + validPasswords);
		
	}
	
	private static int countOccurencesInStr(String string, char c) {
		int count = 0;
		for(char character : string.toCharArray()) {
			if(c == character) {
				count++;
			}
		}
		
		return count;
	}

}

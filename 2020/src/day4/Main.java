package day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource file = new Resource("src/day4/input.txt");
		List<String> input = file.read();
		
		List<String> passports = new ArrayList<String>();
		
		String passport = "";
		boolean tackOn = true;
		for(String line : input) {
			tackOn = !line.equals("");
			if(tackOn) 
				passport += " " + line;
			else {
				passports.add(passport.trim());
				passport = "";
			}
		}
		passports.add(passport.trim());
		
		int validPassports1 = 0;
		int validPassports2 = 0;
		int rejectedPassports = 0;
		for(String passportTest : passports) {
//			System.out.println(passportTest + "\n");
			
			Map<String, String> passportValues = new HashMap<String, String>();
			
			for(String pair : passportTest.split(" ")) {
				String[] keyValue = pair.split(":");
				passportValues.put(keyValue[0], keyValue[1]);
			}
			
			if(passportValues.containsKey("byr") && passportValues.containsKey("iyr") && passportValues.containsKey("eyr") && passportValues.containsKey("hgt") && passportValues.containsKey("hcl") && passportValues.containsKey("ecl") && passportValues.containsKey("pid")) {
				validPassports1++;
				
				boolean birthYearCorrect = checkYear(passportValues.get("byr"), 1920, 2002);
				boolean issueYearCorrect = checkYear(passportValues.get("iyr"), 2010, 2020);
				boolean expirationYearCorrect = checkYear(passportValues.get("eyr"), 2020, 2030);
				boolean heightCorrect = checkHeight(passportValues.get("hgt"));
				boolean hairColorCorrect = checkHairColor(passportValues.get("hcl"));
				boolean eyeColorCorrect = checkEyeColor(passportValues.get("ecl"));
				boolean pidCorrect = checkPID(passportValues.get("pid"));
				
				if(birthYearCorrect && issueYearCorrect && expirationYearCorrect && heightCorrect && hairColorCorrect && eyeColorCorrect && pidCorrect) {
					validPassports2++;
				}
				
			} else {
				//System.out.println("Rejected: " + passportTest);
				rejectedPassports++;
			}
		}
		
		// haha lol, it was 196
		System.out.println("[Part 1]: Valid Passports: " + validPassports1 + " Invalid: " + rejectedPassports + " with input size: " + passports.size());
		System.out.println("[Part 2]: Valid Passports: " + validPassports2);
		
//		for(String passportTest : passports) {
//			boolean birthYearCorrect = false;
//			birthYearCorrect = birthYearPattern.matcher(passportTest).find();
//			
//			if(birthYearCorrect) {
//				System.out.println("[Part 2]: Birth Year Correct");
//			}
//			
//		}
	}

	private static boolean checkHairColor(String string) {
		string = string.trim();
		
		if(string.length() == 7 && string.matches("#[0-9,a-f]{6}")) {
			return true;
		}
		
//		System.out.println("Incorrect Hair Color: " + string);
		
		return false;
	}

	private static boolean checkEyeColor(String string) {
		string = string.trim();
		
		String[] eyeColors = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
		
		if(string.length() == 3 && Arrays.stream(eyeColors).anyMatch(string::contains)) {
			return true;
		}
		
//		System.out.println("Incorrect Eye Color: " + string + " Length: " + string.length());
		
		return false;
	}

	private static boolean checkHeight(String string) {
		int height = Integer.parseInt(string.replaceFirst("cm", "").replaceFirst("in", ""));
		
		if(string.contains("in")) {
			if(height >= 59 && height <= 76) {
				return true;
			}
		} else if(string.contains("cm")) {
			if(height >= 150 && height <= 193) {
				return true;
			}
		}
		
		return false;
	}

	private static boolean checkPID(String string) {
		string = string.trim();
		
		if(string.length() == 9 && string.matches("[0-9]{9}")) {
			return true;
		}
		
		return false;
	}

	private static boolean checkYear(String string, int min, int max) {
		int year = Integer.parseInt(string);
		
		if(min <= year && year <= max) {
			return true;
		}
		
		return false;
	}

}

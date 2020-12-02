package day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String args[]) {
	
		List<String> input = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/day1/input.txt")));
			
			while(reader.ready()) {
				input.add(reader.readLine());
			}
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int sum = 0;
		int result = 0;
		
		for(String line : input) {
			result = getFuelRequiredRecursively(Integer.parseInt(line));
			sum += result;
			System.out.println(line + " = " + result);
		}
		
		System.out.println("Sum: " + sum);
		
//		For a mass of 12, divide by 3 and round down to get 4, then subtract 2 to get 2.
//		For a mass of 14, dividing by 3 and rounding down still yields 4, so the fuel required is also 2.
//		For a mass of 1969, the fuel required is 654.
//		For a mass of 100756, the fuel required is 33583.
		
//		System.out.println("12 = " + getFuelRequired(12) + " expected: " + 2);
//		System.out.println("14 = " + getFuelRequired(14) + " expected: " + 2);
//		System.out.println("1969 = " + getFuelRequired(1969) + " expected: " + 654);
//		System.out.println("100756 = " + getFuelRequired(100756) + " expected: " + 33583);
		
//		A module of mass 14 requires 2 fuel. This fuel requires no further fuel (2 divided by 3 and rounded down is 0, which would call for a negative fuel), so the total fuel required is still just 2.
//		At first, a module of mass 1969 requires 654 fuel. Then, this fuel requires 216 more fuel (654 / 3 - 2). 216 then requires 70 more fuel, which requires 21 fuel, which requires 5 fuel, which requires no further fuel. So, the total fuel required for a module of mass 1969 is 654 + 216 + 70 + 21 + 5 = 966.
//		The fuel required by a module of mass 100756 and its fuel is: 33583 + 11192 + 3728 + 1240 + 411 + 135 + 43 + 12 + 2 = 50346.
//		System.out.println("14 = " + getFuelRequiredRecursively(14) + " expected: " + 2);
//		System.out.println("1969 = " + getFuelRequiredRecursively(1969) + " expected: " + 966);
//		System.out.println("100756 = " + getFuelRequiredRecursively(100756) + " expected: " + 50346);
		
	}
	
	// take its mass, divide by three, round down, and subtract 2.
	public static int getFuelRequired(int mass) {
		return (int) ((Math.floor(((double) mass / 3.0))) - 2);
	}
	
	public static int getFuelRequiredRecursively(int mass) {
		int result = getFuelRequired(mass);
		//System.out.println("Result: " + result);
		int returnValue = result;
		while(true) {
			result = getFuelRequired(result);
			//System.out.println("Result: " + result);
			if(result <= 0) {
				break;
			}
			returnValue += result;
		}
		
		return returnValue;
	}

}

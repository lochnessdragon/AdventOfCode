package day2;

import java.util.List;

import day5.Utils;

public class Main {

	public static void main(String[] args) {
		
		List<String> input = Utils.loadFile("src/day2/input.txt");
		String default_mem = input.get(0);
		
		String test1 = "1,9,10,3,2,3,11,0,99,30,40,50"; // becomes 2,0,0,0,99
		String[] test1split = default_mem.split(",");
		
		int[] stack = new int[test1split.length];
		
		for(int i = 0; i < stack.length; i++) {
			System.out.println("Position: " + i + " Value: " + Integer.parseInt(test1split[i]));
			stack[i] = Integer.parseInt(test1split[i]);
		}
		
		for(int i = 0; i < 100; i++) {
			for(int j = 0; j < 100; j++) {
				int[] stack1 = stack.clone();
				stack1[1] = i;
				stack1[2] = j;
				stack1 = runCpu(stack1);
				System.out.println("pos 0: " + stack1[0] + " pos 1: " + stack1[1] + " pos 2: " + stack1[2]);
				if(stack1[0] == 19690720) {
					System.out.println("Found the answer: " + stack1[0] + " pos 1: " + stack1[1] + "pos 2: " + stack1[2]);
					System.out.println("Puzzle Solution is: " + (100 * stack1[1] + stack1[2]));
					System.exit(0);
				}
			}
		}
		
//		runCpu(stack.clone());
//		
//		for(int j = 0; j < stack.length; j++) {
//			System.out.println("Location: " + j + " Value: " + stack[j]);
//		}
		
	}
	
	public static int[] runCpu(int[] stack) {
		
		boolean cpuIsRunning = true;
		
		int ptr = 0;
		int opcode = 0;
		int int1 = 0;
		int int2 = 0;
		int result = 0;
		
		while(cpuIsRunning) {
			
			opcode = stack[ptr];
			//System.out.println(opcode);
			
			switch (opcode) {
			case 1:
				int1 = stack[stack[ptr + 1]];
				int2 = stack[stack[ptr + 2]];
				result = int1 + int2;
				stack[stack[ptr + 3]] = result;
//				System.out.println("Added: " + int1 + " + " + int2 + " = " + result);
				break;
			
			case 2:
				int1 = stack[stack[ptr + 1]];
				int2 = stack[stack[ptr + 2]];
				result = int1 * int2;
				stack[stack[ptr + 3]] = result;
//				System.out.println("Multiplied: " + int1 + " x " + int2 + " = " + result);
				break;
			
			case 99:
				System.out.println("Shutting down!");
				cpuIsRunning = false;
				break;
				
			default:
				break;
			}
			
			ptr = ptr + 4;
			
		}
		
		return stack;
		
	}

}

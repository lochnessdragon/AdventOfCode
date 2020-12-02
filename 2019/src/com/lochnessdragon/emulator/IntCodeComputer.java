package com.lochnessdragon.emulator;

import java.io.Console;
import java.util.Scanner;

public class IntCodeComputer {
	
	public static void test() {
		int[] instruction = parseOpcode(56703);
		for(int instructionValue : instruction) {
			System.out.println(instructionValue);
		}
	}
	
	public static int[] runCpu(int[] stack) {
		
		boolean cpuIsRunning = true;
		
		int ptr = 0;
		int opcode = 0;
		int[] instruction = new int[4];
		
		int modeParam1 = 0;
		int modeParam2 = 0;
		int modeParam3 = 0;
		int ptr_increase = 0;
		
		while(cpuIsRunning) {
			
			instruction = parseOpcode(stack[ptr]);
			opcode = instruction[3];
			modeParam1 = instruction[2];
			modeParam2 = instruction[1];
			modeParam3 = instruction[0];
			//System.out.println(opcode);
			
			switch (opcode) {
			case 1:
//				int1 = stack[stack[ptr + 1]];
//				int2 = stack[stack[ptr + 2]];
//				result = int1 + int2;
				stack[stack[ptr + 3]] = add(getValue(stack, ptr+1, modeParam1), getValue(stack, ptr+2, modeParam2));
//				System.out.println("Added: " + int1 + " + " + int2 + " = " + result);
				ptr_increase = 4;
				break;
			
			case 2:
//				int1 = stack[stack[ptr + 1]];
//				int2 = stack[stack[ptr + 2]];
//				result = int1 * int2;
				stack[stack[ptr + 3]] = multiply(getValue(stack, ptr+1, modeParam1), getValue(stack, ptr+2, modeParam2));
//				System.out.println("Multiplied: " + int1 + " x " + int2 + " = " + result);
				ptr_increase = 4;
				break;
			
			case 3:
				
				Scanner scanner = new Scanner(System.in);
				
				System.out.print("Input Int: ");
				int value = scanner.nextInt();
//				boolean integer = false;
//				while(!integer) {
//					System.out.print("Input Int: ");
//				
//					String input = console.readLine();
//					
//					try {
//						value = Integer.parseInt(input);
//						integer = true;
//					} catch (Exception e) {
//						System.out.println("You did not input an integer!");
//					}
//				}
				
				stack[stack[ptr + 1]] = value;
				
				ptr_increase = 2;
				break;
				
			case 4:
				System.out.println(getValue(stack, ptr + 1, modeParam1));
				ptr_increase = 2;
				break;
				
			case 5: //jump-if-true
				
				if(getValue(stack, ptr + 1, modeParam1) != 0) {
					ptr = getValue(stack, ptr + 2, modeParam2);
					continue;
				}
				ptr_increase = 3;
				break;
				
			case 6: // jump if false
				if(getValue(stack, ptr + 1, modeParam1) == 0) {
					ptr = getValue(stack, ptr + 2, modeParam2);
					continue;
				}
				ptr_increase = 3;
				break;
				
			case 7: // less than
				if(getValue(stack, ptr + 1, modeParam1) < getValue(stack, ptr + 2, modeParam2)) {
					stack[stack[ptr + 3]] = 1;
				} else {
					stack[stack[ptr + 3]] = 0;
				}
				
				ptr_increase = 4;
				break;
				
			case 8: // equals
				if(getValue(stack, ptr + 1, modeParam1) == getValue(stack, ptr + 2, modeParam2)) {
					stack[stack[ptr + 3]] = 1;
				} else {
					stack[stack[ptr + 3]] = 0;
				}
				
				ptr_increase = 4;
				break;
				
			case 99:
				System.out.println("Shutting down!");
				cpuIsRunning = false;
				break;
				
			default:
				break;
			}
			
			ptr = ptr + ptr_increase;
			
		}
		
		return stack;
	}
	
	private static int[] parseOpcode(int opcode) {
		int[] instruction = new int[4];
		instruction[3] = opcode % 100;
		opcode = opcode / 100;
		
		for(int i = 0; i < 3; i++) {
			instruction[2 - i] = opcode % 10;
			opcode = opcode / 10;
			//System.out.println(opcode + ":" + instruction[2 - i]);
		}
		
		return instruction;
	}

	private static int add(int a, int b) {
		return a + b;
	}
	
	private static int multiply(int a, int b) {
		return a * b;
	}
	
	private static int getValue(int[] stack, int ptr, int mode) {
		if(mode == 0) {
			return stack[stack[ptr]];
		} else {
			return stack[ptr];
		}
	}
	
	public static int[] loadStack(String line) {
		String[] mem = line.split(",");
		int[] stack = new int[mem.length];
		
		for(int i = 0; i < stack.length; i++) {
//			System.out.println("Position: " + i + " Value: " + Integer.parseInt(mem[i]));
			stack[i] = Integer.parseInt(mem[i]);
		}
		
		return stack;
	}

}

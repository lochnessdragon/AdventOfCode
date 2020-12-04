package com.lochnessdragon.emulator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class IntCodeComputer {
	
	private MemMap stack;
	private int programCounter;
	private long relativeBase;
	private boolean running;
	private InputProvider input;
	private OutputProvider output;
	private Queue<Long> outputField;
	private int inputTimes = 0;
	
	public void test() {
		int[] instruction = parseOpcode(56703);
		for(int instructionValue : instruction) {
			System.out.println(instructionValue);
		}
	}
	
	public IntCodeComputer(String defaultMem) {
		this.stack = loadStack(defaultMem);
		this.programCounter = 0;
		this.relativeBase = 0;
		this.running = false;
		this.setRegularInput();
		this.setRegularOutput();
	}
	
	public MemMap getStack() {
		return this.stack;
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public void setInput(InputProvider input) {
		this.input = input;
	}
	
	public void setInput(Queue<Integer> input) {
		this.input = (index) -> {
			return getInputFromQueue(input);
		};
	}
	
	public void setRegularInput() {
		this.input = (index) -> {
			return getRegularInput();
		};
	}
	
	public void setOutput(OutputProvider output) {
		this.output = output;
	}
	
	public void setRegularOutput() {
		this.output = (value, list) -> {return this.logOutput(value, list);};
	}

	public Queue<Long> getOutput() {
		return this.outputField;
	}
	
	public int getInputTimes() {
		return this.inputTimes;
	}
	
	public void runCpu() {
		this.outputField = new LinkedList<Long>();
		
		this.programCounter = 0;
		this.relativeBase = 0;
		this.running = true;
		
		int opcode = 0;
		int[] instruction = new int[4];
		
		int modeParam1 = 0;
		int modeParam2 = 0;
		int modeParam3 = 0;
		int ptr_increase = 0;
		
		while(this.running) {
			
			instruction = parseOpcode( (int) stack.getValue(programCounter));
			opcode = instruction[3];
			modeParam1 = instruction[2];
			modeParam2 = instruction[1];
			modeParam3 = instruction[0];
			//System.out.println(opcode);
			
			ptr_increase = runOpcode(opcode, modeParam1, modeParam2, modeParam3);
			
			programCounter = programCounter + ptr_increase;
			
		}
	}
	
	public void step() {
		int opcode = 0;
		int[] instruction = new int[4];
		
		int modeParam1 = 0;
		int modeParam2 = 0;
		int modeParam3 = 0;
		int ptr_increase = 0;
		
		instruction = parseOpcode( (int) stack.getValue(programCounter));
		opcode = instruction[3];
		modeParam1 = instruction[2];
		modeParam2 = instruction[1];
		modeParam3 = instruction[0];
		System.out.println(opcode + ":" + modeParam1);
		
		ptr_increase = runOpcode(opcode, modeParam1, modeParam2, modeParam3);
		
		programCounter = programCounter + ptr_increase;
	}
	
	public long runUntilOutput() {
		this.outputField = new LinkedList<Long>();
		this.running = true;
		
		int opcode = 0;
		int[] instruction = new int[4];
		
		int modeParam1 = 0;
		int modeParam2 = 0;
		int modeParam3 = 0;
		int ptr_increase = 0;
		
		while(this.running && outputField.isEmpty()) {
			instruction = parseOpcode((int) stack.getValue(programCounter));
			opcode = instruction[3];
			modeParam1 = instruction[2];
			modeParam2 = instruction[1];
			modeParam3 = instruction[0];
			//System.out.println(opcode);
			
			ptr_increase = runOpcode(opcode, modeParam1, modeParam2, modeParam3);
			
			programCounter = programCounter + ptr_increase;
		}
		
		if(this.running == false) {
			this.programCounter = 0;
			return 0;
		}
		
		return this.outputField.peek();
	}
	
	private int[] parseOpcode(int opcode) {
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
	
	private int runOpcode(int opcode, int modeParam1, int modeParam2, int modeParam3) {
		int ptr_increase = 0;
		
		switch (opcode) {
		case 1: // add
			stack.setValue(getPtr(programCounter + 3, modeParam3), add(getValue(programCounter+1, modeParam1), getValue(programCounter+2, modeParam2)));
			//stack[stack[programCounter + 3]] = add(getValue(programCounter+1, modeParam1), getValue(programCounter+2, modeParam2));
//			System.out.println("Added: " + int1 + " + " + int2 + " = " + result);
			ptr_increase = 4;
			break;
		
		case 2: // multiply
			stack.setValue(getPtr(programCounter + 3, modeParam3), multiply(getValue(programCounter+1, modeParam1), getValue(programCounter+2, modeParam2)));
			//stack[stack[programCounter + 3]] = multiply(getValue(programCounter+1, modeParam1), getValue(programCounter+2, modeParam2));
//			System.out.println("Multiplied: " + int1 + " x " + int2 + " = " + result);
			ptr_increase = 4;
			break;
		
		case 3: // get
			int value = getInput();
			stack.setValue(getPtr(programCounter + 1, modeParam1), value);
			//stack[stack[programCounter + 1]] = value;
			
			ptr_increase = 2;
			break;
			
		case 4: // print
			runOutput(getValue(programCounter + 1, modeParam1));
			ptr_increase = 2;
			break;
			
		case 5: //jump-if-true
			
			if(getValue(programCounter + 1, modeParam1) != 0) {
				programCounter = (int) getValue(programCounter + 2, modeParam2);
				break;
			}
			ptr_increase = 3;
			break;
			
		case 6: // jump if false
			if(getValue(programCounter + 1, modeParam1) == 0) {
				programCounter = (int) getValue(programCounter + 2, modeParam2);
				break;
			}
			ptr_increase = 3;
			break;
			
		case 7: // less than
			if(getValue(programCounter + 1, modeParam1) < getValue(programCounter + 2, modeParam2)) {
				stack.setValue(getPtr(programCounter + 3, modeParam3), 1);
				//stack[stack[programCounter + 3]] = 1;
			} else {
				stack.setValue(getPtr(programCounter + 3, modeParam3), 0);
				//stack[stack[programCounter + 3]] = 0;
			}
			
			ptr_increase = 4;
			break;
			
		case 8: // equals
			if(getValue(programCounter + 1, modeParam1) == getValue(programCounter + 2, modeParam2)) {
				stack.setValue(getPtr(programCounter + 3, modeParam3), 1);
				//stack[stack[programCounter + 3]] = 1;
			} else {
				stack.setValue(getPtr(programCounter + 3, modeParam3), 0);
				//stack[stack[programCounter + 3]] = 0;
			}
			
			ptr_increase = 4;
			break;
		case 9: // adjust relative base
			this.relativeBase += getValue(programCounter + 1, modeParam1);
			ptr_increase = 2;
			break;
			
		case 99: // halt
			//System.out.println("Shutting down!");
			this.running = false;
			break;
			
		default:
			break;
		}
		
		return ptr_increase;
	}

	private long add(long a, long b) {
		return a + b;
	}
	
	private long multiply(long a, long b) {
		return a * b;
	}
	
	private long getValue(int ptr, int mode) {
		if(mode == 0 || mode == 2) {
			return this.stack.getValue(getPtr(ptr, mode));
		} else  {
			return this.stack.getValue(ptr);
		}
	}
	
	private long getPtr(int ptr, int mode) {
		if(mode == 0) {
			return this.stack.getValue(ptr);
		} else if (mode == 2) {
			return this.stack.getValue(ptr) + this.relativeBase;
		} else {
			return -1;
		}
	}
	
	private int getInputFromQueue(Queue<Integer> input) {
		int value = 0;
		if(!input.isEmpty()) {
			value = input.poll();
		} else {
			value = getRegularInput();
		}
		
		System.out.println("Getting Input: " + value);
		
		return value;
	}
	
	private int getRegularInput() {
		int value = 0;
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Input Int: ");
		value = scanner.nextInt();
		scanner.close();
		
		return value;
	}
	
	private int getInput() {
		int value = this.input.run(this.inputTimes);
		this.inputTimes++;
		System.out.println("Getting Input: " + value);
		return value;
	}
	
	private void runOutput(long value) {
		this.outputField = this.output.out(value, this.outputField);
	}
	
	private Queue<Long> logOutput(long value, Queue<Long> log) {
		System.out.println("Output: " + value);
		log.add(value);
		return log;
	}
	
	public static MemMap loadStack(String line) {
		String[] mem = line.split(",");
		long[] stack = new long[mem.length];
		
		for(int i = 0; i < stack.length; i++) {
//			System.out.println("Position: " + i + " Value: " + Integer.parseInt(mem[i]));
			stack[i] = Long.parseLong(mem[i]);
		}
		
		return new MemMap(stack);
	}

}

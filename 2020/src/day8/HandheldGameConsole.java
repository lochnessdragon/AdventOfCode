package day8;

import java.util.ArrayList;
import java.util.List;

public class HandheldGameConsole {
	
	private int acc;
	private int pc;
	
	private List<String> program;
	
	public HandheldGameConsole(List<String> program) {
		this.pc = 0;
		this.acc = 0;
		this.program = program;
	}
	
	public boolean runUntilRepeat() {
		this.pc = 0;
		this.acc = 0;
		List<Integer> lastIndexes = new ArrayList<Integer>();
		
		boolean repeat = false;
		
		while(!lastIndexes.contains(this.pc)) {
			if(this.pc >= program.size()) {
				// once we are out of the loop, we are free.
				break;
			}
			
			lastIndexes.add(this.pc);
			
			String opcode = program.get(this.pc);
			String[] parts = opcode.split(" ", 2);
			
			int param1 = Integer.parseInt(parts[1].replaceAll("\\+", ""));
			
			switch(parts[0]) {
			case "acc":
				this.acc += param1;
			case "nop":
				this.pc++;
				break;
			case "jmp":
				this.pc += param1;
				break;
			default:
				break;
			}
//			System.out.println(this.pc);
		}
		
		if(lastIndexes.contains(this.pc)) {
			repeat = true;
		}
		
		return repeat;
	}

	public void setProgram(List<String> program) {
		this.program = program;
	}
	
	public int getAcc() {
		return this.acc;
	}

}

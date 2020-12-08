package day8;

import java.util.List;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource input = new Resource("src/day8/input.txt");
		List<String> lines = input.read();
		
		HandheldGameConsole console = new HandheldGameConsole(lines);
		
		// part 1
		console.runUntilRepeat();
		
		System.out.println("[Part 1]: The bootcode repeated back and the accumulator was: " + console.getAcc());
		
		// part 2
		boolean brokenFree = false;
		int index = 0;
		while(!brokenFree) {
			String line = lines.get(index);
			if(line.contains("acc")) {
			} else {
				if(line.contains("nop")) {
					line = line.replaceAll("nop", "jmp");
				} else if(line.contains("jmp")) {
					line = line.replaceAll("jmp", "nop");
				}
				
				lines.set(index, line);
				
				console.setProgram(lines);
				brokenFree = !console.runUntilRepeat();
				
				if(line.contains("nop")) {
					line = line.replaceAll("nop", "jmp");
				} else if(line.contains("jmp")) {
					line = line.replaceAll("jmp", "nop");
				}
				
				lines.set(index, line);
				
			}
			
			index++;
		}
		
		System.out.println("[Part 2]: Repeat fixed, accumulator is: " + console.getAcc());
		
	}

}

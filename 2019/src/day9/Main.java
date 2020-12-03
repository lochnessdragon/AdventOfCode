package day9;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.lochnessdragon.emulator.IntCodeComputer;

import day5.Utils;

public class Main {

	public static void main(String[] args) {
		List<String> program = Utils.loadFile("src/day9/input.txt");
		
		IntCodeComputer computer = new IntCodeComputer(program.get(0));
		
		computer.setRegularInput();
		System.out.println("Running computer");
		computer.runCpu();
	}

}

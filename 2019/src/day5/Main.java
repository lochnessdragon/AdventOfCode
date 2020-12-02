package day5;

import java.util.List;

import com.lochnessdragon.emulator.IntCodeComputer;

public class Main {

	public static void main(String[] args) {
		List<String> input = Utils.loadFile("src/day5/input.txt");
		String default_mem = input.get(0);
		int[] stack = IntCodeComputer.loadStack(default_mem);
		//IntCodeComputer.test();
		stack = IntCodeComputer.runCpu(stack);
		
	}

}

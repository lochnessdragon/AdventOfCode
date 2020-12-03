package day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.sql.rowset.spi.TransactionalWriter;

import com.lochnessdragon.emulator.IntCodeComputer;

import day5.Utils;

public class Main {

	static List<List<Integer>> trialPhases = new ArrayList<List<Integer>>();
	
	public static void main(String[] args) {
		List<String> program = Utils.loadFile("src/day7/input.txt");
		
		int[] phases = { 0, 1, 2, 3, 4 };
		
		heapAlgorithm(phases, 5, 5);
		
		IntCodeComputer amplifier;
		
		int winningNumber = 0;
		
		for(List<Integer> phase : trialPhases) {
			Queue<Integer> input = new LinkedList<Integer>();
		
			Queue<Integer> output = new LinkedList<Integer>();
			output.add(0);
		
			for(int i = 0; i < 5; i++) {
				System.out.println("Amplifier: " + (i+1));
				input.clear();
				input.add(phase.get(i));
				input.add(output.poll());
			
				amplifier = new IntCodeComputer(program.get(0));
				amplifier.setInput(input);
				amplifier.runCpu();
				output = amplifier.getOutput();
			}
			
			if(output.peek() > winningNumber) {
				winningNumber = output.peek();
			}
		}
		
		System.out.println("[Part 1]: MAX THRUSTER VALUE: " + winningNumber);
		
		// part 2
		winningNumber = 0;
		
		int[] feedbackPhases = {9, 8, 7, 6, 5};
		
		trialPhases = new ArrayList<List<Integer>>();
		
		heapAlgorithm(feedbackPhases, 5, 5);
		
		for(List<Integer> phase : trialPhases) {
		
			List<IntCodeComputer> amplifiers = new ArrayList<IntCodeComputer>();
			amplifiers.add(new IntCodeComputer(program.get(0))); // A
			amplifiers.add(new IntCodeComputer(program.get(0))); // B
			amplifiers.add(new IntCodeComputer(program.get(0))); // C
			amplifiers.add(new IntCodeComputer(program.get(0))); // D
			amplifiers.add(new IntCodeComputer(program.get(0))); // E
		
			boolean systemRunning = true;
		
			int output = 0;
		
			while(systemRunning) {
				for(int i = 0; i < amplifiers.size(); i++) {
					final int sendOutput = output;
					final int sendIndex = i;
				
					amplifiers.get(i).setInput((index) -> {
						if(index > 0) {
							return sendOutput;
						} else {
							return phase.get(sendIndex);
						}
					});
				
					System.out.println("Running computer: " + i);
					amplifiers.get(i).runUntilOutput();
					if(amplifiers.get(i).getOutput().peek() != null) {
						output = amplifiers.get(i).getOutput().poll();
					}
				}
			
				if(!(amplifiers.get(amplifiers.size() - 1).isRunning())) {
					systemRunning = false;
				}
			}
		
			if(winningNumber < output) {
				winningNumber = output;
			}
		}
		
		System.out.println("[Part 2]: MAX THRUSTER VALUE: " + winningNumber);
	}

	private static void heapAlgorithm(int[] array, int size, int number) {
		// if size becomes 1 then prints the obtained
        // permutation
        if (size == 1) {
            //printArr(array, number);
        	List<Integer> perm = new ArrayList<Integer>();
        	
        	for(int value : array) {
        		perm.add(value);
        	}
        	
        	trialPhases.add(perm);
        }
 
        for (int i = 0; i < size; i++) {
            heapAlgorithm(array, size - 1, number);
 
            // if size is odd, swap 0th i.e (first) and
            // (size-1)th i.e (last) element
            if (size % 2 == 1) {
                int temp = array[0];
                array[0] = array[size - 1];
                array[size - 1] = temp;
            }
 
            // If size is even, swap ith 
            // and (size-1)th i.e last element
            else {
                int temp = array[i];
                array[i] = array[size - 1];
                array[size - 1] = temp;
            }
        }
	}

}

package day9;

import java.util.ArrayList;
import java.util.List;

import utils.Utils;

public class Day9 {

	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day9/data.txt");
		
		int part1 = 0;
		int part2 = 0;
		for (String line : lines) {
			Reading reading = new Reading(line);
			reading.fillDerivatives();
			part1 += reading.getNext();
			part2 += reading.getPrev();
		}
		
		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}

}

class Reading {
	public List<List<Integer>> derivatives;
	
	public Reading(String line) {
		derivatives = new ArrayList<List<Integer>>();
		String[] parts = line.split(" ");
		List<Integer> firstList = new ArrayList<Integer>();
		for (String part : parts) {
			firstList.add(Integer.parseInt(part));
		}
		
		derivatives.add(firstList);
	}
	
	public void fillDerivatives() {
		boolean foundZero = false;
		int atDerivative = 0;
		while (!foundZero) {
			// go Towards Zero (thx Agatha Christie)
			List<Integer> positions = derivatives.get(atDerivative);
			
			List<Integer> nextDerivative = new ArrayList<Integer>();
			foundZero = true;
			for (int i = 0; i < positions.size() - 1; i++) {
				int difference = positions.get(i+1) - positions.get(i);
				nextDerivative.add(difference);
				if (difference != 0)
					foundZero = false;
			}
			
			derivatives.add(nextDerivative);
			
			atDerivative++;
		}
	}
	
	public int getNext() {	
		// increase back up
		int nextVal = 0;
		for (int i = derivatives.size() - 1; i >= 0; i--) {
			List<Integer> derivative = derivatives.get(i);
			nextVal += derivative.get(derivative.size() - 1);
		}
		
		return nextVal;
	}
	
	public int getPrev() {
		// increase back up
		int prevVal = 0;
		for (int i = derivatives.size() - 1; i >= 0; i--) {
			List<Integer> derivative = derivatives.get(i);
			prevVal = derivative.get(0) - prevVal;
		}
				
		return prevVal;
	}
}

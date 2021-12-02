package day13;

import java.util.ArrayList;
import java.util.List;

import utils.Resource;

public class Main {
	
	public static void main(String args[]) {
		Resource input = new Resource("src/day13/input.txt");
		List<String> lines = input.read();
		
		// part 1
		int beginTimestep = 0;
		
		beginTimestep = Integer.parseInt(lines.get(0));
		
		String[] busIdsUnparsed = lines.get(1).split(",");
		
		List<Integer> busIds = new ArrayList<Integer>();
		
		long smallestTimestep = Long.MAX_VALUE;
		int smallestIdNumber = 0;
		
		for(String id : busIdsUnparsed) {
			if(id.equals("x")) {
				busIds.add(1);
				continue;
			}
			
			int idNumber = Integer.parseInt(id);
			
			double factor = (double) beginTimestep / (double) idNumber;
			System.out.println("Factor: " + factor);
			
			int multiple = (int) Math.ceil(factor);
			
			System.out.println("Multiple: " + multiple);
			
			if((multiple * idNumber) < smallestTimestep) {
				smallestTimestep = multiple * idNumber;
				smallestIdNumber = idNumber;
			}
			
			busIds.add(Integer.parseInt(id));
		}
		
		System.out.println("[Part 1]: The smallest id number is: " + smallestIdNumber + " at time: " + smallestTimestep + " answer: " + (smallestIdNumber * (smallestTimestep - beginTimestep)));
		
		// part 2
		smallestTimestep = 0;
		boolean found = false;
		
		long multiple = 100000000000000L / busIds.get(0);
		while(!found) {
			
			long potentialTimestep = multiple * busIds.get(0);
			
			for(int i = 1; i < busIds.size(); i++) {
				potentialTimestep++;
				
				if(busIds.get(i) == 1) {
					continue;
				}
				
				if(!isMultiple(potentialTimestep, busIds.get(i))) {
					break;
				}
				
				if(i == (busIds.size() - 1)) {
					smallestTimestep = multiple * busIds.get(0);
					found = true;
					break;
				}
			}
			
			multiple++;
		}
		
		System.out.println("[Part 2]: The smallest timestep is: " + smallestTimestep);
		
	}

	private static boolean isMultiple(long multiple, int factor) {
		return (multiple % factor) == 0;
	}

}

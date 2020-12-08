package day5;

import java.util.ArrayList;
import java.util.List;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource file = new Resource("src/day5/input.txt");
		List<String> lines = file.read();
		
		int minId = Integer.MAX_VALUE;
		int maxId = 0;
		
		List<Integer> ids = new ArrayList<Integer>();
		
		for(String line : lines) {
			// decode
			int row = 0;
			int column = 0;
			
			String rowId = line.substring(0, 7);
			int minRow = 0; 
			int maxRow = 127;
			
			for(char c : rowId.toCharArray()) {
				int divisor = ((maxRow-minRow) / 2)+minRow;
				//System.out.println("Divisor: " + divisor);
				
				if(c == 'F') {
					// front part
					maxRow = divisor;
				} else {
					// back part
					minRow = divisor;
				}
				
				//System.out.println("Min: " + minRow + " Max: " + maxRow);
				
			}
			
			String colId = line.substring(7, 10);
			int minCol = 0; 
			int maxCol = 7;
			
			for(char c : colId.toCharArray()) {
				int divisor = ((maxCol-minCol) / 2)+minCol;
				//System.out.println("Divisor: " + divisor);
				
				if(c == 'L') {
					// front part
					maxCol = divisor;
				} else {
					// back part
					minCol = divisor;
				}
				
				//System.out.println("Min: " + minCol + " Max: " + maxCol);
				
			}
			
			int id = ((maxRow * 8) + maxCol);
			
			ids.add(id);
			
			System.out.println("Id: " + id);
			
			if(id > maxId) {
				maxId = id;
			}
			
			if(id < minId) {
				minId = id;
			}
			
		}
		
		System.out.println("[Part 1]: Max ID: " + maxId);
		
		// part 2
		for(int i = minId; i <= maxId; i++) {
			if(!ids.contains(i)) {
				System.out.println("Missing: " + i);
			}
		}

	}

}

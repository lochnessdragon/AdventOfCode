package com.lochnessdragon.aoc.y2021.day5;

import com.lochnessdragon.aoc.y2021.utils.Utils;
import com.lochnessdragon.aoc.y2021.utils.Vector2i;

public class Main {

	public static void main(String[] args) {
		String data = Utils.readFile("src/com/lochnessdragon/aoc/y2021/day5/input.txt");
		String[] lines = data.split("\n");
		
		// pipes 
		PipesGrid pipesGrid = new PipesGrid(1000);
		boolean calculatePart1 = false; // set to true to calculate for part 1, else its false.
		
		// convert all the data into pipe paths
		for(String line : lines) {
			String[] positions = line.split("->");
			
			Vector2i startPos = getPosition(positions[0]);
			Vector2i endPos = getPosition(positions[1]);
			
			if(startPos.x != endPos.x && startPos.y != endPos.y && calculatePart1 == true) {
				// skip!
				continue;
			}
			
			// fill in pipes array
			pipesGrid.addPipe(startPos, endPos);
		}
		
		{
			// part 1 and 2 calculate number of points where at least 2 lines overlap
			Vector2i position = new Vector2i(0, 0);
			int count = 0;
			
			while(position.x < 1000) {
				while(position.y < 1000) {
					if(pipesGrid.getPosition(position) > 1) {
						count++;
					}
					
					position.y++;
				}
				position.y = 0;
				position.x++;
			}
			
			System.out.println("[ Part 1 + 2 ]: Count is: " + count);
		}
		
	}

	private static Vector2i getPosition(String input) {
		String[] parts = input.split(",");
		return new Vector2i(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
	}

}

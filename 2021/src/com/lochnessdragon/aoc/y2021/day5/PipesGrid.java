package com.lochnessdragon.aoc.y2021.day5;

import com.lochnessdragon.aoc.y2021.utils.Vector2i;

public class PipesGrid {

	private int[][] grid; // accessed with [y][x] ( easier to print )

	public PipesGrid(int size) {
		grid = new int[size][size];
	}
	
	public void addPipeSection(Vector2i sectionPos) {
		grid[sectionPos.y][sectionPos.x] += 1;
	}
	
	public void addPipe(Vector2i startPos, Vector2i endPos) {
		int step = 1;
		
		while(startPos.x != endPos.x || startPos.y != endPos.y) {
			// step
			//System.out.println("Pos X: " + startPos.x + " Pos Y: " + startPos.y);
			addPipeSection(startPos);
			
			Vector2i difference = startPos.difference(endPos);
			startPos.x += step * Integer.signum(difference.x);
			startPos.y += step * Integer.signum(difference.y);
		}
		
		addPipeSection(endPos); // finally, append the ending position
	}
	
	public int getPosition(Vector2i position) {
		return grid[position.y][position.x];
	}
	
}

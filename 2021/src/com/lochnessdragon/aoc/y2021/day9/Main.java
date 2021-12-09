package com.lochnessdragon.aoc.y2021.day9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.lochnessdragon.aoc.y2021.utils.Utils;
import com.lochnessdragon.aoc.y2021.utils.Vector2i;

public class Main {
	public static void main(String[] args) {
		String data = Utils.readFile("src/com/lochnessdragon/aoc/y2021/day9/input.txt");
		
		String[] lines = data.split("\n");
		
		int[][] heightMap = new int[lines.length][lines[0].length()];
		
		for(int y = 0; y < lines.length; y++) {
			char[] heights = lines[y].toCharArray();
			
			for(int x = 0; x < heights.length; x++) {
				heightMap[y][x] = heights[x] - '0';
			}
		}
		
		List<Vector2i> lowPointCoords = new ArrayList<Vector2i>();
		
		{
			// part 1
			int sum = 0;
			
			for(int y = 0; y < heightMap.length; y++) {
				for(int x = 0; x < heightMap[y].length; x++) {
					int height = heightMap[y][x];
					int tileUp = 10;
					int tileDown = 10;
					int tileLeft = 10;
					int tileRight = 10;
					
					if(y > 0)
						tileUp = heightMap[y - 1][x];
					if(y < (heightMap.length - 1))
						tileDown = heightMap[y + 1][x];
					if(x > 0)
						tileLeft = heightMap[y][x - 1];
					if(x < (heightMap[y].length - 1))
						tileRight = heightMap[y][x + 1];
					
					if(height < tileUp && height < tileDown && height < tileLeft && height < tileRight) {
						sum += 1 + height;
						lowPointCoords.add(new Vector2i(x, y));
					}
				}
			}
			
			System.out.println("[ Part 1 ]: Sum of risk factors: " + sum);
		}
		
		{
			// part 2 (basin finding)
			
			List<Integer> sizes = new ArrayList<Integer>();
			
			for(Vector2i lowPoint : lowPointCoords) {
				sizes.add(getBasinSize(lowPoint, heightMap));
			}
			
			sizes = sizes.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
			
			int product = sizes.get(0) * sizes.get(1) * sizes.get(2);
			System.out.println("[ Part 2 ]: Product: " + product);
		}
		
	}
	
	private static int getBasinSize(Vector2i lowPoint, int[][] heightMap) {
		boolean[][] countedPositions = new boolean[heightMap.length][heightMap[0].length];
		return getBasinSizeRecursively(lowPoint.x, lowPoint.y, heightMap, countedPositions);
	}

	private static int getBasinSizeRecursively(int x, int y, int[][] heightMap, boolean[][] countedPositions) {
		int count = 1; // count this one
		countedPositions[y][x] = true;
		
		// x - 1 && x + 1 && y - 1 && y + 1
		int topCoord = y - 1;
		int bottomCoord = y + 1;
		int leftCoord = x - 1;
		int rightCoord = x + 1;
		
		if(y > 0) { 
			if(countedPositions[topCoord][x] == false && heightMap[topCoord][x] != 9) {
				count += getBasinSizeRecursively(x, topCoord, heightMap, countedPositions);
			}
		}
		
		if(y < (heightMap.length - 1)) {
			if(countedPositions[bottomCoord][x] == false && heightMap[bottomCoord][x] != 9) {
				count += getBasinSizeRecursively(x, bottomCoord, heightMap, countedPositions);
			}
		}
		
		if(x > 0) {
			if(countedPositions[y][leftCoord] == false && heightMap[y][leftCoord] != 9) {
				count += getBasinSizeRecursively(leftCoord, y, heightMap, countedPositions);
			}
		}
		
		if(x < (heightMap[y].length - 1)) {
			if(countedPositions[y][rightCoord] == false && heightMap[y][rightCoord] != 9) {
				count += getBasinSizeRecursively(rightCoord, y, heightMap, countedPositions);
			}
		}
		
		return count;
	}

	private static void printHeightMap(int[][] map) {
		for(int y = 0; y < map.length; y++) {
			for(int x = 0; x < map[y].length; x++) {
				System.out.print(map[y][x]);
			}
			
			System.out.println();
		}
	}
}

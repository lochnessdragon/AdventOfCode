package day13;

import java.util.ArrayList;
import java.util.List;

import utils.Pair;
import utils.Utils;

public class Day13 {

	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day13/data.txt");
		
		List<List<String>> grids = new ArrayList<List<String>>();
		List<Pair<Integer, Integer>> rowsColsScoreList = new ArrayList<Pair<Integer, Integer>>();
		List<String> currentGrid = new ArrayList<String>();
		
		int part1 = 0;
		for (String line : lines) {
			if (line.isEmpty()) {
				// flush grid
				if (currentGrid.size() > 0) {
					System.out.println(currentGrid);
					int rows = checkRows(currentGrid);
					int cols = checkCols(currentGrid);
					part1 += rows * 100;
					part1 += cols;
					grids.add(currentGrid);
					rowsColsScoreList.add(new Pair<Integer, Integer>(rows, cols));
					currentGrid = new ArrayList<String>();
				}
			} else {
				currentGrid.add(line);
			}
		}
		// flush grid
		if (currentGrid.size() > 0) {
			System.out.println(currentGrid);
			int rows = checkRows(currentGrid);
			int cols = checkCols(currentGrid);
			part1 += rows * 100;
			part1 += cols;
			grids.add(currentGrid);
			rowsColsScoreList.add(new Pair<Integer, Integer>(rows, cols));
		}
		
		System.out.println("Part 1: " + part1);
		
		int part2 = 0;
		for (int i = 0; i < grids.size(); i++) {
			List<String> grid = grids.get(i);
			Pair<Integer, Integer> rowsCols = rowsColsScoreList.get(i);
			
			for (int row = 0; row < grid.size(); row++) {
				if (row + 1 == rowsCols.getLeft())
					continue;
				
				if (diffReflectedRow(grid, row) == 1)
					part2 += (row + 1) * 100;
			}
			
			for (int col = 0; col < grid.get(0).length(); col++) {
				if (col + 1 == rowsCols.getRight())
					continue;
				
				if (diffReflectedCol(grid, col) == 1)
					part2 += col + 1;
			}
		}
		
		System.out.println("Part 2: " + part2);
	}
	
	public static int checkRows(List<String> grid) {
		int result = 0;
		for (int i = 0; i < grid.size() - 1; i++) {
			if (grid.get(i).equals(grid.get(i + 1))) {
				// radiate out from that
				boolean works = true;
				int j = 1;
				while (i + j + 1 < grid.size() && (i - j) >= 0) {
					if(!grid.get(i - j).equals(grid.get(i + j + 1))) {
						works = false;
						break;
					}
					
					j++;
				}
				
				if (works) {
					result = i + 1;
					break;
				}
			}
		}
		
		return result;
	}
	
	public static int checkCols(List<String> grid) {
		int result = 0;
		
		for (int i = 0; i < grid.get(0).length() - 1; i++) {
			if (checkColsMatch(grid, i, i + 1)) {
				boolean works = true;
				int j = 1;
				
				while (i + j + 1 < grid.get(0).length() && (i - j) >= 0) {
					if (!checkColsMatch(grid, i + j + 1, i - j)) {
						works = false;
						break;
					}
					j++;
				}
				
				if (works) {
					result = i + 1;
					break;
				}
			}
		}
		
		return result;
	}
	
	public static boolean checkColsMatch(List<String> grid, int col1, int col2) {
		for (int i = 0; i < grid.size(); i++) {
			String line = grid.get(i);
			if (line.charAt(col1) != line.charAt(col2))
				return false;
		}
		
		return true;
	}
	
	public static int diffReflectedRow(List<String> grid, int row) {
		int delta = 0;
		int differences = 0;
		while (row - delta >= 0 && (row + delta + 1) < grid.size()) {
			for (int i = 0; i < grid.get(0).length(); i++) {
				if (grid.get(row - delta).charAt(i) != grid.get(row + delta + 1).charAt(i)) {
					differences++;
				}
			}
			
			delta++;
		}
			
		return differences;
	}
	
	public static int diffReflectedCol(List<String> grid, int col) {
		int delta = 0;
		int differences = 0;
		
		while (col - delta >= 0 && (col + delta + 1) < grid.get(0).length()) {
			for (int i = 0; i < grid.size(); i++) {
				String line = grid.get(i);
				if (line.charAt(col - delta) != line.charAt(col + delta + 1)) {
					differences++;
				}
			}
			
			delta++;
		}
		
		return differences;
	}

}

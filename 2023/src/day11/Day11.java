package day11;

import java.util.ArrayList;
import java.util.List;

import utils.Utils;

// part 2: 728017748

public class Day11 {

	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day11/data.txt");
		List<Point> galaxies = new ArrayList<Point>();
		
		List<Integer> emptyRows = new ArrayList<Integer>();
		List<Integer> emptyCols = new ArrayList<Integer>();
		
		for (int x = 0; x < lines.get(0).length(); x++) {
			emptyCols.add(x);
		}
		
		for (int y = 0; y < lines.size(); y++) {
			String line = lines.get(y);
			boolean rowEmpty = true;
			for (int x = 0; x < line.length(); x++) {
				char c = line.charAt(x);
				
				if (c == '#') {
					galaxies.add(new Point(x, y));
					rowEmpty = false;
					if (emptyCols.contains(x)) {
						emptyCols.remove(emptyCols.indexOf(x));
					}
				}
			}
			
			if (rowEmpty) {
				emptyRows.add(y);
			}
		}
		
		// lets expand those rows / columns
		for (Point galaxy : galaxies) {
			int xInc = countLessThan(emptyCols, (int) galaxy.x);
			int yInc = countLessThan(emptyRows, (int) galaxy.y);
			
			galaxy.x += ((long) xInc) * 999999; // change these two lines by commenting out the multiplication symbol (and everything afterwards) to switch to part 1
			galaxy.y += ((long) yInc) * 999999;
		}
		
		long distances = 0;
		for (int i = 0; i < galaxies.size(); i++) {
			for (int j = i + 1; j < galaxies.size(); j++) {
				distances += galaxies.get(i).getTaxicabDist(galaxies.get(j));
			}
		}
		
		System.out.println("Part (1/2): " + distances);
	}
	
	public static int countLessThan(List<Integer> nums, int max) {
		return (int) nums.stream().filter(value -> value < max).count();
	}

}

class Point {
	public long x;
	public long y;
	
	public Point(long x, long y) {
		super();
		this.x = x;
		this.y = y;
	}

	public long getTaxicabDist(Point other) {
		long deltaX = Math.abs(other.x - this.x);
		long deltaY = Math.abs(other.y - this.y);
		
		return deltaX + deltaY;
	}
}
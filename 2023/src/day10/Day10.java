package day10;

import java.util.ArrayList;
import java.util.List;

import utils.Pair;
import utils.Utils;

class Point {
	public int x;
	public int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point newInDirection(Grid.EntryDir dir) {
		switch(dir) {
		case TOP:
			return new Point(x, y - 1);
		case BOTTOM:
			return new Point(x, y + 1);
		case LEFT:
			return new Point(x - 1, y);
		case RIGHT:
			return new Point(x + 1, y);
		default:
			System.out.println("Wrong point thing called");
			return new Point(0, 0);
		}
	}
	
	public Point newFromDirection(Grid.EntryDir dir) {
		switch(dir) {
		case TOP:
			return new Point(x, y + 1);
		case BOTTOM:
			return new Point(x, y - 1);
		case LEFT:
			return new Point(x + 1, y);
		case RIGHT:
			return new Point(x - 1, y);
		default:
			System.out.println("Wrong point thing called");
			return new Point(0, 0);
		}
	}
	
	public boolean equals(Point other) {
		if (other.x == this.x && other.y == this.y)
			return true;
		return false;
	}
	
	public String toString() {
		return "Point (" + x + ", " + y + ")";
	}
	
	public boolean containsNeg() {
		if (this.x < 0 || this.y < 0)
			return true;
		return false;
	}
}

public class Day10 {

	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day10/data.txt");
		
		Grid pipeGrid = new Grid(lines);
		
		System.out.println("Part 1: " + pipeGrid.getLongestDistance());
		System.out.println("Part 2: " + pipeGrid.getInternalArea());
	}

}

class Grid {
	
	static enum EntryDir {
		TOP,
		BOTTOM,
		LEFT,
		RIGHT,
	}
	
	char[][] pipes;
	
	int height;
	int width;
	
	Point startingLoc;
	
	public Grid(List<String> lines) {
		height = lines.size();
		width = lines.get(0).length();
		pipes = new char[lines.size()][];
		
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			char[] pipesInLine = line.toCharArray();
			pipes[i] = pipesInLine;
			for (int j = 0; j < pipesInLine.length; j++) {
				if (pipesInLine[j] == 'S') {
					startingLoc = new Point(j, i);
				}
			}
			
		}
	}
	
	public int getLongestDistance() {
		int longestDist = 1;
		
		List<Point> locations = new ArrayList<Point>();
		List<EntryDir> directions = new ArrayList<EntryDir>();
		
		for (EntryDir direction : EntryDir.values()) {
			Point testLoc = startingLoc.newInDirection(direction);
			if(testLoc.containsNeg())
				continue;
			if (connects(direction, testLoc)) {
				locations.add(testLoc);
				directions.add(direction);
			}
		}
		
		System.out.println("Next locations: " + locations);
		System.out.println("From directions: " + directions);
		
		while (!locations.get(0).equals(locations.get(1))) {
			for (int i = 0; i < locations.size(); i++) {
				Point location = locations.get(i);
				EntryDir direction = directions.get(i);
				Pair<EntryDir, Point> nextPair = getNext(direction, location);
				locations.set(i, nextPair.getRight());
				directions.set(i, nextPair.getLeft());
				
				System.out.println(nextPair.getRight());
			}
			System.out.println("");
			longestDist++;
		}
		
		return longestDist;
	}
	
	public int getInternalArea() {
		char[][] loopOnly = new char[pipes.length][];
		
		for (int i = 0; i < pipes.length; i++) {
			loopOnly[i] = new char[pipes[0].length];
			for (int j = 0; j < loopOnly[i].length; j++) {
				loopOnly[i][j] = '.';
			}
		}
		
		Point location = startingLoc;
		EntryDir direction = EntryDir.TOP;
		EntryDir direction2 = EntryDir.BOTTOM; // LEFT, RIGHT
		
		for (EntryDir testDir : EntryDir.values()) {
			System.out.println(testDir);
			Point testLoc = startingLoc.newInDirection(testDir);
			if(testLoc.containsNeg())
				continue;
			if (connects(testDir, testLoc) && location.equals(startingLoc)) {
				location = testLoc;
				direction = testDir;
			} else {
				direction2 = testDir;
			}
		}
		
		loopOnly[startingLoc.y][startingLoc.x] = pipes[startingLoc.y][startingLoc.x];
		if (direction == EntryDir.TOP && direction2 == EntryDir.BOTTOM) {
			loopOnly[startingLoc.y][startingLoc.x] = '|';
		} else if (direction == EntryDir.LEFT && direction2 == EntryDir.RIGHT) {
			loopOnly[startingLoc.y][startingLoc.x] = '-';
		} else if (direction == EntryDir.BOTTOM && direction2 == EntryDir.RIGHT) {
			loopOnly[startingLoc.y][startingLoc.x] = 'F';
		} else if (direction == EntryDir.BOTTOM && direction2 == EntryDir.LEFT) {
			loopOnly[startingLoc.y][startingLoc.x] = '7';
		} else if (direction == EntryDir.TOP && direction2 == EntryDir.RIGHT) {
			loopOnly[startingLoc.y][startingLoc.x] = 'L';
		} else if (direction == EntryDir.TOP && direction2 == EntryDir.LEFT) {
			loopOnly[startingLoc.y][startingLoc.x] = 'J';
		}
		
		System.out.println("Next location: " + location);
		System.out.println("From direction: " + direction);
		
		while (!location.equals(startingLoc)) {
			loopOnly[location.y][location.x] = pipes[location.y][location.x];
			Pair<EntryDir, Point> nextPair = getNext(direction, location);
			location = nextPair.getRight();
			direction = nextPair.getLeft();
		}
		
		displayGrid(loopOnly);
		
		int area = 0;
		
		for (int y = 0; y < loopOnly.length; y++) {
			boolean canCount = false;
			char prevPipeChar = '|';
			for (int x = 0; x < loopOnly[y].length; x++) {
				char current = loopOnly[y][x];
				if (current == '.') {
					if (canCount) {
						System.out.println("Counted: (" + x + ", " + y + ")");
						area++;
					}
				} else {
					if (current == '|' || current == 'F' || current == 'L') {
						// easy decision
						canCount = !canCount;
						prevPipeChar = current;
					} else if (current == 'J' && prevPipeChar == 'L') {
						canCount = !canCount;
						prevPipeChar = current;
					} else if (current == '7' && prevPipeChar == 'F') {
						canCount = !canCount;
						prevPipeChar = current;
					}
				}
				
			}
		}
		
		return area;
	}
	
	public void displayGrid(char[][] grid) {
		for (int i = 0; i < grid.length; i ++) {
			System.out.println(grid[i]);
		}
	}
	
	public boolean connects(EntryDir outFromLocation, Point location) {
		char pipe = pipes[location.y][location.x];
		
		boolean doesConnect = false;
		
		switch(outFromLocation) {
		case TOP:
			if (pipe == '|' || pipe == '7' || pipe == 'F')
				doesConnect = true;
			break;
		case BOTTOM:
			if (pipe == '|' || pipe == 'L' || pipe == 'J')
				doesConnect = true;
			break;
		case LEFT:
			if (pipe == '-' || pipe == 'L' || pipe == 'F')
				doesConnect = true;
			break;
		case RIGHT:
			if (pipe == '-' || pipe == 'J' || pipe == '7')
				doesConnect = true;
			break;
		}
		
		return doesConnect;
	}
	
	public Pair<EntryDir, Point> getNext(EntryDir fromPointDir, Point fromPoint) {
		char pipe = pipes[fromPoint.y][fromPoint.x];
		
		EntryDir nextDir;
		
		switch(pipe) {
		case '|':
			if (fromPointDir == EntryDir.BOTTOM) {
				nextDir = EntryDir.BOTTOM;
			} else {
				nextDir = EntryDir.TOP;
			}
			break;
		case '-':
			if (fromPointDir == EntryDir.LEFT) {
				nextDir = EntryDir.LEFT;
			} else {
				nextDir = EntryDir.RIGHT;
			}
			break;
		case 'L':
			if (fromPointDir == EntryDir.BOTTOM) {
				nextDir = EntryDir.RIGHT;
			} else {
				nextDir = EntryDir.TOP;
			}
			break;
		case 'J':
			if (fromPointDir == EntryDir.BOTTOM) {
				nextDir = EntryDir.LEFT;
			} else {
				nextDir = EntryDir.TOP;
			}
			break;
		case '7':
			if (fromPointDir == EntryDir.TOP) {
				nextDir = EntryDir.LEFT;
			} else {
				nextDir = EntryDir.BOTTOM;
			}
			break;
		case 'F':
			if (fromPointDir == EntryDir.TOP) {
				nextDir = EntryDir.RIGHT;
			} else {
				nextDir = EntryDir.BOTTOM;
			}
			break;
		default:
			System.out.println("Found non-piped pipe");
			nextDir = EntryDir.TOP;
		}
		
		Point nextPoint = fromPoint.newInDirection(nextDir);
		
		return new Pair<EntryDir, Point>(nextDir, nextPoint);
	}
}

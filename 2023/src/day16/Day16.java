package day16;

import java.util.ArrayList;
import java.util.List;

import utils.Utils;
import utils.Vec2;

public class Day16 {
	public static void main(String[] args) {
		char[][] grid = Utils.readFileArr("src/day16/data.txt");
		
		// laser
		int part1 = getEnergized(grid, new Vec2(0, 0), Going.RIGHT);
		
		System.out.println("Part 1: " + part1);
		
		// find the largest
		int part2 = 0;
		for (int x = 0; x < grid[0].length; x++) {
			int potential = getEnergized(grid, new Vec2(x, 0), Going.DOWN);
			if (potential > part2)
				part2 = potential;
			
			potential = getEnergized(grid, new Vec2(x, grid.length - 1), Going.UP);
			if (potential > part2)
				part2 = potential;
		}
		
		for (int y = 0; y < grid.length; y++) {
			int potential = getEnergized(grid, new Vec2(0, y), Going.RIGHT);
			if (potential > part2)
				part2 = potential;
			
			potential = getEnergized(grid, new Vec2(grid[y].length - 1, y), Going.LEFT);
			if (potential > part2)
				part2 = potential;
		}
		
		System.out.println("Part 2: " + part2);
	}
	
	public static int getEnergized(char[][] grid, Vec2 startPos, Going startDir) {
		boolean[][] energized = new boolean[grid.length][];
		for (int i = 0; i < grid.length; i++) {
			energized[i] = new boolean[grid[i].length];
		}
		
		traverseGrid(grid, energized, startPos, startDir);
		
		int count = 0;
		for (int i = 0; i < energized.length; i++) {
			for (int j = 0; j < energized[i].length; j++) {
				if (energized[i][j])
					count++;
			}
		}
		
		return count;
	}
	
	public static void traverseGrid(char[][] grid, boolean[][] energized, Vec2 startPos, Going startDir) {
		List<Laser> lasers = new ArrayList<Laser>();
		lasers.add(new Laser(startPos, startDir));
		
		int iter = 0; // I don't need to write proper cycle detection, right?
		while (lasers.size() > 0 && iter < 100000) {
			List<Laser> newLasers = new ArrayList<Laser>();
			for (Laser laser : lasers) {
				// decisions based on the mirror piece
				char mirror = grid[laser.pos.y][laser.pos.x];
				
				if (mirror == '/') {
					switch (laser.direction) {
					case RIGHT:
						laser.direction = Going.UP;
						break;
					case LEFT:
						laser.direction = Going.DOWN;
						break;
					case UP:
						laser.direction = Going.RIGHT;
						break;
					case DOWN:
						laser.direction = Going.LEFT;
						break;
					}
				} else if (mirror == '\\') {
					switch (laser.direction) {
					case RIGHT:
						laser.direction = Going.DOWN;
						break;
					case LEFT:
						laser.direction = Going.UP;
						break;
					case UP:
						laser.direction = Going.LEFT;
						break;
					case DOWN:
						laser.direction = Going.RIGHT;
						break;
					}
				} else if (mirror == '|' && (laser.direction == Going.RIGHT || laser.direction == Going.LEFT)) {
					if (energized[laser.pos.y][laser.pos.x])
						laser.remove = true; // already hit this
					laser.direction = Going.UP;
					Vec2 nextPos = new Vec2(laser.pos.x, laser.pos.y + 1);
					newLasers.add(new Laser(nextPos, Going.DOWN));
				} else if (mirror == '-' && (laser.direction == Going.UP || laser.direction == Going.DOWN)) {
					if (energized[laser.pos.y][laser.pos.x])
						laser.remove = true; // already hit this
					laser.direction = Going.LEFT;
					Vec2 nextPos = new Vec2(laser.pos.x + 1, laser.pos.y);
					newLasers.add(new Laser(nextPos, Going.RIGHT));
				}
				
				// we are energized bf move
				energized[laser.pos.y][laser.pos.x] = true;
				
				// move the direction
				switch (laser.direction) {
				case RIGHT:
					laser.pos.x++;
					break;
				case LEFT:
					laser.pos.x--;
					break;
				case UP:
					laser.pos.y--;
					break;
				case DOWN:
					laser.pos.y++;
					break;
				}
			}
			lasers.addAll(newLasers);
			
			lasers.removeIf((laser) -> (laser.pos.x < 0 || laser.pos.y < 0 || laser.pos.x >= grid[0].length || laser.pos.y >= grid.length));
			lasers.removeIf((laser) -> laser.remove);
			iter++;
		}
	}
	
	public static void printEnergized(boolean[][] energized) {
		for (boolean[] subArr : energized) {
			for (boolean energy : subArr) {
				if (energy) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
	}
}

class Laser {
	public Going direction;
	public Vec2 pos;
	public boolean remove = false;
	
	public Laser(Vec2 pos, Going direction) {
		this.pos = pos;
		this.direction = direction;
	}
}

enum Going {
	RIGHT,
	LEFT,
	UP,
	DOWN
}
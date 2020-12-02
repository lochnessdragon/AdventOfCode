package day6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		List<Integer[]> coords = new ArrayList<Integer[]>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/day6/test.txt")));
			
			String line;
			String[] xy;
			while((line = reader.readLine()) != null) {
				
				xy = line.split(",");
				Integer[] coord = {Integer.parseInt(xy[0]), Integer.parseInt(xy[1].replaceAll(" ", ""))};
				coords.add(coord);
				
			}
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[][] grid;
		int highX = 0;
		int highY = 0;
		
		for(Integer[] coord : coords) {
			if(coord[1] > highX) {
				highX = coord[1];
			}
			if(coord[0] > highY) {
				highY = coord[0];
			}
		}
		
		grid = new String[highX + 2][highY + 2];
		System.out.println(highX + " " + highY);
		
		String lettersStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] letters = lettersStr.toCharArray();
		
		int letter = 0;
		int count = 1;
		int radius = 100;
		int dist;
		
		for(Integer[] coord : coords) {
			int x = coord[1];
			int y = coord[0];
			grid[x][y] = Character.toString(letters[letter]) + "" + count;
			
			for(int i = x - radius; i < x + radius; i++) {
				for(int j = y - radius; j < y + radius; j++) {
					dist = (i - x) + j - y;
				}
			}
			
			letter++;
			if(letter > 25) {
				letter = 0;
				count++;
			}
		}
		
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j] == null) {
					grid[i][j] = ".";
				}
			}
		}
		
//		for(int i = 0; i < grid.length; i++) {
//			for(int j = 0; j < grid.length; j++) {
//				System.out.print(grid[i][j]);
//			}
//			System.out.println("");
//		}
		
//		for (int i = 0; i < grid.length; i++) {
//		    for (int j = 0; j < grid[i].length; j++) {
//		        System.out.print(grid[i][j] + " ");
//		    }
//		    System.out.println();
//		}
		

	}

}

package day3;

import java.util.List;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource inputFile = new Resource("src/day3/input.txt");
		
		List<String> lines = inputFile.read();
		{
			int x = 0;
			int y = 0;
		
			int slopeX = 3;
			int slopeY = 1;
		
			int treesEncountered = 0;
		
			for(String line : lines) {
				if((line.charAt(x % line.length()) == '#')) {
					// we hit a tree.
					treesEncountered++;
				}
			
				x += slopeX;
				y -= slopeY;
			}
		
			System.out.println("[Part 1]: Trees Encountered: " + treesEncountered);
		}
		
		// part 2 
		{
			int[] slopes = { 1, 1, 3, 1, 5, 1, 7, 1, 1, 2};
			int checksum = 1;
			
			for(int i = 0; i < slopes.length/2; i++) {
				int x = 0;
			
				int slopeX = slopes[(i*2)];
				int slopeY = slopes[(i*2)+1];
			
				int treesEncountered = 0;
			
				for(int y = 0; y < lines.size(); y+=slopeY) {
				String line = lines.get(y);
					if((line.charAt(x % line.length()) == '#')) {
						// we hit a tree
						treesEncountered++;
					}
					x+=slopeX;
				}
				
				System.out.println("[Part 2]: Trees Encountered: " + treesEncountered);
				checksum *= treesEncountered;
			}
			
			System.out.println("[Part 2]: Checksum: " + checksum);
		}
	}

}

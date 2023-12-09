package day2;

import java.util.List;

import utils.Utils;

public class Day2 {

	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day2/data.txt");
		
		int part1 = 0;
		int part2 = 0;
		GameSet max = new GameSet(12, 13, 14);
		
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			int headerEnd = line.indexOf(':');
			String removedHeader = line.substring(headerEnd + 1);
			String[] sets = removedHeader.split(";");
			boolean valid = true;
			GameSet minRequired = new GameSet(0, 0, 0);
			for (String set : sets) {
				GameSet gameSet = new GameSet(set);
				if(!gameSet.isPossible(max)) {
					valid = false;
				}
				minRequired = minRequired.max(gameSet);
			}
			if(valid)
				part1 += i + 1;
			
			part2 += minRequired.power();
		}
		
		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);

	}

}
package day4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import utils.Utils;

public class Day4 {
	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day4/data.txt");
		
		int part1 = 0;
		int part2 = 0;
		
		List<Integer> numberOfWinning = new ArrayList<Integer>();
		for (String line : lines) {
			System.out.println(line);
			String[] parts = line.split(":")[1].split("\\|");
			List<Integer> winningNums = new ArrayList<Integer>();
			for (String winningNum : parts[0].split(" ")) {
				if(winningNum != "")
					winningNums.add(Integer.parseInt(winningNum));
			}
			
			List<Integer> heldNums = new ArrayList<Integer>();
			for (String heldNum : parts[1].split(" ")) {
				if (heldNum != "")
					heldNums.add(Integer.parseInt(heldNum));
			}
			
			System.out.println("Winning Nums: " + winningNums.toString() + " Held Nums: " + heldNums.toString());
			
			int exponent = -1;
			for (int num : winningNums) {
				if(heldNums.contains(num)) {
					exponent++;
				}
			}
			
			if(exponent >= 0) {
				System.out.println("Found: " + (exponent + 1) + " winning nums.");
				int score = (int) Math.pow(2, exponent);
				part1 += score;
			}
			numberOfWinning.add(exponent + 1);
		}
		
		int[] copiesSpawned = new int[numberOfWinning.size()];
		
		for (int i = numberOfWinning.size() - 1;  i >= 0; i--) {
			int score = numberOfWinning.get(i);
			int cardsSpawnedFromThis = 0;
			System.out.println("Score: " + score);
			for (int j = 1; j <= score; j++) {
				cardsSpawnedFromThis += copiesSpawned[i + j];
			}
			copiesSpawned[i] = cardsSpawnedFromThis + score;
		}
		
		part2 = IntStream.of(copiesSpawned).sum() + numberOfWinning.size();
		
		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}
}

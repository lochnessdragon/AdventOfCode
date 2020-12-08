package day7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource input = new Resource("src/day7/input.txt");
		List<String> lines = input.read();
		
		Map<String, List<String>> bagsMap = new HashMap<String, List<String>>();
		
		for(String line : lines) {
			String[] parts = line.split(" contain ");
			parts[0] = parts[0].replaceAll("s$", "");
			System.out.println("Parts[0]: " + parts[0]);
			
			String[] bagsContained = parts[1].replace('.', ' ').replaceAll("[0-9]", "").split(", ");
			for(int i = 0; i < bagsContained.length; i++) {
				String bag = bagsContained[i];
				
				bag = bag.trim();
				bag = bag.replaceAll("s$", "");
				
				bagsContained[i] = bag;
				
				System.out.println(bag);
			}
			
			for(String bag : bagsContained) {
				List<String> bagsSoFar = bagsMap.getOrDefault(bag, new ArrayList<String>());
				
				bagsSoFar.add(parts[0]);
				
				bagsMap.put(bag, bagsSoFar);
			}
		}
		
		List<String> bagColors = recurseBags("shiny gold bag", bagsMap, new ArrayList<String>());
		
		System.out.println();
		System.out.println("-----------------------------------------");
		System.out.println("[Part 1]: Bag count: " + bagColors.size());
		System.out.println("-----------------------------------------");
		System.out.println();
		
		// part 2 
		Map<String, Map<String, Integer>> bagsCount = new HashMap<String, Map<String, Integer>>();
		
		for(String line : lines) {
			String[] parts = line.split(" contain ");
			parts[0] = parts[0].replaceAll("s$", "");
			System.out.println("Parts[0]: " + parts[0]);
			
			String[] bagsContained = parts[1].replace('.', ' ').split(", ");
			for(int i = 0; i < bagsContained.length; i++) {
				String bag = bagsContained[i];
				
				bag = bag.trim();
				bag = bag.replaceAll("s$", "");
				
				bagsContained[i] = bag;
				
				System.out.println(bag);
			}
			
			Map<String, Integer> bagCounts = bagsCount.getOrDefault(parts[0], new HashMap<String, Integer>());
			
			for(String bag : bagsContained) {
				if(bag.equals("no other bag")) {
					bagCounts = null;
					break;
				}
				
				System.out.println(bag);
				String[] kv = bag.split(" ", 2);
				bagCounts.put(kv[1], Integer.parseInt(kv[0]));
			}
			
			bagsCount.put(parts[0], bagCounts);
		}
		
		int bagCount = countBagsRecursively("shiny gold bag", bagsCount);
		
		System.out.println();
		System.out.println("-----------------------------------------");
		System.out.println("[Part 2]: Bag count: " + bagCount);
		System.out.println("-----------------------------------------");
		System.out.println();
		
	}

	private static int countBagsRecursively(String key, Map<String, Map<String, Integer>> bagsCount) {
		int count = 0;
		
		Map<String, Integer> bags = bagsCount.get(key);
		
		if(bags != null) {
			for(String bag : bags.keySet()) {
				int number = bags.get(bag);
				
				count += number * (countBagsRecursively(bag, bagsCount) + 1);
			}
		}
		
		System.out.println("Key: " + key + " Count: " + count);
		
		return count;
	}

	private static List<String> recurseBags(String key, Map<String, List<String>> bagsMap, List<String> additiveBags) {
		List<String> bags = bagsMap.get(key);
		if(bags != null) {
			for(String bag : bags) {
				if(!additiveBags.contains(bag)) {
					additiveBags.add(bag);
				}
				additiveBags = recurseBags(bag, bagsMap, additiveBags);
			}
		}
		
		return additiveBags;
	}

}

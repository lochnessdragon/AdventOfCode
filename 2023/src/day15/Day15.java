package day15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;

public class Day15 {

	public static void main(String[] args) {
		String line = Utils.readFileElimNl("src/day15/data.txt");
		String[] parts = line.split(",");
		
		int part1 = 0;
		Map<Integer, List<Lens>> lenses = new HashMap<Integer, List<Lens>>();
		for (String part : parts) {
			part1 += hash(part);
			
			if (part.endsWith("-")) {
				// lens remove operation
				String deleteLensName = part.substring(0, part.length() - 1);
				int boxnum = hash(deleteLensName);
				if (lenses.containsKey(boxnum)) {
					List<Lens> boxLenses = lenses.get(boxnum);
					boxLenses.removeIf((lens) -> lens.name.equals(deleteLensName));
					lenses.put(boxnum, boxLenses);
				}
			} else {
				// lens set operation
				String[] nameStr = part.split("=");
				String lensName = nameStr[0];
				int strength = Integer.parseInt(nameStr[1]);
				int boxnum = hash(lensName);
				
				List<Lens> boxLenses = lenses.getOrDefault(boxnum, new ArrayList<Lens>());
				int replaceIdx = -1;
				for (int i = 0; i < boxLenses.size(); i++) {
					Lens lens = boxLenses.get(i);
					if (lens.name.equals(lensName)) {
						// already has this lens, replace it
						replaceIdx = i;
						break;
					}
				}
				
				Lens newLens = new Lens(lensName, strength);
				if (replaceIdx > -1) {
					boxLenses.set(replaceIdx, newLens);
				} else {
					boxLenses.add(newLens);
				}
				
				lenses.put(boxnum, boxLenses);
			}
		}
		
		int part2 = 0;
		for (int i = 0; i < 256; i++) {
			if (lenses.containsKey(i)) {
				List<Lens> boxLenses = lenses.get(i);
				for (int j = 0; j < boxLenses.size(); j++) {
					part2 += (i + 1) * (j + 1) * boxLenses.get(j).strength; 
				}
			}
		}
		
		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}
	
	public static int hash(String str) {
		int value = 0;
		for (char c : str.toCharArray()) {
			value += c;
			value *= 17;
			value %= 256;
		}
		
		return value;
	}

}

class Lens {
	public String name;
	public int strength;
	
	public Lens(String name, int strength) {
		super();
		this.name = name;
		this.strength = strength;
	}
}

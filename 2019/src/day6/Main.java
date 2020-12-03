package day6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import day5.Utils;

public class Main {

	public static void main(String[] args) {
		List<String> input = Utils.loadFile("src/day6/puzzle.txt");
		//int directOrbits = input.size();
		//int indirectOrbits = 0;
		Map<String, List<String>> space = new HashMap<String, List<String>>();
		
		String[] orbitModel;
		for(String line : input) {
			orbitModel = line.split("\\)");
			List<String> batch = space.getOrDefault(orbitModel[0], new ArrayList<String>());
			batch.add(orbitModel[1]);
			space.put(orbitModel[0], batch);
		}
		
		// part 1
		Map<String, Integer> orbitMap = new HashMap<String, Integer>();
		
		String COM = "COM";
		orbitMap.put(COM, 0);
		//space.keySet().forEach((value) -> {System.out.println(value);});
		
		orbitMap = flattenToList(orbitMap, space, COM);
		
		int total = 0;
		
		for(Integer number : orbitMap.values()) {
			total += number;
		}
		
		System.out.println("[Part 1]: Checksum: " + total);
		
		// part 2
		Map<String, String> part2Map = new HashMap<String, String>();
		
		for(String line : input) {
			orbitModel = line.split("\\)");
			part2Map.put(orbitModel[1], orbitModel[0]);
		}
		
		Map<String, Integer> santaOrbit = new HashMap<String, Integer>();
		String nextOrbit = "SAN";
		
		int orbits = 0;
		
		while(!nextOrbit.equals("COM")) {
			nextOrbit = part2Map.get(nextOrbit);
			santaOrbit.put(nextOrbit, orbits);
			orbits++;
			System.out.println("Next Orbit: " + nextOrbit);
		}
		
		String commonAncestor = "YOU";
		
		int orbitsRequired = -1;
		
		while(!santaOrbit.keySet().contains(commonAncestor)) {
			commonAncestor = part2Map.get(commonAncestor);
			orbitsRequired++;
		}
		
		orbitsRequired += santaOrbit.get(commonAncestor);
		
		System.out.println("[Part 2]: Orbits Required: " + orbitsRequired);
		
	}
	
	public static Map<String, Integer> flattenToList(Map<String, Integer> orbitMap, Map<String, List<String>> space, String center) {
		System.out.println(center);
		List<String> bodies = space.get(center);
		if(bodies == null)
			return orbitMap;
		
		for(String body : bodies) {
			orbitMap.put(body, orbitMap.get(center) + 1);
			orbitMap = flattenToList(orbitMap, space, body);
		}
		
		return orbitMap;
	}

}

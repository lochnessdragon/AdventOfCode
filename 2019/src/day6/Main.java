package day6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import day5.Utils;

public class Main {

	public static void main(String[] args) {
		List<String> input = Utils.loadFile("src/day6/input.txt");
		int directOrbits = input.size();
		int indirectOrbits = 0;
		Map<String, Planet> space = new HashMap<String, Planet>();
		
		String[] orbitModel;
		for(String line : input) {
			orbitModel = line.split(")");
		}

	}

}

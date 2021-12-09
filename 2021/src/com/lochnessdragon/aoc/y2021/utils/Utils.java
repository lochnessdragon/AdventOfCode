package com.lochnessdragon.aoc.y2021.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	public static String readFile(String filename) {
		String data = "";
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			while(reader.ready()) {
				data += reader.readLine() + '\n';
			}
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	public static List<String> getAllPermutationsOfStr(String str) {
		List<String> result = new ArrayList<String>();
		
		return result;
	}
}

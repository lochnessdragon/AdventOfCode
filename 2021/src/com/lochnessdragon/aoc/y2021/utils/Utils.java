package com.lochnessdragon.aoc.y2021.utils;

import java.io.BufferedReader;
import java.io.FileReader;

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
}

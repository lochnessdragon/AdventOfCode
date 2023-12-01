package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
	public static List<String> readFile(String filename) {
		List<String> lines;
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(filename));
			lines = fileReader.lines().collect(Collectors.toList());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			lines = new ArrayList<String>();
		}
		return lines;
	}
}

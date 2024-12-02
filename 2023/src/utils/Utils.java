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
	
	public static String readFileElimNl(String filename) {
		// read file elimnating newlines
		String result = "";
		try {
			BufferedReader file = new BufferedReader(new FileReader(filename));
			while (file.ready()) {
				result += file.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static char[][] readFileArr(String filename) {
		// had I written this earlier, I would've used it in more solutions
		List<String> lines = readFile(filename);
		
		char[][] buffer = new char[lines.size()][];
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			buffer[i] = line.toCharArray();
		}
		
		return buffer;
	}
	
	public static int countCharOccurences(String str, char character) {
		int total = 0;
		for (char c : str.toCharArray()) {
			if (c == character)
				total++;
		}
		return total;
	}
	
	public static char[] copyAndReplaceChar(char[] buffer, int i, char newChar) {
		char[] value = buffer.clone();
		value[i] = newChar;
		return value;
	}
}

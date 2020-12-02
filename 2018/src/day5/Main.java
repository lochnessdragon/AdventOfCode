package day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		String line = "Uh oh...";
		char[] characters;
		List<String> differentLines = new ArrayList<String>();
		int shortestLength = 0;

		String differentCharacters1 = "abcdefghijklmnopqrstuvwxyz";

		char[] differentCharacters = differentCharacters1.toCharArray();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/day5/input1.txt")));
			line = reader.readLine();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		shortestLength = line.length();

		// Remove different polymer threads!!!
		for (char differentChar : differentCharacters) {
			String line1 = line;
			line1 = line1.replaceAll(Character.toString(differentChar), "");
			line1 = line1.replaceAll(Character.toString(Character.toUpperCase(differentChar)), "");
			differentLines.add(line1);
		}

		// Fully react polymer

		for (String line2 : differentLines) {

			boolean repeat = true;

			while (repeat == true) {

				characters = line2.toCharArray();

				char oldChar = ' ';
				char newChar = ' ';

				for (int i = 0; i < characters.length; i++) {
					oldChar = newChar;
					newChar = characters[i];
					if ((oldChar != newChar) && (Character.toLowerCase(oldChar) == Character.toLowerCase(newChar))) {
						characters[i] = ' ';
						characters[i - 1] = ' ';
						break;
					}
					if (i + 1 == characters.length) {
						repeat = false;
					}
				}

				line2 = String.copyValueOf(characters);
				line2 = line2.replaceAll("\\s", "");
			}
			
			if(line2.length() < shortestLength) {
				shortestLength = line2.length();
			}
			
		}

		// try {
		// BufferedWriter writer = new BufferedWriter(new FileWriter(new
		// File("src/day5/output1.txt")));
		// writer.write(line);
		// writer.close();
		// } catch(Exception e) {
		// e.printStackTrace();
		// }

		System.out.println(shortestLength);

	}

}

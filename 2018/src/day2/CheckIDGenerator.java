package day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckIDGenerator {

	private int checkSum;
	private int letters2Times;
	private int letters3Times;
	private String lettersInIds;
	private Map<Character, Integer> listOfLetters;
	List<String> lines;

	public CheckIDGenerator() {
		letters2Times = 0;
		letters3Times = 0;
		listOfLetters = new HashMap<Character, Integer>();
		lines = new ArrayList<String>();
	}

	public void generate() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/day2/input1.txt")));
			String line;
			char[] letters;
			boolean twice;
			boolean thrice;
			while ((line = br.readLine()) != null) {
				listOfLetters.clear();
				twice = false;
				thrice = false;

				System.out.println(line);
				letters = line.toCharArray();

				for (int i = 0; i < letters.length; i++) {
					System.out.println(letters[i]);
					try {
						int batch = listOfLetters.get(letters[i]);
						batch += 1;
						System.out.println("Letter: " + letters[i] + " has been repeated " + batch);
						listOfLetters.put(letters[i], batch);
					} catch (NullPointerException e) {
						System.out.println("This character has not been assigned to the table yet!");
						int batch = 1;
						listOfLetters.put(letters[i], batch);
					}
				}

				for (char character : listOfLetters.keySet()) {
					int batch = listOfLetters.get(character);
					if (batch == 2 && twice == false) {
						letters2Times += 1;
						twice = true;
					} else if (batch == 3 && thrice == false) {
						letters3Times += 1;
						thrice = true;
					}
				}
				lines.add(line);

			}
			br.close();
			checkSum = letters2Times * letters3Times;
			
			boolean linesEqual;
			while(!lines.isEmpty()) {
				String line1 = lines.get(0);
				linesEqual = false;
				for(String line2 : lines.subList(1, lines.size())) {
					if (checkLines(line1, line2) == true) {
						linesEqual = true;
						System.out.println("Lines: " + line1 + " and " + line2 + " are equal!");
						lettersInIds = getSameLetters(line1, line2);
					}
				}
				if (!linesEqual) {
					lines.remove(0);
				} else {
					break;
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Could not find the file!");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String getSameLetters(String line1, String line2) {
		String str = "";
		char[] characters1 = line1.toCharArray();
		char[] characters2 = line2.toCharArray();
		
		for (int i = 0; i < characters1.length && i < characters2.length; i++) {
			if (characters1[i] == characters2[i]) {
				str += characters1[i];
			}
		}
		
		return str;
	}

	private boolean checkLines(String line1, String line2) {
		char[] characters1 = line1.toCharArray();
		char[] characters2 = line2.toCharArray();
		int charsNotEqual = 0;

		for (int i = 0; i < characters1.length && i < characters2.length; i++) {
			if (characters1[i] != characters2[i]) {
				charsNotEqual += 1;
			}
			if (charsNotEqual > 1) {
				return false;
			}
		}
		return true;
	}

	public int getCheckSum() {
		return checkSum;
	}

	public String getLettersOfIDs() {
		return lettersInIds;
	}

}

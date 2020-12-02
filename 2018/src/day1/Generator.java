package day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Generator {

	private int endNum;
	private List<Integer> genNums;
	private boolean repeated = false;
	private int numRepeatedTimes;

	public Generator(int startNum) {
		this.endNum = startNum;
		genNums = new ArrayList<Integer>();
		numRepeatedTimes = 1;
	}

	public int getEndNum() {
		return endNum;
	}

	public void begin() {
		genNums.clear();
		while (repeated == false) {
			try {
				FileReader fr = new FileReader(new File("src/day1/input1.txt"));
				BufferedReader br = new BufferedReader(fr);
				String line;
				while ((line = br.readLine()) != null) {
					System.out.println(line);
					if (line.startsWith("+")) {
						System.out.println("addition " + Integer.parseInt(line));
						endNum += Integer.parseInt(line);
					} else if (line.startsWith("-")) {
						System.out.println("subtration" + Integer.parseInt(line));
						endNum += Integer.parseInt(line);
					} else {
						System.out.println("That operation is not supported by this generators version!!!");
					}
					System.out.println("New num = " + endNum);
					if (genNums.contains(endNum)) {
						System.out.println("Frequency " + endNum + " has been repeated!!!");
						repeated = true;
						break;
					} else {
						genNums.add(endNum);
					}
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			numRepeatedTimes += 1;
		}
		genNums.clear();

	}

}

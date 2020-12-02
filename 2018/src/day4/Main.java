package day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
	
	public static String WAKE = "wakes up";
	public static String ASLEEP = "falls asleep";
	public static String SHIFT = "begins shift";

	public static void main(String[] args) {
		
		Map<Date, String> lines = new TreeMap<Date, String>();
		Map<Integer, List<Integer>> guardsSleep = new HashMap<Integer, List<Integer>>();
		Map<Integer, Map<Integer, Integer>> guardsTime = new HashMap<Integer, Map<Integer, Integer>>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("src/day4/input1.txt")));
			String line;
			Date date1;
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			
			while ((line = reader.readLine()) != null) {
				
				date1 = format.parse(line.substring(1, line.length()));
				
				//System.out.println(format.parse(line.substring(1, line.length())));
				
				lines.put(date1, line);
				
				
			}
			
			reader.close();
			
			//[1518-03-18 00:01] Guard #89 begins shift
			//[1518-03-18 00:35] falls asleep
			//[1518-03-18 00:59] wakes up
			
			String guardNum;
			int guardNumber = 0;
			List<Integer> sleepTime = new ArrayList<Integer>();
			Map<Integer, Integer> time = new HashMap<Integer, Integer>();
			Date date2 = new Date();
			Date date3;
			
			for(String line1 : lines.values()) {
				
				if(line1.contains(SHIFT)) {
					guardNum = line1.split(" ")[3];
					guardNum = guardNum.replaceAll("#", "");
					guardNumber = Integer.parseInt(guardNum);
					
					sleepTime = guardsSleep.get(guardNumber);
					time = guardsTime.get(guardNumber);
					if (sleepTime == null) {
						sleepTime = new ArrayList<Integer>();
					}
					
					if (time == null) {
						time = new HashMap<Integer, Integer>();
					}
					
					System.out.println("Shift changed to #" + guardNumber);
				} else if (line1.contains(ASLEEP)) {
					
					date2 = format.parse(line1.substring(1));
					
					//time.put(date2.getMinutes(), (time.get(date2.getMinutes()) + 1));
					
				} else if(line1.contains(WAKE)) {
					
					date3 = format.parse(line1.substring(1));
					
					System.out.println(date3.getMinutes() - date2.getMinutes() + " :Number of mins spent asleep: " + date2.getMinutes() + " " + date3.getMinutes());
					sleepTime.add((date3.getMinutes() - date2.getMinutes()));
					guardsSleep.put(guardNumber, sleepTime);
					
					for(int i = date2.getMinutes(); i < date3.getMinutes(); i++) {
						if (time.get(i) == null) {
							System.out.println("I was null");
							time.put(i, 1);
							System.out.println(i);
						} else {
							time.put(i, (time.get(i) + 1));
							System.out.println("Added one to " + i + " making it " + time.get(i));
						}
						
					}
					guardsTime.put(guardNumber, time);
					
				} else {
					System.err.println("Uh.. oh.. Unlisted command!!!");
				}
			}
			
			int totalTimeAsleep;
			Map<Integer, Integer> topGuard = new HashMap<Integer, Integer>();
			boolean firstTry = true;
			
			for(Integer guardNumber2 : guardsSleep.keySet()) {
				totalTimeAsleep = 0;
				sleepTime = guardsSleep.get(guardNumber2);
				for(Integer sleep : sleepTime) {
					totalTimeAsleep += sleep;
				}
				
				for(int guardNumber3 : topGuard.keySet()) {
					if(topGuard.get(guardNumber3) < totalTimeAsleep) {
						topGuard.clear();
						topGuard.put(guardNumber2, totalTimeAsleep);
					}
				}
				
				if (firstTry) {
					topGuard.put(guardNumber2, totalTimeAsleep);
					firstTry = false;
				}
			}
			
			int guardNumber4 = 0;
			
			for(int guardNumber3 : topGuard.keySet()) {
				guardNumber4 = guardNumber3;
				System.out.println("Most Time Asleep was spent by Guard: " + guardNumber3 + " who spent " + topGuard.get(guardNumber3) + "mins asleep!");
			}
			
			Map<Integer, Integer> timesAsleep = guardsTime.get(guardNumber4);
			System.out.println(timesAsleep.get(24));
//			firstTry = true;
			int bestTime = 0;
			int thisTime = 0;
			int goodKey = 0;
			for(int key : timesAsleep.keySet()) {
				thisTime = timesAsleep.get(key);
				if(thisTime > bestTime) {
					bestTime = thisTime; //changed from thisTime
					goodKey = key;
					System.out.println(key);
				}
			}
			
			System.out.println("Guard: " + guardNumber4 + " spent " + bestTime + " most asleep!");
			int ans = guardNumber4 * goodKey;
			System.out.println("First answer: " + ans); //21956
			
			//Second Answer
			
			int guardNum2 = 0;
			int time2 = 0;
			int greatestMinutesAsleep = 0;
			Map<Integer, Integer> timesAsleep2 = guardsTime.get(guardNumber4);
			
			for(int key2 : guardsTime.keySet()) {
				
				System.out.println("Changing to ..." + key2);
				timesAsleep2 = guardsTime.get(key2);
				
				for (int key3 : timesAsleep2.keySet()) {
					if (timesAsleep2.get(key3) > greatestMinutesAsleep) {
						time2 = key3;
						guardNum2 = key2;
						greatestMinutesAsleep = timesAsleep2.get(key3);
						System.out.println("Hey, I was activated... " + greatestMinutesAsleep);
					}
					System.out.println(key3);
				}
				
			}
			
			System.out.println(guardNum2 + " " + time2);
			
			int ans2 = guardNum2 * time2;
			
			System.out.println("Second answer: " + ans2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

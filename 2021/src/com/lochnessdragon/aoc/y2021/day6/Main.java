package com.lochnessdragon.aoc.y2021.day6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lochnessdragon.aoc.y2021.utils.Utils;

public class Main {

	public static void main(String[] args) {
		String data = Utils.readFile("src/com/lochnessdragon/aoc/y2021/day6/input.txt");
		data = data.trim();
		String[] fishes = data.split(",");
		
		List<Integer> fishTimers = new ArrayList<Integer>();
		
		for(String fish : fishes) {
			fishTimers.add(Integer.parseInt(fish));
		}
		
		{
			// part 1
			int lastDay = 256;
			
			//int count = simulateLaternFishSlow(lastDay, fishTimers);
			long count = simulateLaternFish(lastDay, fishTimers);
			
			System.out.println("[ Part 1 ]: Number of Lantern Fish: " + count);
		}
	}
	
	private static long simulateLaternFish(int lastDay, List<Integer> fishTimers) {
		long count = 0;
		
		Map<Integer, Long> timerMap = new HashMap<Integer, Long>();
		
		// load initial state
		for(int fish : fishTimers) {
			timerMap.put(fish, timerMap.getOrDefault(fish, 0L) + 1);
		}
		
		for(int i = 0; i < lastDay; i++) {
			long procreatingFish = timerMap.getOrDefault(0, 0L);
			for(int key = 0; key < 9; key++) {
				timerMap.put(key, timerMap.getOrDefault(key + 1, 0L));
			}
			
			timerMap.put(8, procreatingFish);
			timerMap.put(6, timerMap.getOrDefault(6, 0L) + procreatingFish);
		}
		
		for(long timer : timerMap.values()) {
			count += timer;
		}
		
		return count;
	}

	private static long simulateLaternFishSlow(int lastDay, List<Integer> fishTimers) {
		for(int i = 0; i < lastDay; i++) {
			int newFishCount = 0;
			
			for(int x = 0; x < fishTimers.size(); x++) {
				int timer = fishTimers.get(x);
				
				if(timer == 0) {
					newFishCount++;
					timer = 6;
				} else {
					timer--;
				}
				
				fishTimers.set(x, timer);
			}
			
			for(int x = 0; x < newFishCount; x++) {
				fishTimers.add(8);
			}
		}
		return fishTimers.size();
	}

}

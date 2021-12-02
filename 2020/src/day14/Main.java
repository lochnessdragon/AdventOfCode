package day14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource input = new Resource("src/day14/test.txt");

		// part 1
		{
			Map<Long, Long> memory = new HashMap<Long, Long>();

			String mask = "";

			for (String line : input.read()) {
				String[] parts = line.split("=");

				if (line.contains("mask")) {
					mask = parts[1].trim();
					System.out.println("New Mask: m" + mask + ":");
				} else {
					long address = Long.parseLong(parts[0].replaceFirst("mem\\[", "").replaceFirst("\\]", "").trim());
					long value = Long.parseLong(parts[1].trim());

					memory.put(address, applyMask(mask, value));
				}
			}

			long total = 0;

			for (long value : memory.values()) {
				if (!(value == 0)) {
					total += value;
				}
			}

			System.out.println("[Part 1]: The total amount of the values in memory is: " + total);
		}
//		System.out.println("Set Bit test: " + unsetBit(-1, 63) + " Long Max: " + Long.MAX_VALUE + " Long Min: " + Long.MIN_VALUE);
//		System.out.println("Mask test: " + applyMask("XXXX1XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX", 1));

		// part 2 
		{
			Map<Long, Long> memory = new HashMap<Long, Long>();
			
			String mask = "";

			for (String line : input.read()) {
				String[] parts = line.split("=");

				if (line.contains("mask")) {
					mask = parts[1].trim();
					System.out.println("New Mask: m" + mask + ":");
				} else {
					List<Long> addresses = getAllPossibleAddresses(mask, Long.parseLong(parts[0].replaceFirst("mem\\[", "").replaceFirst("\\]", "").trim()));
					long value = Long.parseLong(parts[1].trim());

					for(long address : addresses) {
						memory.put(address, value);
					}
				}
			}

			long total = 0;

			for (long value : memory.values()) {
				if (!(value == 0)) {
					total += value;
				}
			}
			
			System.out.println("[Part 2]: The total amount of the values in memory is: " + total);
		}
		
	}

	private static List<Long> getAllPossibleAddresses(String mask, long originalAddress) {
		List<Long> addresses = new ArrayList<Long>();
		
		List<Integer> indexOfX = new ArrayList<Integer>();
		
		for(int i = 0; i < mask.length(); i++) {
			if(mask.charAt(i) == 'X') {
				indexOfX.add(i);
			}
		}
		
		
		
		return addresses;
	}

	private static Long applyMask(String mask, long value) {

//		if(mask.charAt(0) == '1') {
//			System.out.println("Original Value: " + value);
//		}

		int max_offset = 35;

		for (long i = 0; i < 36; i++) {
			char bitPart = mask.charAt((int) i);
//			System.out.println("Bit: " + (35-i));
//			if((35-i) == 63) {
//				System.out.println("ERROR!");
//			}

//			if((max_offset - i) == 31) {
//				max_offset = 35;
//				System.out.println("Changed max offset");
//			}

			if (bitPart == '0') {
				value = unsetBit(value, max_offset - i);
			} else if (bitPart == '1') {
				value = setBit(value, max_offset - i);
			} else {
				continue;
			}
		}

		if (value < 0) {
			System.out.println("Final Value: " + value);
		}

		return value;
	}

	private static long unsetBit(long value, long pos) {
		return value & ~((long) 1 << pos);
	}

	private static long setBit(long value, long pos) {
		// TODO Auto-generated method stub
		return value | ((long) 1 << pos);
	}

}

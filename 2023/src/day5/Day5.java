package day5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import utils.Utils;

// p1: 662197086 - 662197086
public class Day5 {

	public static void main(String[] args) {
		// test answers
		// part 1 = 35
		// part 2 = 46
		List<String> lines = Utils.readFile("src/day5/data.txt");
		
		// part 1
		List<Long> seeds = new ArrayList<Long>();
		List<SeedRange> seedsPt2 = new ArrayList<SeedRange>();
		List<List<MapRange>> ranges = new ArrayList<List<MapRange>>();
		List<String> headers = new ArrayList<String>();
		
		int rangeIdx = -1;
		
//		int linenum = 0;
		for (String line : lines) {
//			System.out.println(linenum + ": " + line);
//			linenum++;
			if (line.isEmpty())
				continue; // skip empty
			
			if(line.startsWith("seeds:")) {
				// read seeds
				String seedStr = line.split(":")[1].trim();
				String[] nums = seedStr.split(" ");
				
				// part 1 seeds
				for (String num : nums) {
					if(num.isEmpty())
						continue;
					seeds.add(Long.parseLong(num));
				}
				
				// part 2 seed ranges
				for(int i = 0; i < nums.length / 2; i ++) {
					int properIdx = i * 2;
					long start = Long.parseLong(nums[properIdx]);
					long length = Long.parseLong(nums[properIdx + 1]);
					seedsPt2.add(new SeedRange(start, length));
				}
			} else if(line.endsWith(":")) {
				// debug headers to indicate which part of the maps we are at
				headers.add(line);
				rangeIdx++;
			} else {
				if(rangeIdx >= ranges.size()) {
					ranges.add(new ArrayList<MapRange>());
				}
				
				List<MapRange> currRangeList = ranges.get(rangeIdx);
				currRangeList.add(new MapRange(line));
//				System.out.println("Length: " + currRangeList.size());
			}
		}
		
		// figure out map collisions
//		for(int i = 0; i < ranges.size(); i++) {
//			System.out.println("Collisions in group " + headers.get(i) + getMapCollisionCount(ranges.get(i)));
//		}
		
		// figure out part 2 collisions (just some random debug info)
		System.out.println("Collisions: " + getCollisionCount(seedsPt2));
		System.out.println("Seeds pt 1 len: " + seeds.size());
		System.out.println("Seeds pt 2 len: " + seedsPt2.size());
		
		System.out.println("Part 1: " + getLowest(ranges, seeds));
		System.out.println("Part 2: " + getLowestSeedRange(headers, ranges, seedsPt2));
	}
	
	static int getMapCollisionCount(List<MapRange> ranges) {
		int collisions = 0;
		for (int i = 0; i < ranges.size(); i++) {
			for (int j = i + 1; j < ranges.size(); j++) {
				if (ranges.get(i).collidesWith(ranges.get(j))) {
					collisions++;
				}
			}
		}
		return collisions;
	}
	
	static int getCollisionCount(List<SeedRange> seeds) {
		int collisions = 0;
		for (int i = 0; i < seeds.size(); i++) {
			for (int j = i + 1; j < seeds.size(); j++) {
				if(seeds.get(i).intersects(seeds.get(j))) {
					collisions++;
				}
			}
		}
		return collisions;
	}
	
	static long getLowest(List<List<MapRange>> ranges, List<Long> seeds) {
		for (List<MapRange> rangeList : ranges) {
			for(int i = 0; i < seeds.size(); i++) {
				long seed = seeds.get(i);
				for(MapRange range : rangeList) {
					long newVal = range.map(seed);
					if(newVal != seed) {
						seeds.set(i, newVal);
						break;
					}
				}
			}
		}
		
		return seeds.stream().min(Comparator.naturalOrder()).get();
	}
	
	static void sortSeedList(List<SeedRange> seeds) {
		seeds.sort((SeedRange side1, SeedRange side2) -> { return Long.compare(side1.start, side2.start); });
	}
	
	static void printSeedRanges(List<SeedRange> seeds) {
		sortSeedList(seeds);
		for (SeedRange range : seeds) {
			System.out.println(range);
		}
	}
	
	static List<SeedRange> shrinkSeedList(List<SeedRange> seeds) {
		sortSeedList(seeds);
		List<SeedRange> newSeeds = new ArrayList<SeedRange>();
		SeedRange currRange = null;
		int intersections = 0;
		for (int i = 0; i < seeds.size(); i++) {
			SeedRange element = seeds.get(i);
			if(currRange != null) {
				if(currRange.intersects(element)) {
					// append the element to this seed range
					currRange.len += element.end() - currRange.end();
					intersections++;
				} else {
					// no intersection
					newSeeds.add(currRange);
					currRange = element;
				}
			} else {
				currRange = element;
			}
		}
		
		System.out.println("Found " + intersections + " optimizations");
		
		if(currRange != null)
			newSeeds.add(currRange);
		
		return newSeeds;
	}
	
	static long getLowestSeedRange(List<String> headers, List<List<MapRange>> ranges, List<SeedRange> seeds) {
		// part 2, returns the lowest seed created by the maps
		long startingSeeds = seeds.stream().map(seed->seed.len).reduce(0L, (a, b) -> a + b);
		int header_idx = 0;
		for (List<MapRange> rangeList : ranges) {
			System.out.println(headers.get(header_idx));
			
			List<SeedRange> newSeeds = new ArrayList<SeedRange>();
			for(int i = 0; i < seeds.size(); i++) {
				SeedRange seed = seeds.get(i);
				for(MapRange range : rangeList) {
					// see if the range has any mapping to apply
					MapRange.SeedRangeTuple answer = range.mapRange(seed);
					long seedSum = answer.sumSeeds();
					if(seedSum != seed.len) {
						System.out.println("We are printing some seeds here! 0xfishy business");
						System.out.println((seedSum - seed.len) + " new seeds.");
						System.out.println(seed);
						System.out.println(range);
						System.out.println(answer);
					}
					
					if(answer.newRange.isPresent()) {
						// new range, meaning we have some modification to do
						
						if(answer.unmodifiedRanges.size() == 0) {
							// previous one totally absorbed, replace with new range and break.
							if(answer.newRange.get().start == 0) {
								System.out.println("New Range is 0");
								System.out.println("From: " + seed);
							}
							seeds.set(i, answer.newRange.get());
							break; // no need to continue to process this one
						} else {
							//newSeeds.add(answer.newRange.get());
							seed = answer.unmodifiedRanges.get(0);
							seeds.set(i, seed);
							if(answer.unmodifiedRanges.size() > 1) {
								seeds.addAll(answer.unmodifiedRanges.subList(1, answer.unmodifiedRanges.size()));
								System.out.println("Split right down the middle");
							}
							newSeeds.add(answer.newRange.get());
						}
					}
				}
			}
			
			seeds.addAll(newSeeds);
			seeds = shrinkSeedList(seeds); // unneeded optimization, but makes debugging easier
			
			header_idx++;
		}
		
		long currentSeedNum = seeds.stream().map(seed->seed.len).reduce(0L, (a, b) -> a + b);
		
		System.out.println("Number of seed ranges after total application: " + seeds.size());
		System.out.println("Number of starting seeds: " + startingSeeds);
		System.out.println("Number of current seeds:  " + currentSeedNum);
		System.out.println("Difference in total number of seeds: " + (currentSeedNum - startingSeeds));
		
		// find minimum seed range
		printSeedRanges(seeds);
		return seeds.stream().map(seed -> seed.start).min(Comparator.naturalOrder()).get();
	}

}

class SeedRange {
	public long start;
	public long len;
	
	public SeedRange(long start, long len) {
		this.start = start;
		this.len = len;
	}
	
	public long end() {
		return start + (len - 1);
	}
	
	public boolean equals(SeedRange other) {
		if (other.start == this.start && other.len == this.len)
			return true;
		return false;
	}
	
	public boolean intersects(SeedRange other) {
		if ((this.start <= other.start && this.end() >= other.start) || (other.start <= this.start && other.end() >= this.start)) 
			return true;
		return false;
	}
	
	public String toString() {
		return "SeedRange: [" + start + ", " + end() + "] (" + len + " seeds)";
	}
}

class MapRange {
	long srcStart;
	long destStart;
	long rangeLen;
	
	public MapRange(String rangeStr) {
		String[] parts = rangeStr.split(" ");
		destStart = Long.parseLong(parts[0]);
		srcStart = Long.parseLong(parts[1]);
		rangeLen = Long.parseLong(parts[2]);
	}
	
	public MapRange(long srcStart, long destStart, long rangeLen) {
		super();
		this.srcStart = srcStart;
		this.destStart = destStart;
		this.rangeLen = rangeLen;
	}

	public long map(long num) {
		if(!isInRange(num))
			return num;
		
		return (num - srcStart) + destStart;
	}
	
	public boolean isInRange(long num) {
		long adjForBeginning = num - srcStart;
		if (adjForBeginning >= 0 && adjForBeginning < rangeLen) 
			return true;
		return false;
	}
	
	public boolean collidesWith(MapRange other) {
		if ((srcStart <= other.srcStart && srcEnd() >= other.srcStart) || (other.srcStart <= srcStart && other.srcEnd() >= srcStart))
			return true;
		return false;
	}
	
	public long srcEnd() {
		return srcStart + (rangeLen - 1);
	}
	
	public SeedRangeTuple mapRange(SeedRange other) {
		// this function either returns 1, 2, or 3 Seed Ranges
		// 1 if the seed range was fully contained (and therefore modified), 2 if the seed range extended on one bound, and three if the seed range extended both rounds
		
		// stores the new range for use in the tuple
		Optional<SeedRange> newRange = Optional.empty();
		// store the unmodified ranges (1 or 2)
		List<SeedRange> unmodifiedRanges = new ArrayList<SeedRange>();
		if(this.srcStart > other.end() || this.srcEnd() < other.start) {
			// if we are outside the bounds
			// nothing happened, it's technically an unmodified range, return it
			unmodifiedRanges.add(other);
		} else if (this.srcStart <= other.start && this.srcEnd() >= other.end()) {
			// the other range is fully contained within this one.
			newRange = Optional.of(new SeedRange((other.start - this.srcStart) + this.destStart, other.len));
		} else {
			// one new range, but could be more than one unmodified range (i.e. split down the middle)
			
			long distanceBtwnStart = other.start - srcStart;
			// offset into range (the distance between the beginning of the src range to the beginning of the seed range (or 0 if the other one starts before we do)
			long offsetIntoRange = Math.max(0, distanceBtwnStart);
			
			// find the new range
			long newRangeLength = (Math.min(srcEnd(), other.end()) - Math.max(srcStart, other.start)) + 1;
			long newRangeStart = destStart + offsetIntoRange;
			newRange = Optional.of(new SeedRange(newRangeStart, newRangeLength));
			
			// find the potentially two unmodified ranges
			if(distanceBtwnStart < 0) {
				// the other seed range starts first, we need to find it's length and cap it
				unmodifiedRanges.add(new SeedRange(other.start, Math.abs(distanceBtwnStart)));
			}
			
			if(other.end() - srcEnd() > 0) {
				// the other range ends after this one
				unmodifiedRanges.add(new SeedRange(srcEnd() + 1, other.end() - srcEnd()));
			}
		}
		
		return new SeedRangeTuple(newRange, unmodifiedRanges);
	}
	
	public String toString() {
		return "MapRange: srcStart=" + srcStart + " srcEnd=" + srcEnd() + " destStart=" + destStart + " length=" + rangeLen;
	}
	
	static class SeedRangeTuple {
		public Optional<SeedRange> newRange;
		public List<SeedRange> unmodifiedRanges;
		
		public SeedRangeTuple(Optional<SeedRange> newRange, List<SeedRange> unmodifiedRanges) {
			this.newRange = newRange;
			this.unmodifiedRanges = unmodifiedRanges;
		}
		
		public long sumSeeds() {
			long sum = 0;
			
			if (!newRange.isEmpty())
				sum += newRange.get().len;
			for (SeedRange range : unmodifiedRanges) {
				sum += range.len;
			}
			
			return sum;
		}
		
		public String toString() {
			String newRangeStr = "<empty>";
			String unmodifiedRangesStr = "";
			if (!newRange.isEmpty())
				newRangeStr = newRange.get().toString();
			int i = 0;
			for (SeedRange range : unmodifiedRanges) {
				if (i > 0) {
					unmodifiedRangesStr += ", ";
				}
				unmodifiedRangesStr += range.toString();
				i++;
			}
			return "SeedRangeTuple: {newRange: " + newRangeStr + " UnmodifiedRanges: [" + unmodifiedRangesStr + "]}";
		}
	}
}
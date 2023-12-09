package day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import utils.Utils;

public class Day7 {
	
	static String CARD_ORDER = "AKQJT98765432";
	static String CARD_ORDER_PART2 = "AKQT98765432J"; // joker made the weakest for part 2

	public static void main(String[] args) {
		List<String> lines = Utils.readFile("src/day7/data.txt");
		List<Hand> hands = new ArrayList<Hand>();
		
		for (String line : lines) {
			hands.add(new Hand(line));
		}
		
		// part 1
		Collections.sort(hands);
		int part1 = getWinnings(hands);
		
		// part 2
		hands.sort(new Hand.Part2Comparator());
		System.out.println(hands);
		int part2 = getWinnings(hands);
		
		System.out.println("Part 1: " + part1);
		System.out.println("Part 2: " + part2);
	}
	
	static int getWinnings(List<Hand> sortedHands) {
		int winnings = 0;
		for (int i = 0; i < sortedHands.size(); i++) {
			winnings += sortedHands.get(i).getWinning(i + 1);
		}
		
		return winnings;
	}

}

class Hand implements Comparable<Hand> {
	String cards;
	int bid;
	
	public Hand(String line) {
		String[] parts = line.split(" ");
		cards = parts[0];
		bid = Integer.parseInt(parts[1]);
	}
	
	public int getWinning(int rank) {
		return bid * rank;
	}
	
	public String toString() {
		return "Hand: \"" + cards + "\" " + bid;
	}
	
	
	// part 1
	public int getType() {
		/*
		 * Five of a kind, where all five cards have the same label: AAAAA
		 * Four of a kind, where four cards have the same label and one card has a different label: AA8AA
		 * Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
		 * Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
		 * Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
		 * One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
		 * High card, where all cards' labels are distinct: 23456
		 */
		// A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2
		List<Integer> uniqueCharOccurrences = new ArrayList<Integer>();
		List<Character> uniqueChars = new ArrayList<Character>();
		for (char card : cards.toCharArray()) {
			if (uniqueChars.contains(card)) {
				int idx = uniqueChars.indexOf(card);
				int count = uniqueCharOccurrences.get(idx);
				uniqueCharOccurrences.set(idx, count + 1);
			} else {
				uniqueChars.add(card);
				uniqueCharOccurrences.add(1);
			}
		}
		
		Collections.sort(uniqueCharOccurrences);
		Collections.reverse(uniqueCharOccurrences);
		
		if (uniqueCharOccurrences.get(0) == 5) {
			return 7;
		} else if (uniqueCharOccurrences.get(0) == 4) {
			return 6;
		} else if (uniqueCharOccurrences.get(0) == 3 && uniqueCharOccurrences.get(1) == 2) {
			return 5;
		} else if (uniqueCharOccurrences.get(0) == 3) {
			return 4;
		} else if (uniqueCharOccurrences.get(0) == 2 && uniqueCharOccurrences.get(1) == 2) {
			return 3;
		} else if (uniqueCharOccurrences.get(0) == 2) {
			return 2;
		} else {
			return 1;
		}
	}
	
	// part 2
	public int getType2() {
		/*
		 * Five of a kind, where all five cards have the same label: AAAAA
		 * Four of a kind, where four cards have the same label and one card has a different label: AA8AA
		 * Full house, where three cards have the same label, and the remaining two cards share a different label: 23332
		 * Three of a kind, where three cards have the same label, and the remaining two cards are each different from any other card in the hand: TTT98
		 * Two pair, where two cards share one label, two other cards share a second label, and the remaining card has a third label: 23432
		 * One pair, where two cards share one label, and the other three cards have a different label from the pair and each other: A23A4
		 * High card, where all cards' labels are distinct: 23456
		 */
		// A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, or 2
		List<Integer> uniqueCharOccurrences = new ArrayList<Integer>();
		List<Character> uniqueChars = new ArrayList<Character>();
		int jokerCount = 0;
		for (char card : cards.toCharArray()) {
			if (card == 'J') {
				jokerCount++;
				continue;
			}
			if (uniqueChars.contains(card)) {
				int idx = uniqueChars.indexOf(card);
				int count = uniqueCharOccurrences.get(idx);
				uniqueCharOccurrences.set(idx, count + 1);
			} else {
				uniqueChars.add(card);
				uniqueCharOccurrences.add(1);
			}
		}
		
		Collections.sort(uniqueCharOccurrences);
		Collections.reverse(uniqueCharOccurrences);
		
		// early out if the whole string is jokers
		if(jokerCount == 5)
			return 7;
		
		int firstCharCount = uniqueCharOccurrences.get(0);
		uniqueCharOccurrences.set(0, firstCharCount + jokerCount);
		
		if (uniqueCharOccurrences.get(0) == 5) {
			return 7;
		} else if (uniqueCharOccurrences.get(0) == 4) {
			return 6;
		} else if (uniqueCharOccurrences.get(0) == 3 && uniqueCharOccurrences.get(1) == 2) {
			return 5;
		} else if (uniqueCharOccurrences.get(0) == 3) {
			return 4;
		} else if (uniqueCharOccurrences.get(0) == 2 && uniqueCharOccurrences.get(1) == 2) {
			return 3;
		} else if (uniqueCharOccurrences.get(0) == 2) {
			return 2;
		} else {
			return 1;
		}
	}
	
	@Override
    public int compareTo(Hand other) {
    	// return 0 if equal
    	// less than 0 if left is less than right
    	// greater than 0 if left is greater than right
        int hand1Type = this.getType();
        int hand2Type = other.getType();
        
        if (hand1Type < hand2Type) {
        	return -1;
        } else if (hand1Type > hand2Type) {
        	return 1;
        } else {
        	for (int i = 0; i < 5; i++) {
        		char c1 = this.cards.charAt(i);
        		char c2 = other.cards.charAt(i);
        		if(Day7.CARD_ORDER.indexOf(c1) > Day7.CARD_ORDER.indexOf(c2)) {
        			return -1;
        		} else if (Day7.CARD_ORDER.indexOf(c1) < Day7.CARD_ORDER.indexOf(c2)) {
        			return 1;
        		}
        	}
        }
        System.out.println("Equal cards!");
        return 0;
    }
	
	public static class Part2Comparator implements Comparator<Hand> {

		@Override
		public int compare(Hand hand1, Hand hand2) {
			// return 0 if equal
	    	// less than 0 if left is less than right
	    	// greater than 0 if left is greater than right
	        int hand1Type = hand1.getType2();
	        int hand2Type = hand2.getType2();
	        
	        if (hand1Type < hand2Type) {
	        	return -1;
	        } else if (hand1Type > hand2Type) {
	        	return 1;
	        } else {
	        	for (int i = 0; i < 5; i++) {
	        		char c1 = hand1.cards.charAt(i);
	        		char c2 = hand2.cards.charAt(i);
	        		if(Day7.CARD_ORDER_PART2.indexOf(c1) > Day7.CARD_ORDER_PART2.indexOf(c2)) {
	        			return -1;
	        		} else if (Day7.CARD_ORDER_PART2.indexOf(c1) < Day7.CARD_ORDER_PART2.indexOf(c2)) {
	        			return 1;
	        		}
	        	}
	        }
	        System.out.println("Equal cards!");
	        return 0;
		}
		
	}

}

package com.lochnessdragon.aoc.y2021.day4;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import com.lochnessdragon.aoc.y2021.utils.Utils;

public class Main {
	public static void main(String[] args) {
		String data = Utils.readFile("src/com/lochnessdragon/aoc/y2021/day4/input.txt");
		
		String[] lines = data.split("\n");
		List<Integer> bingoNumbers = new ArrayList<Integer>();
		
		for(String section : lines[0].split(",")) {
			bingoNumbers.add(Integer.parseInt(section));
		}
		
		List<BingoBoard> boardsPart1 = new ArrayList<BingoBoard>();
		List<BingoBoard> boardsPart2= new ArrayList<BingoBoard>();
		List<List<Integer>> boardValues = new ArrayList<List<Integer>>();
		
		int index = 0;
		for(String section : lines) {
			
			if(index > 1) {
				if(!section.equals("")) {
					// append to int array for board
					//System.out.println(section);
					String[] numbersStrs = section.split("\\s+");
					
					List<Integer> numbers = new ArrayList<Integer>();
					
					for(String numberStr : numbersStrs) {
//						System.out.print(numberStr + ", ");
						if(numberStr.equals("")) {
							continue;
						}
						numbers.add(Integer.parseInt(numberStr));
					}
					
					for(int i = 0; i < numbers.size(); i++) {
						if(boardValues.size() < (i + 1)) {
							boardValues.add(new ArrayList<Integer>());
						}
						
						boardValues.get(i).add(numbers.get(i));
					}
				} else {
					// push bingo board
					int[][] array = new int[boardValues.size()][boardValues.get(0).size()];
					
					for(int x = 0; x < boardValues.size(); x++) {
						for(int y = 0; y < boardValues.get(x).size(); y++) {
							array[x][y] = boardValues.get(x).get(y);
						}
					}
					
					boardsPart1.add(new BingoBoard(array));
					boardsPart2.add(new BingoBoard(array));
					
					boardValues.clear();
				}
			}
			
			index++;
		}
		
		// push bingo board ( final)
		int[][] array = new int[boardValues.size()][boardValues.get(0).size()];
		
		for(int x = 0; x < boardValues.size(); x++) {
			for(int y = 0; y < boardValues.get(x).size(); y++) {
				array[x][y] = boardValues.get(x).get(y);
			}
		}
		
		boardsPart1.add(new BingoBoard(array));
		boardsPart2.add(new BingoBoard(array));
		
		boardValues.clear();
		
		for(BingoBoard board : boardsPart1) {
			board.print();
			System.out.println();
		}
		
		{
			// part 1
			BingoBoard solvedBoard = null;
			int winningRoundNumber = 0;
			
			for(Integer number : bingoNumbers) {
				for(BingoBoard board : boardsPart1) {
					board.markNumber(number);
				}
				
				for(BingoBoard board : boardsPart1) {
					if(board.isSolved()) {
						solvedBoard = board;
						winningRoundNumber = number;
						break;
					}
				}
				
				if(solvedBoard != null)
					break;
			}
			
			//int markedCount = solvedBoard.countMarked();
			int unmarkedCount = solvedBoard.sumUnMarked();
			System.out.println("[ Part 1 ]: Found best board! Last Called #: " + winningRoundNumber + " Unmarked Sum: " + unmarkedCount + " Multiplied: " + winningRoundNumber * unmarkedCount);
		}
		
		{
			// part 2
			int bingoNumberIndex = 0;
			BingoBoard losingBoard = null;
			while(losingBoard == null) {
				int bingoNumber = bingoNumbers.get(bingoNumberIndex);
				
				for(BingoBoard board : boardsPart2) {
					board.markNumber(bingoNumber);
				}
				
				if(boardsPart2.size() > 1) {
					boardsPart2.removeIf(board -> (board.isSolved()));
				} else if (boardsPart2.get(0).isSolved()) {
					losingBoard = boardsPart2.get(0);
					System.out.println(losingBoard.boardInts[0][0] + "");
					break;
				}
				
				bingoNumberIndex++;
			}
			
			int unmarkedSum = losingBoard.sumUnMarked();
			int roundNumber = bingoNumbers.get(bingoNumberIndex);
			System.out.println("[ Part 2 ]: Found worst board! Last Called #: " + roundNumber + " Unmarked Sum: " + unmarkedSum + " Multiplied: " +  roundNumber * unmarkedSum);
		}
	}
}

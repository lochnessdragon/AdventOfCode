package com.lochnessdragon.aoc.y2021.day4;

public class BingoBoard {
	public final int[][] boardInts;
	public boolean[][] goodInts;
	
	public BingoBoard(int[][] board) {
		this.boardInts = board;
		
		goodInts = new boolean[board.length][board[0].length];
	}
	
	void markNumber(int number) {
		for(int x = 0; x < boardInts.length; x++) {
			for(int y = 0; y < boardInts[x].length; y++) {
				if(boardInts[x][y] == number) {
					goodInts[x][y] = true;
					return;
				}
			}
		}
	}
	
	boolean isSolved() {
		// check every column
		for(int x = 0; x < goodInts.length; x++) {
			boolean columnSolved = true;
			for(boolean marked : goodInts[x]) {
				if(!marked) {
					columnSolved = false;
					break;
				}
			}
			
			if(columnSolved == true)
				return true;
		}
		
		// check every row
		for(int y = 0; y < goodInts[0].length; y++) {
			boolean rowSolved = true;
			
			for(int x = 0; x < goodInts.length; x++) {
				if(!goodInts[x][y]) {
					rowSolved = false;
					break;
				}
			}
			
			if(rowSolved == true)
				return true;
		}
		
		// if no rows or columns were good, return false
		return false;
	}
	
	void print() {
		for(int x = 0; x < boardInts.length; x++) {
			for(int y = 0; y < boardInts[x].length; y++) {
				System.out.print(boardInts[x][y] + ", ");
			}
			System.out.println();
		}
	}
	
	int countMarked() {
		int count = 0;
		for(int x = 0; x < goodInts.length; x++) {
			for(boolean marked : goodInts[x]) {
				if(marked) {
					count++;
				}
			}
		}
		return count;
	}
	
	int countUnMarked() {
		int count = 0;
		for(int x = 0; x < goodInts.length; x++) {
			for(boolean marked : goodInts[x]) {
				if(!marked) {
					count++;
				}
			}
		}
		return count;
	}
	
	int sumUnMarked() {
		int sum = 0;
		for(int x = 0; x < goodInts.length; x++) {
			for(int y = 0; y < goodInts[x].length; y++) {
				if(!goodInts[x][y]) {
					sum += boardInts[x][y];
				}
			}
		}
		return sum;
	}
}

package day3;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SquareFeetGenerator {

	private int squareFeet;
	private List<Rectangle> rects;
	private ArrayList<ArrayList<Integer>> squares;
	private Cloth cloth;
	private int runningTotal;

	public SquareFeetGenerator() {
		rects = new ArrayList<Rectangle>();
		squares = new ArrayList<ArrayList<Integer>>();
		cloth = new Cloth(1000, 1000); //131916 115215
		runningTotal = 0;
	}

	public int generate() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("src/day3/input1.txt")));
			String line;
			String[] split1;
			String[] split2;
			String[] xAndY;
			String[] wAndH;
			int x, y, w, h;
			Rectangle rect1;
			int lineNumber = 1;

			while ((line = br.readLine()) != null) {
				//System.out.println("Line: " + lineNumber);
				split1 = line.split("@"); // Split it to divide the number from the dimensions!
				split2 = split1[1].split(" "); // Split the rest to get x, y and w x h
				xAndY = split2[1].split(",");
				wAndH = split2[2].split("x");
				x = Integer.parseInt(xAndY[0]);
				y = Integer.parseInt(xAndY[1].split(":")[0]);
				w = Integer.parseInt(wAndH[0]);
				h = Integer.parseInt(wAndH[1]);
				System.out.print("X: " + x);
				System.out.print(" Y: " + y);
				System.out.print(" Width: " + w);
				System.out.println(" Height: " + h);

				rect1 = new Rectangle(x, y, w, h);

				// for (Rectangle rect2 : rects) {
				// System.out.println("Getting Overlapping Grids...");
				// List<Integer[]> grids = getOverlapGrid(rect1, rect2);
				// System.out.println("Got Overlapping Grids");
				// if (!grids.isEmpty()) {
				// for (Integer[] grid1 : squares) {
				// for (Integer[] grid2 : grids) {
				// if (grid1 == grid2) {
				// grids.remove(grid2);
				// System.out.println("Removing grid!");
				// }
				// }
				// }
				// squares.addAll(grids);
				// }
				//
				// }
				rects.add(rect1);
				lineNumber++;
			}
			br.close();

			calcOverlappingSquares();

		} catch (Exception e) {
			e.printStackTrace();
		}

		squareFeet = cloth.getAmountOfTrue(); //117948
		System.out.println(runningTotal);

		return squareFeet;
	}

	private void calcOverlappingSquares() {
		System.out.println("Calculating Overlapping Squares!");

		Rectangle current;
//		ArrayList<ArrayList<Integer>> overlappedSquares;

		while (!rects.isEmpty()) {
			current = rects.get(0);
			for (Rectangle rect2 : rects.subList(1, rects.size())) {
				getOverlapGrid(current, rect2);
//				for (ArrayList<Integer> square : overlappedSquares) {
//					if (!squares.contains(square)) {
//						squares.add(square);
//					} else {
//						System.out.println("Square is already on file!");
//					}
//				}
			}
			rects.remove(0);
			//System.out.println("Rectangle List length: " + rects.size());
		}

//		for (List<Integer> square : squares) {
//			System.out.println("\n" + "X " + square[0] + "\n" + "Y " + square[1]);
//		}

	}
	
	//ArrayList<ArrayList<Integer>>

	private void getOverlapGrid(Rectangle rect1, Rectangle rect2) {
		int x1 = (int) rect1.getX();
		int y1 = (int) rect1.getY();
		int w1 = (int) rect1.getWidth();
		int h1 = (int) rect1.getHeight();

		int x2 = (int) rect2.getX();
		int y2 = (int) rect2.getY();
		int w2 = (int) rect2.getWidth();
		int h2 = (int) rect2.getHeight();

		int x3 = 0, y3 = 0, w3 = 0, h3 = 0, numOfGridSquares = 0;

//		int distX1X2;
//		int distY1Y2;
//
//		ArrayList<ArrayList<Integer>> gridSquares = new ArrayList<ArrayList<Integer>>();
//		Integer[] xY;

		if (rect1.intersects(rect2)) {

			if (x1 < x2) {
//				distX1X2 = x2 - x1;
				x3 = x2; 
//				w3 = (x1 + w1) - x3; // Switched values
			} else {
//				distX1X2 = x1 - x2;
				x3 = x1; 
//				w3 = (x2 + w2) - x3;
			}

			if (y1 < y2) {
//				distY1Y2 = y2 - y1;
				y3 = y2; // Switched values
//				h3 = (y1 + h1) - y3;
			} else {
//				distY1Y2 = y1 - y2;
				y3 = y1;
//				h3 = (y2 + h2) - y3;
			}
			
			if ( x1 + w1 < x2 + w2) {
				w3 = (x1 + w1) - x3;
				
			} else {
				w3 = (x1 + w1) - x3;
			}
			
			if ( y1 + h1 < y2 + h2) {
				h3 = (y1 + h1) - y3;
				
			} else {
				h3 = (y1 + h1) - y3;
			}
			
			if(x3 <= 0) {
				System.out.println("X3 is negative!");
			}
			
			if(y3 <= 0) {
				System.out.println("Y3 is negative!");
			}
			
			if(w3 <= 0) {
				System.out.println("W3 is negative!");
			}
			
			if(h3 <= 0) {
				System.out.println("H3 is negative!");
			}
			//System.out.println("Grids Overlapped");

//			System.out.println(x1 + "\n" + y1 + "\n" + w1 + "\n" + h1);
//			System.out.println(x2 + "\n" + y2 + "\n" + w2 + "\n" + h2);
//			System.out.println(x3 + "\n" + y3 + "\n" + w3 + "\n" + h3);

			numOfGridSquares = w3 * h3;
//			System.out.println("Number of Grid Squares:" + numOfGridSquares);
			runningTotal += numOfGridSquares;
			
			for (int i = x3; i < x3 + w3; i++) {
//				System.out.println("Col:" + i);
				for (int j = y3; j < y3 + h3; j++) {
//					System.out.println("Row:" + j);
//					ArrayList<Integer> gridCoord = new ArrayList<Integer>();
//					gridCoord.add(i);
//					gridCoord.add(j);
//					gridSquares.add(gridCoord);
					cloth.set(i, j, true);
					if ((i * j) == numOfGridSquares) {
						System.out.println("Did this enough so that the grid squares are equal!");
					}
				}
			}
		} 

		// numOfGridSquares = w3 * h3;
		//
		// // System.out.println(x1 + "\n" + y1 + "\n" + w1 + "\n" + h1);
		// // System.out.println(x2 + "\n" + y2 + "\n" + w2 + "\n" + h2);
		// // System.out.println(x3 + "\n" + y3 + "\n" + w3 + "\n" + h3);
		// System.out.println("Number of Grid Squares:" + numOfGridSquares);
		//
		// for (int i = x3; i < x3 + w3; i++) {
		// System.out.println("Col:" + i);
		// for (int j = y3; j < y3 + h3; j++) {
		// System.out.println("Row:" + j);
		// Integer[] gridCoord = { i, j };
		// gridSquares.add(gridCoord);
		// }
		// }
		//
		// return gridSquares;

	}

}

package day3;

public class Cloth {

	private boolean[][] xY;
	private int alreadySet;

	public Cloth(int width, int height) {
		xY = new boolean[width][height];
		alreadySet = 0;
	}

	public void set(int xOff, int yOff, boolean value) {
		if (xY[xOff][yOff] == value) {
			alreadySet += 1;
		}
		xY[xOff][yOff] = value;
	}

	public boolean get(int xOff, int yOff) {
		
		return xY[xOff][yOff];

	}

	public int getAmountOfTrue() {
		int amount = 0;
		for (int i = 0; i < xY.length; i++) {
			for (int j = 0; j < xY.length; j++) {
				if (get(i, j)) {
					amount++;
				}
			}
		}
		System.out.println("Already set number is: " + alreadySet + " which taken away from all of the possible squares is: " + (205058 - alreadySet));
		return amount; //1000000
	}

}

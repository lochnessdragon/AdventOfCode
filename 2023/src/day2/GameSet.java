package day2;

public class GameSet {

	public int redCount;
	public int greenCount;
	public int blueCount;
	
	public GameSet(int redCount, int greenCount, int blueCount) {
		this.redCount = redCount;
		this.greenCount = greenCount;
		this.blueCount = blueCount;
	}

	public GameSet(String set) {
		redCount = 0;
		blueCount = 0;
		greenCount = 0;
		
		String[] parts = set.split(",");
		for (String part : parts) {
			part = part.trim();
			String[] separated = part.split(" ");
			int val = Integer.parseInt(separated[0]);
			switch (separated[1]) {
			case "red":
				redCount = val;
				break;
			case "blue":
				blueCount = val;
				break;
			case "green":
				greenCount = val;
				break;
			default:
				System.out.println("Failed to id: " + separated[1]);
			}
		}
	}
	
	public boolean isPossible(GameSet max) {
		return (max.redCount >= redCount && max.blueCount >= blueCount && max.greenCount >= greenCount);
	}
	
	public GameSet max(GameSet other) {
		return new GameSet(Math.max(redCount, other.redCount), Math.max(greenCount, other.greenCount), Math.max(blueCount, other.blueCount));
	}
	
	public int power() {
		return redCount * greenCount * blueCount;
	}
}

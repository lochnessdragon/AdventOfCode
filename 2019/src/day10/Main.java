package day10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import day5.Utils;

class Vector2 {
	public int x, y;
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public double getDistance(Vector2 other) {
		int diffX = this.x - other.x;
		int diffY = this.y - other.y;
		
		return Math.sqrt((diffY*diffY) + (diffX*diffX));
	}
	
	public String toString() {
		return "Vector: (" + this.x + ", " + this.y + ")";
	}
}

public class Main {

	public static void main(String[] args) {
		// part 1
		List<String> inputFile = Utils.loadFile("src/day10/input.txt");
		
		int[][] asteroids = new int[inputFile.size()][inputFile.get(0).trim().toCharArray().length];
		List<Vector2> asteroidLocations = new ArrayList<Vector2>();
		
		for(int y = 0; y < inputFile.size(); y++) {
			String line = inputFile.get(y);
			char[] characters = line.toCharArray();
			for(int x = 0; x < characters.length; x++) {
				if(characters[x] == '#') {
					asteroids[y][x] = 1;
					asteroidLocations.add(new Vector2(x, y));
				} else {
					asteroids[y][x] = 0;
				}
			}
		}
		
		printAsteroids(asteroids);
		
		int greatestVisibility = 0;
		Vector2 locOfStation = new Vector2(0, 0);
		
		for(Vector2 position : asteroidLocations) {
			int possibleVal = solvePosition(position, asteroidLocations);
			
			if(possibleVal > greatestVisibility) {
				greatestVisibility = possibleVal;
				locOfStation = position;
			}
		}
		
		System.out.println("[Part 1]: The greatest visibility is: " + greatestVisibility + " at location: (" + locOfStation.x + ", " + locOfStation.y + ")");
	
		// part 2
		asteroidLocations.remove(locOfStation);
		
		// get and translate radial positions
		Map<Double, Vector2> radialPositions = getRotations(locOfStation, asteroidLocations);
		
		double[] angles = radialPositions.keySet().stream().sorted().mapToDouble((value) -> {return value;}).toArray();
		Map<Double, Double> translatedAngles = new HashMap<Double, Double>();
		
		for(int i = 0; i < angles.length; i++) {
			double translatedAngle = angles[i] + 90;
			if(translatedAngle < 0) {
				translatedAngle = 360 + translatedAngle;
			}
			
			translatedAngles.put(translatedAngle, angles[i]);
			
			System.out.println((translatedAngle) + ": " + radialPositions.get(angles[i]));
		}
		
		int destroyedAsteroids = 0;
		Vector2 asteroid = new Vector2(0, 0);
		
		while(destroyedAsteroids < 200) {
			double angle = translatedAngles.keySet().stream().sorted().findFirst().get();
			
			asteroid = radialPositions.get(translatedAngles.get(angle));
			
			asteroidLocations.remove(asteroid);
			radialPositions.remove(translatedAngles.get(angle));
			translatedAngles.remove(angle);
			
			destroyedAsteroids++;
		}
		
		
		System.out.println("[Part 2]: The 200th asteroid is: " + ((asteroid.x * 100) + asteroid.y));
	}

	private static int solvePosition(Vector2 location, List<Vector2> asteroidLocations) {
		int asteroidsVisible = 0;
		
		Map<Double, Vector2> visiblePositions = getRotations(location, asteroidLocations);
		
		asteroidsVisible = visiblePositions.size();
		
		return asteroidsVisible;
	}
	
	private static Map<Double, Vector2> getRotations(Vector2 location, List<Vector2> asteroidLocations) {
		Map<Double, Vector2> visiblePositions = new HashMap<Double, Vector2>();
		
		for(Vector2 position : asteroidLocations) {
			if(position.x == location.x && position.y == location.y) {
				continue;
			}
			
			double uuid = Math.toDegrees(Math.atan2(position.y - location.y, position.x - location.x));
			
			Vector2 possibleVector = visiblePositions.get(uuid);
			
			if(possibleVector != null) {
				double distanceOld = location.getDistance(possibleVector);
				double distanceNew = location.getDistance(position);
				if(distanceNew < distanceOld) {
					visiblePositions.remove(uuid);
				}
			}
			
			visiblePositions.putIfAbsent(uuid, position);
		}
		
		return visiblePositions; 
	}

	private static void printAsteroids(int[][] asteroids) {
		for(int y = 0; y < asteroids.length; y++) {
			for(int x = 0; x < asteroids[y].length; x++) {
				if(asteroids[y][x] == 1) {
					System.out.print("#");
				} else {
					System.out.print(".");
				}
			}
			System.out.println();
		}
		
	}

}

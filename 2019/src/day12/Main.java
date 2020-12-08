package day12;

import java.util.ArrayList;
import java.util.List;

import day5.Utils;

class Vector3 {
	int x;
	int y;
	int z;
	
	public Vector3(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(Vector3 vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}

	public void add(Vector3 other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
	}
	
	public String toString() {
		return "<x=" + this.x + ", y=" + this.y + ", z=" + this.z+">";
	}
	
	public boolean equals(Object otherObject) {
		if(!(otherObject instanceof Vector3)) 
			return false;
		Vector3 otherVector = (Vector3) otherObject;
		if(otherVector.x == this.x && otherVector.y == this.y && otherVector.z == this.z)
			return true;
		return false;
	}
}

class Moon {
	Vector3 position;
	Vector3 velocity;
	
	public Moon(Vector3 position) {
		this.position = position;
		this.velocity = new Vector3(0, 0, 0);
	}

	public Moon(Moon moon) {
		this.position = new Vector3(moon.position);
		this.velocity = new Vector3(moon.velocity);
	}

	public void applyGravity(List<Moon> otherObjects) {
		
		// update velocity
		for(Moon moon : otherObjects) {
			if(moon.equals(this)) {
				continue;
			}
			
			velocity.x += getChangeVector(position.x, moon.position.x);
			velocity.y += getChangeVector(position.y, moon.position.y);
			velocity.z += getChangeVector(position.z, moon.position.z);
		}
	}
	
	public void applyVelocity() {
		position.add(velocity);
	}
	
	private int getChangeVector(int pos1, int pos2) {
		int vector = 0;
		
		if(pos1 < pos2) {
			vector = 1;
		} else if (pos1 > pos2) {
			vector = -1;
		}
		
		return vector;
	}

	public String toString() {
		return "pos=" + position.toString() + ", vel=" + velocity.toString();
	}
	
	public boolean equals(Object otherObject) {
		if(!(otherObject instanceof Moon))
			return false;
		Moon otherMoon = (Moon) otherObject;
		if(otherMoon.position.equals(this.position) && otherMoon.velocity.equals(this.velocity))
			return true;
		return false;
	}
	
	public int getPotentialEnergy() {
		return Math.abs(this.position.x) + Math.abs(this.position.y) + Math.abs(this.position.z);
	}
	
	public int getKinecticEnergy() {
		return Math.abs(this.velocity.x) + Math.abs(this.velocity.y) + Math.abs(this.velocity.z);
	}
	
	public int getTotalEnergy() {
		return getPotentialEnergy() * getKinecticEnergy();
	}
	
	public static int calculateTotalEnergy(List<Moon> moons) {
		int totalEnergy = 0;
		for(Moon moon : moons) {
			totalEnergy += moon.getTotalEnergy();
		}
		return totalEnergy;
	}
}

class Moon1d {
	int position;
	int velocity;
	
	public Moon1d(int position, int velocity) {
		this.position = position;
		this.velocity = velocity;
	}
	
	public Moon1d(Moon1d moon) {
		this.position = moon.position;
		this.velocity = moon.velocity;
	}

	public void applyGravity(List<Moon1d> otherObjects) {
		
		// update velocity
		for(Moon1d moon : otherObjects) {
			if(moon.equals(this)) {
				continue;
			}
			
			velocity += getChangeVector(this.position, moon.position);
		}
	}
	
	public void applyVelocity() {
		position += velocity;
	}
	
	private int getChangeVector(int pos1, int pos2) {
		int vector = 0;
		
		if(pos1 < pos2) {
			vector = 1;
		} else if (pos1 > pos2) {
			vector = -1;
		}
		
		return vector;
	}
	
	public boolean equals(Object otherObject) {
		if(!(otherObject instanceof Moon1d)) 
			return false;
		Moon1d otherMoon = (Moon1d) otherObject;
		
		if(otherMoon.position == this.position && otherMoon.velocity == this.velocity) {
			return true;
		}
		
		return false;
	}
	
}

public class Main {
	public static void main(String args[]) {
		List<String> input = Utils.loadFile("src/day12/input.txt");
		
		List<Moon> moons = new ArrayList<Moon>();
		
		for(String line : input) {
			line = line.replace('<', ' ');
			line = line.replace('>', ' ');
			
			line = line.trim();
			
			String[] parts = line.split(",");
			parts[0] = parts[0].replaceAll("x=", "");
			parts[1] = parts[1].replaceAll("y=", "");
			parts[2] = parts[2].replaceAll("z=", "");
			
			parts[0] = parts[0].trim();
			parts[1] = parts[1].trim();
			parts[2] = parts[2].trim();
			
			Moon moon = new Moon(new Vector3(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
			
			moons.add(moon);
		}
		
		//System.out.println("0 and 1 equal? " + moons.get(0).equals(moons.get(1)));
		
		// part 1
//		for(int i = 0; i < 11; i++) {
//			// display
//			System.out.println("After " + i + " steps:");
//			for(Moon moon : moons) {
//				System.out.println(moon);
//			}
//			System.out.println("Total Energy: " + Moon.calculateTotalEnergy(moons));
//			
//			// update
//			for(Moon moon : moons) {
//				moon.applyGravity(moons);
//			}
//			
//			for(Moon moon : moons) {
//				moon.applyVelocity();
//			}
//		}
		
		// part 2
		List<Moon1d> xMoons = new ArrayList<Moon1d>();
		
		for(Moon moon : moons) {
			xMoons.add(new Moon1d(moon.position.x, 0));
		}
		
		List<Moon1d> yMoons = new ArrayList<Moon1d>();
		
		for(Moon moon : moons) {
			yMoons.add(new Moon1d(moon.position.y, 0));
		}
		
		List<Moon1d> zMoons = new ArrayList<Moon1d>();
		
		for(Moon moon : moons) {
			zMoons.add(new Moon1d(moon.position.z, 0));
		}
		
		System.out.println("Calculating x dim");
		int timeX = runUntilRepeat(xMoons);
		System.out.println("Calculating y dim");
		int timeY = runUntilRepeat(yMoons);
		System.out.println("Calculating z dim");
		int timeZ = runUntilRepeat(zMoons);
		
		System.out.println("[Part 2]: Timestep: " + lcm(timeX, timeY, timeZ));
		
	}

	private static int runUntilRepeat(List<Moon1d> xMoons) {
		int timestep = 0;
		
		List<List<Moon1d>> oldMoons = new ArrayList<List<Moon1d>>();
		
		do {
			
			List<Moon1d> oldMoonList = new ArrayList<Moon1d>();
			
			for(Moon1d moon : xMoons) {
				oldMoonList.add(new Moon1d(moon));
			}
			
			oldMoons.add(oldMoonList);
			
			for(Moon1d moon : xMoons) {
				moon.applyGravity(xMoons);
			}
			
			for(Moon1d moon : xMoons) {
				moon.applyVelocity();
			}
			
			timestep++;
		} while(!oldMoons.contains(xMoons));
		
		return timestep;
	}

	private static long lcm(int a, int b, int c) {
		return lcm(lcm(a, b), c);
	}

	private static long lcm(long a, long b) {
		long gcd = 1;
		
		for (int i = 1; i <= a && i <= b; ++i) {
			// Checks if i is factor of both integers
			if (a % i == 0 && b % i == 0)
				gcd = i;
		}

		long lcm = (a * b) / gcd;

		return lcm;
	}
}

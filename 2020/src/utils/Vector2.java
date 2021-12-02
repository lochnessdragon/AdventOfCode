package utils;

public class Vector2 {
	public long x;
	public long y;
	
	public Vector2(long x, long y) {
		this.x = x;
		this.y = y;
	}

	public void rotateDegrees(double degrees) {
		this.rotateRadians(Math.toRadians(degrees));
	}
	
	public void rotateRadians(double radians) {
		long tempX = (long) ((x * Math.cos(radians)) - (y * Math.sin(radians)));
		long tempY = (long) ((x * Math.sin(radians)) + (y * Math.cos(radians)));
		
		this.x = tempX;
		this.y = tempY;
	}
	
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}

package day1;

public class Main {
	
	public static int START_NUMBER = 0;
	
	public static void main(String[] args) {
		
		Generator gen = new Generator(START_NUMBER);
		gen.begin();
		System.out.println("End Number: " + gen.getEndNum());
		
		
	}

}

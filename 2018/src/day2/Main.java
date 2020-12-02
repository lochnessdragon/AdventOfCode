package day2;

public class Main {

	public static void main(String[] args) {
		
		CheckIDGenerator generator = new CheckIDGenerator();
		generator.generate();
		System.out.println("This is the checkSum: " + generator.getCheckSum());
		System.out.println("There are the letters the two ids have in common: " + generator.getLettersOfIDs());
		System.out.println("Done!");

	}

}

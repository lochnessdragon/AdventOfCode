package day5;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	public static List<String> loadFile(String filename) {
		List<String> contents = new ArrayList<String>();
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(new File(filename)));
			
			while(in.ready()) {
				contents.add(in.readLine());
			}
			
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return contents;
	}

}

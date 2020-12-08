package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Resource {
	
	private String filename;
	
	public Resource(String filename) {
		this.filename = filename;
	}
	
	public List<String> read() {
		
		List<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.filename));
			
			while(reader.ready()) {
				lines.add(reader.readLine());
			}
			
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lines;
	}
}

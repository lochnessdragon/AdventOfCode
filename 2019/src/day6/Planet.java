package day6;

import java.util.ArrayList;
import java.util.List;

public class Planet {
	
	public String id;
	public Planet parent;
	public List<Planet> children;
	
	public Planet(String id, Planet parent) {
		this.id = id;
		this.parent = parent;
		this.children = new ArrayList<Planet>();
	}
	
	public void addChild(Planet planet) {
		children.add(planet);
	}
	
	public int getTotalOrbits() {
		if(this.parent == null) {
			return 0;
		}
		return parent.getTotalOrbits() + 1;
	} 
	
}

package day12;

import utils.Resource;

public class Main {

	public static void main(String[] args) {
		Resource input = new Resource("src/day12/input.txt");
		
		Ferry ferry = new Ferry(input.read());
		
		ferry.runCommands();
		
		System.out.println("[Part 1]: Distance: " + ferry.getManhattanDistance() + " Position: " + ferry.getPosition() + " Direction: " + ferry.getDirection());
		
		
		WaypointFerry waypointFerry = new WaypointFerry(input.read());
		
		waypointFerry.runCommands();
		
		System.out.println("[Part 2]: Distance: " + waypointFerry.getManhattanDistance() + " Position: " + waypointFerry.getPosition() + " Waypoint Offset: " + waypointFerry.getWaypoint());
	}

}

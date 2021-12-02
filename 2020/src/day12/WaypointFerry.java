package day12;

import java.util.List;

import utils.Vector2;

public class WaypointFerry extends Ferry {

	private Vector2 waypointLocation;
	
	public WaypointFerry(List<String> commands) {
		super(commands);
		this.waypointLocation = new Vector2(10, -1);
	}
	
	@Override
	public void runCommands() {
		for(String command : commands) {
			long commandArg = Long.parseLong(command.substring(1));
			
			char commandCode = command.charAt(0);
			
			switch(commandCode) {
			case 'N':
				waypointLocation.y -= commandArg;
				break;
			case 'S':
				waypointLocation.y += commandArg;
				break;
			case 'E':
				waypointLocation.x += commandArg;
				break;
			case 'W':
				waypointLocation.x -= commandArg;
				break;
			case 'F':
				position.x += waypointLocation.x * commandArg;
				position.y += waypointLocation.y * commandArg;
				break;
			case 'L':
				waypointLocation.rotateDegrees(-commandArg);
				System.out.println("Rotation: " + -commandArg + " Direction: " + this.waypointLocation);
				break;
			case 'R':
				waypointLocation.rotateDegrees(commandArg);
				System.out.println("Rotation: " + commandArg + " Direction: " + this.waypointLocation);
				break;
			}
		}
	}

	public Vector2 getWaypoint() {
		return this.waypointLocation;
	}

}

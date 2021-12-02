package day12;

import java.util.List;

import utils.Vector2;

public class Ferry {
	
	protected Vector2 position;
	protected Vector2 direction;
	
	protected List<String> commands;
	
	public Ferry(List<String> commands) {
		this.commands = commands;
		this.position = new Vector2(0, 0);
		this.direction = new Vector2(1, 0);
	}
	
	public void runCommands() {
		for(String command : commands) {
			long commandArg = Long.parseLong(command.substring(1));
			
			char commandCode = command.charAt(0);
			
			switch(commandCode) {
			case 'N':
				position.y -= commandArg;
				break;
			case 'S':
				position.y += commandArg;
				break;
			case 'E':
				position.x += commandArg;
				break;
			case 'W':
				position.x -= commandArg;
				break;
			case 'F':
				position.x += direction.x*commandArg;
				position.y += direction.y*commandArg;
				break;
			case 'L':
				direction.rotateDegrees(-commandArg);
				break;
			case 'R':
				direction.rotateDegrees(commandArg);
				break;
			}
		}
	}
	
	public long getManhattanDistance() {
		return (Math.abs(position.x)) + (Math.abs(position.y));
	}

	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getDirection() {
		return direction;
	}

}

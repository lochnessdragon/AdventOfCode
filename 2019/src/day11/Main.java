package day11;

import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import com.lochnessdragon.emulator.InputProvider;
import com.lochnessdragon.emulator.IntCodeComputer;
import com.lochnessdragon.emulator.OutputProvider;

import day5.Utils;

class Vector2 {
	public int x;
	public int y;
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}

class InputOutputManager {
	public Map<Integer, Map<Integer, Integer>> grid;
	public Vector2 position;
	
	int outputCount = 1;
	int direction = 0; // 0 = N, 1 = E, 2 = S, 3 = W
	
	public InputOutputManager() {
		this.grid = new HashMap<Integer, Map<Integer, Integer>>();
		this.position = new Vector2(0, 0);
		Map<Integer, Integer> valuesAt0 = new HashMap<Integer, Integer>();
		valuesAt0.put(0, 1);
		this.grid.put(position.x, valuesAt0); // first is white value
	}

	public InputProvider getInputManager() {
		return (index) -> {
			return grid.getOrDefault(position.x, new HashMap<Integer, Integer>()).getOrDefault(position.y, 0);
		};
	}

	public OutputProvider getOutputManager() {
		return (value, list) -> {
			list.add(value);
			
			if(outputCount % 2 == 0) {
				// rotation
				if(value == 0) {
					direction--;
				} else if(value == 1) {
					direction++;
				}
				
				if(direction < 0) {
					direction = 3;
				}
				
				if(direction > 3) {
					direction = 0;
				}
				
				// move forward
				int velX = 0;
				int velY = 0;
				switch(direction) {
				case 0:
					velY = -1;
					break;
				case 1:
					velX = 1;
					break;
				case 2:
					velY = 1;
					break;
				case 3:
					velX = -1;
					break;
				default:
					System.out.println("Unknown direction error");
				}
				
				position.x += velX;
				position.y += velY;
				
				if(position.x > 0 || position.y > 0) {
					System.out.println("They crossed the zero threshold!!!!! Position: " + this.position);
				}
				
				//System.out.println("Moving to: " + position);
				
			} else {
				//paint
				//System.out.println("Painting: " + position);
				
				Map<Integer, Integer> batch = grid.getOrDefault(position.x, new HashMap<Integer, Integer>());
				batch.put(position.y, (int) value);
				
				grid.put(position.x, batch);
				
				//System.out.println("Sizeof Grid: " + grid.size());
			}
			
			outputCount++;
			
			return list;
		};
	}
}

public class Main {
	
	public static void main(String args[]) {
		// part 1
		List<String> program = Utils.loadFile("src/day11/input.txt");
		
		InputOutputManager io = new InputOutputManager();
		
		IntCodeComputer robot = new IntCodeComputer(program.get(0));
		robot.setInput(io.getInputManager());
		robot.setOutput(io.getOutputManager());
		
		robot.runCpu();
		
		int computedSize = 0;
		for(Map<Integer, Integer> columns : io.grid.values()) {
			computedSize += columns.size();
		}
		
		System.out.println("[Part 1]: Number of painted tiles: " + computedSize);
		
		// part 2
		Vector2 leftMostPosition = new Vector2(0, 0);
		Vector2 rightMostPosition = new Vector2(0, 0);
		
		int largestColumnSize = 0;
		for(Integer key : io.grid.keySet()) {
			
			if(leftMostPosition.x > key) {
				leftMostPosition.x = key;
			}
			if(key > rightMostPosition.x) {
				rightMostPosition.x = key;
			}
			
			Map<Integer, Integer> column = io.grid.get(key);
			
			IntStream columnKeys = column.keySet().stream().sorted().mapToInt((value) -> {return value;});
			IntStream columnKeysCopy = column.keySet().stream().sorted().mapToInt((value) -> {return value;});
			
			int min = columnKeys.min().getAsInt();
			int max = columnKeysCopy.max().getAsInt();
			
			if(min < leftMostPosition.y) {
				leftMostPosition.y = min;
			}
			if(max > rightMostPosition.y) {
				rightMostPosition.y = max;
			}
		}
		
		largestColumnSize = Math.abs(leftMostPosition.y - rightMostPosition.y)+1;
		
		System.out.println("[Part 2]: Right most position: " + leftMostPosition + " Largest Row Size: " + io.grid.size() + " Largest Column Size: " + largestColumnSize);
		
		int[][] imageArray = new int[largestColumnSize][io.grid.size()];
		
		for(Integer x : io.grid.keySet()) {
			Map<Integer, Integer> column = io.grid.get(x);
			
			for(Integer y : column.keySet()) {
				int adjX = x - leftMostPosition.x;
				int adjY = y - leftMostPosition.y;
				System.out.println("X: " + x + " Y: " + y + " Adjusted: (" + adjX + ", " + adjY + ")");
				imageArray[adjY][adjX] = column.get(y);
			}
		}
		
		System.out.println("[Part 2]:");
		for(int i = 0; i < imageArray.length; i++) {
			for (int j = 0; j < imageArray[i].length; j++) {
				if(imageArray[i][j] == 0) {
					System.out.print(" ");
				} else {
					System.out.print("0");
				}
			}
			System.out.println();
		}
		System.out.println();
		
		
		int height = imageArray.length;
		int width = imageArray[0].length;
		
		byte[] imageBytes = new byte[height*width*3];
		
		for(int i = 0; i < imageArray.length; i++) {
			for (int j = 0; j < imageArray[i].length; j++) {
				if(imageArray[i][j] == 0) {
					imageBytes[(((i*width)+j)*3)] = (byte) 0x00;
					imageBytes[(((i*width)+j)*3)+1] = (byte) 0x00;
					imageBytes[(((i*width)+j)*3)+2] = (byte) 0x00;
				} else {
					imageBytes[(((i*width)+j)*3)] = (byte) 0xff;
					imageBytes[(((i*width)+j)*3)+1] = (byte) 0xff;
					imageBytes[(((i*width)+j)*3)+2] = (byte) 0xff;
				}
			}
		}
		
		DataBuffer buffer = new DataBufferByte(imageBytes, imageBytes.length);
		
		// 3 bytes per pixel
		WritableRaster raster = Raster.createInterleavedRaster(buffer, width, height, 3 * width, 3, new int[] {0, 1, 2}, (Point)null);
		ColorModel colorModel = new ComponentColorModel(ColorModel.getRGBdefault().getColorSpace(), false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
		BufferedImage image = new BufferedImage(colorModel, raster, true, null);
		
		try {
			ImageIO.write(image, "png", new File("day11.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

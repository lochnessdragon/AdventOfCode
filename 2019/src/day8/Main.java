package day8;

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
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import day5.Utils;

public class Main {
	public static void main(String args[]) {
		List<String> input = Utils.loadFile("src/day8/input.txt");
		
		int width = 25;
		int height = 6;
		
		// part 1
		List<Integer[][]> result = parseInput(input.get(0), width, height);
		
		printImage(result);
		
		int leastZeros = Integer.MAX_VALUE;
		int layerWithLeastZeros = 0;
		
		for(int i = 0; i < result.size(); i++) {
			Integer[][] array = result.get(i);
			int numberOfZeros = count(array, 0);
			if(numberOfZeros < leastZeros) {
				leastZeros = numberOfZeros;
				layerWithLeastZeros = i;
			}
		}
		
		System.out.println("[Part 1]: The layer with the least number of zeros is layer: " + layerWithLeastZeros + " with " + leastZeros + " zeros.");
		
		System.out.println("[Part 1]: This layer has a checksum of: " + (count(result.get(layerWithLeastZeros), 1) * count(result.get(layerWithLeastZeros), 2)));
		
		// part 2
		Integer[][] compiledImage = compileImage(result, width, height);
		
		System.out.println("[Part 2]:");
		for(int i = 0; i < compiledImage.length; i++) {
			for (int j = 0; j < compiledImage[i].length; j++) {
				if(compiledImage[i][j] == 0) {
					System.out.print(" ");
				} else {
					System.out.print("0");
				}
			}
			System.out.println();
		}
		System.out.println();
		 
		byte[] imageArray = new byte[25*6*3];
		for(int i = 0; i < compiledImage.length; i++) {
			for (int j = 0; j < compiledImage[i].length; j++) {
				if(compiledImage[i][j] == 0) {
					imageArray[(((i*width)+j)*3)] = (byte) 0x00;
					imageArray[(((i*width)+j)*3)+1] = (byte) 0x00;
					imageArray[(((i*width)+j)*3)+2] = (byte) 0x00;
				} else {
					imageArray[(((i*width)+j)*3)] = (byte) 0xff;
					imageArray[(((i*width)+j)*3)+1] = (byte) 0xff;
					imageArray[(((i*width)+j)*3)+2] = (byte) 0xff;
				}
			}
		}
		
		DataBuffer buffer = new DataBufferByte(imageArray, imageArray.length);
		
		// 3 bytes per pixel
		WritableRaster raster = Raster.createInterleavedRaster(buffer, width, height, 3 * width, 3, new int[] {0, 1, 2}, (Point)null);
		ColorModel colorModel = new ComponentColorModel(ColorModel.getRGBdefault().getColorSpace(), false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
		BufferedImage image = new BufferedImage(colorModel, raster, true, null);
		
		try {
			ImageIO.write(image, "png", new File("day8.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static Integer[][] compileImage(List<Integer[][]> array, int width, int height) {
		Integer[][] result = new Integer[height][width];
		
		for(int i = array.size(); i-- > 0;) {
			for(int j = 0; j < array.get(i).length; j++) {
				for(int k = 0; k < array.get(i)[j].length; k++) {
					int paint = array.get(i)[j][k];
					if(paint != 2) {
						result[j][k] = paint;
					}
				}
			}
		}
		
		return result;
	}

	private static int count(Integer[][] array, int number) {
		int count = 0;
		
		for(int i = 0; i < array.length; i++) {
			 for (int j = 0; j < array[i].length; j++) {
				 if(array[i][j] == number) {
					 count++;
				 }
			 }
		}
		
		return count;
	}

	private static void printImage(List<Integer[][]> list) {
		for(int i = 0; i < list.size(); i++) {
			 System.out.print("Layer " + i + ": ");
			 
			 for(int j = 0; j < list.get(i).length; j++) {
				 for (int k = 0; k < list.get(i)[j].length; k++) {
					 System.out.print(list.get(i)[j][k]);
				 }
				 System.out.print("\n\t");
			 }
			 System.out.println();
		}
		System.out.println();
	}

	public static List<Integer[][]> parseInput(String line, int width, int height) {
		char[] characters = line.toCharArray();
		List<Integer> numbers = new ArrayList<Integer>();
		
		for(char c : characters) {
			if('0' <= c && c <= '9') {
				numbers.add(Integer.parseInt(String.valueOf(c)));
			}
		}
		
		List<Integer[][]> returnValues = new ArrayList<Integer[][]>();
		while(!numbers.isEmpty()) {
			Integer[][] batch = new Integer[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					batch[i][j] = numbers.remove(0);
				}
			}
			
			returnValues.add(batch);
		}
		
		return returnValues;
	}
}

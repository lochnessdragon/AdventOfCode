package day3;

import java.util.*;

import day5.Utils;

class Main {
  public static void main(String[] args) {

	List<String> lines = Utils.loadFile("src/day3/input.txt");
	  
    String line1 = lines.get(0); 
    String line2 = lines.get(1);

    String[] splitLine1 = line1.split(",");
    String[] splitLine2 = line2.split(",");
    System.out.println(splitLine1[0]);

    Map<Integer, Map<Integer, Integer>> grid = new HashMap<Integer, Map<Integer, Integer>>();

    int intersection = 900;
    //Map<Integer, Map<Integer, Integer>> combinedStepsTaken = new HashMap<Integer, Map<Integer, Integer>>();

    int[] ptr = {0, 0};
    int stepsTaken = 0;
    for(String instruction : splitLine1) {
      char code = instruction.charAt(0);
      String value = instruction.substring(1);
      int length = Integer.parseInt(value);
      System.out.println(code);
      System.out.println(length);

      switch (code) {
        case 'R':
          System.out.println("Code is R");
          for(int i = 0; i < length; i++) {
        	  stepsTaken++;
        	  
            ptr[0]++;
            // grid[ptr[0]][ptr[1]] = 1;
            grid = putInGridSmallest(ptr, grid, stepsTaken);
          }
          break;
        case 'L':
          System.out.println("Code is L");
          for(int i = 0; i < length; i++) {
        	  stepsTaken++;
        	  
            ptr[0]--;
            // grid[ptr[0]][ptr[1]] = 1;
            grid = putInGridSmallest(ptr, grid, stepsTaken);
          }
          break;
        case 'U':
          System.out.println("Code is U");
          for(int i = 0; i < length; i++) {
        	  stepsTaken++;
        	  
            ptr[1]++;
            // grid[ptr[0]][ptr[1]] = 1;
            grid = putInGridSmallest(ptr, grid, stepsTaken);
          }
          break;
        case 'D':
          System.out.println("Code is D");
          for(int i = 0; i < length; i++) {
        	  stepsTaken++;
        	  
            ptr[1]--;
            // grid[ptr[0]][ptr[1]] = 1;
            grid = putInGridSmallest(ptr, grid, stepsTaken);
          }
          break;
        default:
          System.out.println("Could not identify code");
          break;
      }
    }

    ptr[0] = 0;
    ptr[1] = 0;
    stepsTaken = 0;
    int smallestStepsTaken = Integer.MAX_VALUE;
    
    int retrievedValue = 0;
    
    for(String instruction : splitLine2) {
      char code = instruction.charAt(0);
      String value = instruction.substring(1);
      int length = Integer.parseInt(value);
      System.out.println(code);
      System.out.println(length);

      switch (code) {
        case 'R':
          System.out.println("Code is R");
          for(int i = 0; i < length; i++) {
        	  stepsTaken++;
        	  
            ptr[0]++;
            
            retrievedValue = getValue(ptr, grid);
            
            if(retrievedValue != 0) {
              // grid[ptr[0]][ptr[1]] = 99;
            	
            	if((retrievedValue + stepsTaken) < smallestStepsTaken) {
            		smallestStepsTaken = (retrievedValue + stepsTaken);
            		
            		System.out.println("Value: " + retrievedValue);
            		System.out.println("Steps: " + stepsTaken);
            	}
            	
              //grid = putInGrid(ptr, grid, retrievedValue + stepsTaken);

              if((Math.abs(ptr[0]) + Math.abs(ptr[1])) < intersection) {
                intersection = (Math.abs(ptr[0]) + Math.abs(ptr[1]));
              }
            } else {
              // grid[ptr[0]][ptr[1]] = 2;
              //grid = putInGrid(ptr, grid, 2);
            }
          }
          break;
        case 'L':
          System.out.println("Code is L");
          for(int i = 0; i < length; i++) {
        	  stepsTaken++;
        	  
            ptr[0]--;
            
            retrievedValue = getValue(ptr, grid);
            
            if(retrievedValue != 0) {
            	
            	if((retrievedValue + stepsTaken) < smallestStepsTaken) {
            		smallestStepsTaken = (retrievedValue + stepsTaken);
            		
            		System.out.println("Value: " + retrievedValue);
            		System.out.println("Steps: " + stepsTaken);
            	}
            	
              // grid[ptr[0]][ptr[1]] = 99;
              //grid = putInGrid(ptr, grid, 99);

              if((Math.abs(ptr[0]) + Math.abs(ptr[1])) < intersection) {
                intersection = (Math.abs(ptr[0]) + Math.abs(ptr[1]));
              }
            } else {
              // grid[ptr[0]][ptr[1]] = 2;
              //grid = putInGrid(ptr, grid, 2);
            }
          }
          break;
        case 'U':
          System.out.println("Code is U");
          for(int i = 0; i < length; i++) {
        	  stepsTaken++;
        	  
            ptr[1]++;
            
            retrievedValue = getValue(ptr, grid);
            
            if(retrievedValue != 0) {
            	
            	if((retrievedValue + stepsTaken) < smallestStepsTaken) {
            		smallestStepsTaken = (retrievedValue + stepsTaken);
            		
            		System.out.println("Value: " + retrievedValue);
            		System.out.println("Steps: " + stepsTaken);
            	}
            	
              // grid[ptr[0]][ptr[1]] = 99;
              //grid = putInGrid(ptr, grid, 99);

              if((Math.abs(ptr[0]) + Math.abs(ptr[1])) < intersection) {
                intersection = (Math.abs(ptr[0]) + Math.abs(ptr[1]));
              }
            } else {
              // grid[ptr[0]][ptr[1]] = 2;
              //grid = putInGrid(ptr, grid, 2);
            }
          }
          break;
        case 'D':
          System.out.println("Code is D");
          for(int i = 0; i < length; i++) {
        	  stepsTaken++;
        	  
            ptr[1]--;
            
            retrievedValue = getValue(ptr, grid);
            
            if(retrievedValue != 0) {
            	
            	if((retrievedValue + stepsTaken) < smallestStepsTaken) {
            		smallestStepsTaken = (retrievedValue + stepsTaken);
            		System.out.println("Value: " + retrievedValue);
            		System.out.println("Steps: " + stepsTaken);
            	}
            	
              // grid[ptr[0]][ptr[1]] = 99;
              //grid = putInGrid(ptr, grid, 99);

              if((Math.abs(ptr[0]) + Math.abs(ptr[1])) < intersection) {
                intersection = (Math.abs(ptr[0]) + Math.abs(ptr[1]));
              }
            } else {
              // grid[ptr[0]][ptr[1]] = 2;
              //grid = putInGrid(ptr, grid, 2);
            }
          }
          break;
        default:
          System.out.println("Could not identify code");
          break;
      }
      
    }

    System.out.println("Smallest Distance: " + intersection);
    System.out.println("Smallest Steps Taken: " + smallestStepsTaken);
  }

  private static Map<Integer, Map<Integer, Integer>> putInGridSmallest(int[] ptr,
		Map<Integer, Map<Integer, Integer>> grid, int value) {
	if(getValueReturnMax(ptr, grid) <= value) {
	} else {
		grid = putInGrid(ptr, grid, value);
	}
	return grid;
}

public static Map<Integer, Map<Integer, Integer>> putInGrid(int[] ptr, Map<Integer, Map<Integer, Integer>> grid, int value) {

    Map<Integer, Integer> batch;

    if(grid.get(ptr[0]) == null) {
      batch = new HashMap<Integer, Integer>();
    } else {
      batch = grid.get(ptr[0]);
    }

    batch.put(ptr[1], value);
    grid.put(ptr[0], batch);

    return grid;
  }

  public static int getValue(int[] ptr, Map<Integer, Map<Integer, Integer>> grid) {
    if(grid.get(ptr[0]) == null) {
      return 0;
    } else if (grid.get(ptr[0]).get(ptr[1]) == null) {
      return 0;
    } else {
      return grid.get(ptr[0]).get(ptr[1]);
    }

  }
  
  public static int getValueReturnMax(int[] ptr, Map<Integer, Map<Integer, Integer>> grid) {
	    if(grid.get(ptr[0]) == null) {
	      return Integer.MAX_VALUE;
	    } else if (grid.get(ptr[0]).get(ptr[1]) == null) {
	      return Integer.MAX_VALUE;
	    } else {
	      return grid.get(ptr[0]).get(ptr[1]);
	    }

	  }

}
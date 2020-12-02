package day4;

class Main {
	  public static void main(String[] args) {
	    int numPasswords = 0;
	    
	    for(int i = 138241; i <= 674034; i++) {
	      if(checkValue(i)) {
	        numPasswords++;
	      }
	    }
	    
	    System.out.println(numPasswords);
//	    System.out.println(checkValue(111122));
	  }
	  
	  public static boolean checkValue(int value) {
	    
	    boolean incrementing = true;
	    boolean repeating = false;
	    
	    int[] digits = new int[6];
	    for(int i = 0; i < 6; i++) {
	      digits[5-i] = value % 10;
	      value = value / 10;
	      //System.out.println(value);
	    }
	    int repeatCount = 0;
	    int lastDigit = -1;
	    
	    for(int i = 0; i < digits.length; i++) {
	      if(digits[i] < lastDigit) {
	        incrementing = false;
	      } 
	      if (digits[i] == lastDigit) {
	        repeatCount++;
//	        System.out.println(repeatCount);
	      } else {
	    	  if(repeatCount == 1) {
	    		  repeating = true;
	    	  } 
	    	  repeatCount = 0;
	      }
	      lastDigit = digits[i];
	    }
	    if(repeating == false) {
	    	repeating = repeatCount == 1;
	    }
	    
	    return incrementing && repeating;
	  }
	}

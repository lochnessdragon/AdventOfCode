package com.lochnessdragon.emulator;

import java.util.HashMap;
import java.util.Map;

public class MemMap {
	private Map<Long, Long> mem;
	
	public MemMap(long[] initial) {
		this.mem = new HashMap<Long, Long>();
		
		for(int i = 0; i < initial.length; i++) {
			setValue(i, initial[i]);
		}
	}
	
	public long getValue(long address) {
		if(address < 0) {
			throw new ArrayIndexOutOfBoundsException((int) address);
		}
		
		return this.mem.getOrDefault(address, (long) 0);
	}
	
	public void setValue(long address, long value) {
		if(address < 0) {
			throw new ArrayIndexOutOfBoundsException((int) address);
		}
		
		this.mem.put(address, value);
	}
}

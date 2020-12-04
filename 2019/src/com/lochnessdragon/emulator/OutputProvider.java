package com.lochnessdragon.emulator;

import java.util.Queue;

public interface OutputProvider {
	public Queue<Long> out(long number, Queue<Long> previousOutputs);
}

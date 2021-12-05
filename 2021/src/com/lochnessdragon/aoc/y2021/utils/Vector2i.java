package com.lochnessdragon.aoc.y2021.utils;

public class Vector2i {
	public int x;
	public int y;
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2i difference(Vector2i vector2) {
		return new Vector2i(vector2.x - x, vector2.y - y);
	}
}

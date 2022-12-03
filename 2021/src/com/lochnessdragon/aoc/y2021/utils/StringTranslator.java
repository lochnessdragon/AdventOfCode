package com.lochnessdragon.aoc.y2021.utils;

import java.util.HashMap;
import java.util.Map;

public class StringTranslator {
	
	private Map<Character, Character> translateMap = new HashMap<Character, Character>();
	
	public StringTranslator(String from, String to) {
		if(from.length() != to.length()) {
			throw new NullPointerException("String Translator from and to cannot have different lengths");
		}
		
		char[] fromChars = from.toCharArray();
		
		char[] toChars = to.toCharArray();
		
		for(int i = 0; i < fromChars.length; i++) {
			translateMap.put(fromChars[i], toChars[i]);
		}
	}
	
	public char translate(char c) {
		return translateMap.get(c);
	}
	
	public String translate(String str) {
		char[] toTranslate = str.toCharArray();
		
		String newStr = "";
		
		for(char c : toTranslate) {
			newStr += translate(c);
		}
		
		return newStr;
	}
}
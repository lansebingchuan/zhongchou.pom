package com.testJava;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MapTest {
	
	@Test
	public void mapTest() {
		Map<Integer, Object> map = new HashMap<Integer, Object>();
		map.put(1, Arrays.asList(new int[] {1,2,3}));
		map.put(2, Arrays.asList(new int[] {1,2,3}));
		System.out.println(map.get(1));
	}
	
}

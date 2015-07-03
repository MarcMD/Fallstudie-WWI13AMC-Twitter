package com.wwi13amc.twitter_api.business_logic_api.mockUpData;

import java.util.HashMap;

public class HashMapMockUp {

	public static HashMap<String, Integer> getHashMap() {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("LH", 10);
		map.put("Air Canada", 20);
		map.put("4U", 5);

		return map;
	}

	public static HashMap<String, Integer> getHashMapForAirline(String airline) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		if (airline.equals("4U")) {
			map.put("Spanien", 100);
		} else {
			map.put("Deutschland", 20);
			map.put("Frankreich", 10);
			map.put("Spanien", 5);
		}

		return map;
	}
}

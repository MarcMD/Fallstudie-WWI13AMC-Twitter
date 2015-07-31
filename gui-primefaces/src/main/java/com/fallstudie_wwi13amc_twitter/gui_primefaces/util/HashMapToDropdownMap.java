package com.fallstudie_wwi13amc_twitter.gui_primefaces.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.primefaces.model.chart.PieChartModel;

public class HashMapToDropdownMap {


	public static Map<String, String> hashMapToDropdownMap(HashMap<String, Integer> map) {
		Map<String, String> dropdownMap = new HashMap<String, String>();
		Set<String> keySetFromMap = map.keySet();
		List<String> listOfKeys = new ArrayList<String>(keySetFromMap);
		
		for(int i=0; i<listOfKeys.size();i++) {
			dropdownMap.put(listOfKeys.get(i), listOfKeys.get(i));
		}
		
		dropdownMap = new TreeMap<String, String>(dropdownMap);
		
		return dropdownMap;
	}
}

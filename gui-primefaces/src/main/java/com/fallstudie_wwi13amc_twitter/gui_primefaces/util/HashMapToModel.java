package com.fallstudie_wwi13amc_twitter.gui_primefaces.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.primefaces.model.chart.PieChartModel;

public class HashMapToModel {

	public static PieChartModel hashMapToModel(HashMap<String, Integer> map) {
		PieChartModel pieChartModelObject = new PieChartModel();
		Set<String> keySetFromMap = map.keySet();
		List<String> listOfKeys = new ArrayList<String>(keySetFromMap);
		
		for(int i=0; i<listOfKeys.size();i++) {
			pieChartModelObject.set(listOfKeys.get(i), map.get(listOfKeys.get(i)));
		}
		
		return pieChartModelObject;
	}
	
}

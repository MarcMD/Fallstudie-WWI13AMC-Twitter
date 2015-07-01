package com.fallstudie_wwi13amc_twitter.gui_primefaces.view;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.PieChartModel;

import com.fallstudie_wwi13amc_twitter.gui_primefaces.util.HashMapToDropdownMap;
import com.fallstudie_wwi13amc_twitter.gui_primefaces.util.HashMapToModel;
import com.wwi13amc.twitter_api.business_logic_api.mockUpData.HashMapMockUp;

@ManagedBean(name="RegionModel")
@RequestScoped
public class RegionModel {
	
	private PieChartModel airlineModel; 
	private Map<String, String> dropdown; 
	private String selectedCountry;
	
	public PieChartModel getAirlineModel() {
		return airlineModel;
	}
	
	public void setAirlineModel(PieChartModel airlineModel) {
		this.airlineModel = airlineModel;
	}
	
	public Map<String, String> getDropdown() {
		return dropdown;
	}
	
	public void setDropdown(Map<String, String> dropdown) {
		this.dropdown = dropdown;
	}
	
	public String getSelectedCountry() {
		return selectedCountry;
	}
	
	public void setSelectedCountry(String selectedCountry) {
		this.selectedCountry = selectedCountry;
	}
	
	@PostConstruct
	public void init() {
		
		// Erhalt der HashMap vom Backend
		HashMap<String, Integer> hashMapFromBackend = HashMapMockUp.getHashMap();
		
		// Umwandlung der HashMap in ein PieChartmodel
		PieChartModel hashMapToPieChartModel = HashMapToModel.hashMapToModel(hashMapFromBackend);
		hashMapToPieChartModel.setLegendPosition("w");
		airlineModel = new PieChartModel();
		setAirlineModel(hashMapToPieChartModel);
		
		// Umwandlung der HashMap in eine Map mit dem Format Map<String, String>
		Map<String, String> hashMapToDropdownMap = HashMapToDropdownMap.hashMapToDropdownMap(hashMapFromBackend);
		setDropdown(hashMapToDropdownMap);
	}
	
	public void updateChart() {
		HashMap<String, Integer> hashMapFromBackend = HashMapMockUp.getHashMapForAirline(getSelectedCountry());
		PieChartModel hashMapToPieChartModel = HashMapToModel.hashMapToModel(hashMapFromBackend);
		hashMapToPieChartModel.setLegendPosition("w");
		airlineModel = new PieChartModel();
		setAirlineModel(hashMapToPieChartModel);
		
		Map<String, String> hashMapToDropdownMap = HashMapToDropdownMap.hashMapToDropdownMap(hashMapFromBackend);
		setDropdown(hashMapToDropdownMap);
	}
	
	
}

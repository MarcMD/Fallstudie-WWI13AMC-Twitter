package com.fallstudie_wwi13amc_twitter.gui_primefaces.view;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.PieChartModel;

import com.fallstudie_wwi13amc_twitter.gui_primefaces.util.HashMapToDropdownMap;
import com.fallstudie_wwi13amc_twitter.gui_primefaces.util.HashMapToModel;
import com.wwi13amc.twitter_api.business_logic_api.chartObjects.ObjectCreator;
import com.wwi13amc.twitter_api.business_logic_api.chartObjects.ThreadHandler;
import com.wwi13amc.twitter_api.business_logic_api.chartObjects.TwitterMessageCrawler;
import com.wwi13amc.twitter_api.business_logic_api.mockUpData.HashMapMockUp;

@ManagedBean(name="AirlineModel")
@RequestScoped
public class AirlineModel {

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
		// try {
			HashMap<String, Integer> hashMapFromBackend;
									
		// 	hashMapFromBackend = ObjectCreator.getHashMapforAirline("Alle");
	hashMapFromBackend = HashMapMockUp.getHashMap();
			PieChartModel hashMapToPieChartModel = HashMapToModel.hashMapToModel(hashMapFromBackend);
			hashMapToPieChartModel.setLegendPosition("w");
			airlineModel = new PieChartModel();
			setAirlineModel(hashMapToPieChartModel);
			
			Map<String, String> hashMapToDropdownMap = HashMapToDropdownMap.hashMapToDropdownMap(hashMapFromBackend);
			setDropdown(hashMapToDropdownMap);
		// }
//		catch(SQLException e) { 
//			System.out.println("Fehler in der Init-Datei der Klasse AirlineModel.java - SQLException: "+e.getMessage());
//		}
	}
	
	public void updateChart() {
		HashMap<String, Integer> hashMapFromBackend;
		try {
			hashMapFromBackend = ObjectCreator.getHashMapforAirline(getSelectedCountry());
			PieChartModel hashMapToPieChartModel = HashMapToModel.hashMapToModel(hashMapFromBackend);
			hashMapToPieChartModel.setLegendPosition("w");
			airlineModel = new PieChartModel();
			setAirlineModel(hashMapToPieChartModel);
			
			Map<String, String> hashMapToDropdownMap = HashMapToDropdownMap.hashMapToDropdownMap(hashMapFromBackend);
			setDropdown(hashMapToDropdownMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

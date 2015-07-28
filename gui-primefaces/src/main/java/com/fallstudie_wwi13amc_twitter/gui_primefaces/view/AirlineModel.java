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
	private String selectedAirline;
	
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
	
	public String getSelectedAirline() {
		return selectedAirline;
	}
	
	public void setSelectedAirline(String selectedAirline) {
		this.selectedAirline = selectedAirline;
	}
	
	@PostConstruct 
	public void init() {
		 
			HashMap<String, Integer> hashMapFromBackend = null;
		
			try {
				
				ThreadHandler threadHandler = new ThreadHandler();
			 	new TwitterMessageCrawler(threadHandler.airlinesArray);
				
			 	hashMapFromBackend = ObjectCreator.getHashMapforAirline("Alle");
				PieChartModel hashMapToPieChartModel = HashMapToModel.hashMapToModel(hashMapFromBackend);
		
			
				airlineModel = new PieChartModel();
				setAirlineModel(hashMapToPieChartModel);
				airlineModel.setLegendPosition("w");
				airlineModel.setLegendCols(3);

				hashMapFromBackend = null; 
				hashMapFromBackend = ObjectCreator.getHashMapforCountry("Weltweit");
				Map<String, String> hashMapToDropdownMap = HashMapToDropdownMap.hashMapToDropdownMap(hashMapFromBackend);
				setDropdown(hashMapToDropdownMap);
			} catch (SQLException e) {
				System.out.println("SQL Fehler beim Aufrufen des Airline Modells : " +e.getMessage()); 
				e.printStackTrace();
			}
			catch (Exception e) { 
				System.out.println("Sonstiger Fehler beim Generieren des Airline Modells: " +e.getMessage());
			}
		
	}
	
	public void updateChart() {
		HashMap<String, Integer> hashMapFromBackend;
		try {
			hashMapFromBackend = ObjectCreator.getHashMapforAirline(getSelectedAirline());
			PieChartModel hashMapToPieChartModel = HashMapToModel.hashMapToModel(hashMapFromBackend);
			hashMapToPieChartModel.setLegendPosition("w");
			airlineModel = new PieChartModel();
			setAirlineModel(hashMapToPieChartModel);
			
			Map<String, String> hashMapToDropdownMap = HashMapToDropdownMap.hashMapToDropdownMap(hashMapFromBackend);
			setDropdown(hashMapToDropdownMap);
		} catch (SQLException e) {
			System.out.println("Fehler bei der SQL-Abfrage zum Updaten des Airline Models:" +e.getMessage());
		}
		
	}
	
}

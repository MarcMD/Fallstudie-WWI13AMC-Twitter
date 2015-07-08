package com.wwi13amc.twitter_api.business_logic_api.chartObjects;

import java.util.ArrayList;

/**
 * @author christiankegelmann
 *
 */
public class ThreadHandler{
	
	/** 
	 * Notwendige Variablen werden deklariert.
	 * 
	 * @param airlines Beinhaltet die Airlinenamen, für weiteres Vorgehen im Konstruktor zu definieren.
	 * 
	 */
	
	static String[] airlinesArray;
	
	
	/**
	 * Konstruktor.
	 * @param airlines Erstellung ArrayList<String> für Airline-Namen
	 * 
	 */
	
	public ThreadHandler(){
		
		airlinesArray = new String[] {"Lufthansa", "#LH", "Germanwings", "#4U", "Austrian Airlines",
				"#OS", "Brussels Airlines", "#SN", "United Airlines", "#UA", "Air Canada", "#AC", 
				"Air Berlin", "#AB", "KLM Royal Dutch Airlines", "#KL", "British Airways", "#BA", "Swiss International Air Lines", "#LX"};
		
	
		
	}
		
	public static void main(String[] args) {
			
		new ThreadHandler();	
		
		TwitterMessageCrawler CrawlerLH = new TwitterMessageCrawler(airlinesArray);
		
		CrawlerLH.start();
		
	}			  
}

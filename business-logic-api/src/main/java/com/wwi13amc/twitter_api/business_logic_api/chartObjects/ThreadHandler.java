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
	
	static ArrayList<String> airlines;
	
	
	/**
	 * Konstruktor.
	 * @param airlines Erstellung ArrayList<String> für Airline-Namen
	 * 
	 */
	
	public ThreadHandler(){
		
		airlines = new ArrayList<String>();
		airlines.add("Lufthansa");
		airlines.add("Germanwings");
		airlines.add("Air France");
	}
		
	public static void main(String[] args) {
			
		new ThreadHandler();	
		
		TwitterMessageCrawler test4 = new TwitterMessageCrawler(airlines.get(0),"#LH");
		TwitterMessageCrawler test = new TwitterMessageCrawler(airlines.get(1),"#4U");
		TwitterMessageCrawler test2 = new TwitterMessageCrawler(airlines.get(2),"#AF");
		
		test4.start();
		test.start();
		test2.start();			
	}			  
}

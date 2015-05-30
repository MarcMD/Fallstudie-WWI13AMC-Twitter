package com.wwi13amc.twitter_api.business_logic_api.chartObjects;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ThreadHandler{
	
	/* 
	 * Notwendige Variablen werden deklariert.
	 * 
	 * @param timer Timer für zeitgesteuertes Event
	 * @param airlines Beinhaltet die Airlinenamen, für weiteres Vorgehen im Konstruktor zu definieren.
	 * @param array Beinhaltet die Zählerstande. airlines über Index mit array vernküpfbar.
	 * 
	 */
	
	Timer timer;
	static ArrayList<String> airlines;
	static ArrayList<Integer> array;
	
	
	/*
	 * Konstruktor.
	 * @param timer Erstellung zeitgesteuertes Event, Firerate 5 Sekunden
	 * @param airlines Erstellung ArrayList<String> für Airline-Namen
	 * @param array Erstellung ArrayList<Integer> für Zählerstände
	 * 
	 */
	
	public ThreadHandler(){
		
		//Timer wird erstellt, verknuepfte Methode run() wird alle 5 Sek aufgerufen
		timer = new Timer();
		timer.scheduleAtFixedRate(new RemindTask(),new Date(),5000); 
		airlines = new ArrayList<String>();
		airlines.add("Lufthansa");
		airlines.add("Germanwings");
		airlines.add("Air France");
		array = new ArrayList<Integer>();
		array.add(0);
		array.add(0);
		array.add(0);
		   
	}
	
	
	class RemindTask extends TimerTask {
		
		/*
		 * Erstellung TimerTask für Timer, Firerate 5 Sek.
		 * Angegebene Datei wird geöffnet, ausgelesen und beim Komma getrennt Dateiformat=(AIRLINE,VALUE).
		 * Value wird zu Integer geparsed und in Array array geschrieben.
		 * Diese Schritte werden für jedes Element in der ArrayList airlines wiederholt.
		 * 
		 * @see java.util.TimerTask#run()
		 * @param zeile Beinhaltet gelesene Zeile aus .txt-Dokument
		 * @param werte Beinhaltet Elemente aus Zeile, gesplitted bei ","
		 * @param zeilesplit Beinhaltet werte[1] als Integer
		 * @param array  Beinhaltet die Zählerstande. airlines über Index mit array vernküpfbar.
		 */

		public void run() {		   	
			for (int i = 0; i < airlines.size(); i++){
				try {
					FileReader fr = new FileReader("src/main/java/CounterStorage"+airlines.get(i)+".txt");
					BufferedReader reader = new BufferedReader(fr);
					String zeile = reader.readLine(); 
				
				    String werte[] = zeile.split(",");
				    int zeilesplit = Integer.parseInt(werte[1]);
				    array.set(i, zeilesplit);
				    System.out.println(zeile+" Value= "+zeilesplit);
				    reader.close();
				     
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Datei (noch) nicht verfügbar");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
		    }
		}
	}
	
	public static void main(String[] args) {
	
		new ThreadHandler();	
		
		TwitterMessageCrawler test4 = new TwitterMessageCrawler(airlines.get(0),"#LH", array.get(0));
		TwitterMessageCrawler test = new TwitterMessageCrawler(airlines.get(1),"#4U", array.get(1));
		TwitterMessageCrawler test2 = new TwitterMessageCrawler(airlines.get(2),"#AF", array.get(2));
		
		test4.start();
		test.start();
		test2.start();		
	}			  
}

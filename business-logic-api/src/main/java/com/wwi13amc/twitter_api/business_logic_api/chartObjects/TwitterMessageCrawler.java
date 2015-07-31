package com.wwi13amc.twitter_api.business_logic_api.chartObjects;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

public class TwitterMessageCrawler extends Thread{


	private String airlineCode ="leer";
	private String timeZone = "leer";
	private String identifier = "leer";
	private String[] airlines;
	private DBConnectionPool connector;
	
	public static boolean useList(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}
			
	public TwitterMessageCrawler(String[] a){
		airlines = a;
		//Ein Pool von Connections
		connector = new DBConnectionPool();
	}
		
	

	/**
	 * Folgender Code kann gethreaded werden.
	 * 
	 * @param airlineName Erster übergebener Wert, nach dem im JSON-String gesucht werden soll
	 * @param airlineCode Zweiter übergebener Wert, nach dem im JSON-String gesucht werden soll
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
				
		//Verbindung zu Twitter aufbauen
		String consumerKey = "5rr4ZRhdKYiQHGStqEr8Y7Vya";
		String consumerSecret = "4xWGyG64iM9L5A1RSn5pp408SdYU90MTT8F6A8U14VK5UtbR89";
		String token = "3320469549-v95Aaqf4DNuH7yAzbSrw98pT3EH7xBckZDCIzBO";
		String secret = "ygDHpE7OTcDdCAa7vrSNXwMUyHPPTZuxKm6LGUPgriWmd";
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		// add some track terms
		endpoint.trackTerms(Lists.newArrayList(airlines));

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
		// Authentication auth = new BasicAuth(username, password);

		// Create a new BasicClient. By default gzip is enabled.
		Client client = new ClientBuilder().hosts(Constants.STREAM_HOST).endpoint(endpoint).authentication(auth).processor(new StringDelimitedProcessor(queue)).build();

		 // Establish a connection
		client.connect();
	
		//Tweets nach Suchbegriffen filtern
		for (int msgRead = 0; msgRead < 10000; msgRead++) {

			String msg = null;
			try {
				msg = queue.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * In diesem Abschnitt wird die Nachricht aufgeteilt und bestimmte Elemente herausgesucht. 
			 * Die Werte werden dann in die Datenbank geschrieben.   
			 */
			
			if (msg.contains(airlines[0]) == true || msg.contains(airlines[1]) == true){
				 System.out.println(""+airlines[0]);
				 airlineCode = airlines[1];
			}
			else if (msg.contains(airlines[2]) == true || msg.contains(airlines[3]) == true){
				 System.out.println(""+airlines[2]);
				 airlineCode = airlines[3];
			}
			else if (msg.contains(airlines[4]) == true || msg.contains(airlines[5]) == true){
				 System.out.println(""+airlines[4]);
				 airlineCode = airlines[5];
			}
			else if (msg.contains(airlines[6]) == true || msg.contains(airlines[7]) == true){
				 System.out.println(""+airlines[6]);
				 airlineCode = airlines[7];
			}
			else if (msg.contains(airlines[8]) == true || msg.contains(airlines[9]) == true){
				 System.out.println(""+airlines[8]);
				 airlineCode = airlines[9];
			}
			else if (msg.contains(airlines[10]) == true || msg.contains(airlines[11]) == true){
				 System.out.println(""+airlines[10]);
				 airlineCode = airlines[11];
			}
			else if (msg.contains(airlines[12]) == true || msg.contains(airlines[13]) == true){
				 System.out.println(""+airlines[12]);
				 airlineCode = airlines[13];
			}
			else if (msg.contains(airlines[14]) == true || msg.contains(airlines[15]) == true){
				 System.out.println(""+airlines[14]);
				 airlineCode = airlines[15];
			}
			else if (msg.contains(airlines[16]) == true || msg.contains(airlines[17]) == true){
				 System.out.println(""+airlines[16]);
				 airlineCode = airlines[17];
			}
			else if (msg.contains(airlines[18]) == true || msg.contains(airlines[19]) == true){
				 System.out.println(""+airlines[18]);
				 airlineCode = airlines[19];
			}
				 String[] arrTZ = msg.split(",");
				 String[] arrTS = arrTZ;
					
				for (int i = 0; i < arrTZ.length; i++){
					if (arrTZ[i].contains("time_zone")){
						String[] hold = arrTZ[i].split(":");
						timeZone = hold[1].replaceAll("\"", "");
						System.out.println(timeZone);
					
					}
				}
				for (int i = 0; i < arrTS.length; i++){
					if (arrTS[i].contains("timestamp")){
						String[] hold2 = arrTS[i].split(":");
						String holder =hold2[1].replaceAll("\"", ""); 
						holder = holder.split("}")[0]; 
						
						identifier = holder;
						
						System.out.println(identifier);
						
					}
				}
				
				System.out.println(msg);
				System.out.println(airlineCode);
				System.out.println("");
				
				save();					
			
			
		}
		client.stop();	
	}
	
	/**
	 * Schreibt den Tweet mit seinen Eigenschaften (identifier, timeZone, airlineCode) 
	 * in die die Datenbank
	 */
	public void save() {
		String sql = "INSERT INTO fallstudie.TWEETS (ID, TIMEZONE, AIRLINE) " +
				"VALUES ('"+identifier+"', '"+timeZone+"', '"+airlineCode.substring(1)+"');";
		try {
			Connection con = connector.getConnection();
			Statement statement = con.createStatement();
			statement.execute(sql);		
			statement.close();
			con.close();
			System.out.println("Datenbankeintrag erfolgreich: " +identifier + " " + timeZone + " " + airlineCode.substring(1) + " wurde geschrieben");
		} catch (SQLException e) {
		}
	}
	
}

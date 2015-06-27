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

	private String airlineName;
	private String airlineCode;
	private String timeZone = "leer";
	private String identifier = "leer";
	
	private DBConnectionPool connector;
	
	public static boolean useList(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}
			
	public TwitterMessageCrawler(String airlineName, String airlineCode){
		this.airlineName = airlineName;
		this.airlineCode = airlineCode;
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
		String consumerKey = "syuSUS1kjr5VbUpW04ANNxRhJ";
		String consumerSecret = "teUcLlLaylfBRUPxLlv4VIrTOBvEnWIg8QqXTrplVqIaMOdZzN";
		String token = "347953670-KJfJTQ37bEYYkF89Bkdpl5BbVG9SDWt4WC5ex350";
		String secret = "LjK6hBAuXNWB8PoMKCGNOlMB7XcJ33OrZqJD7ySIHR8BZ";
		BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
		StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		// add some track terms
		endpoint.trackTerms(Lists.newArrayList(airlineName, airlineCode));

		Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
		// Authentication auth = new BasicAuth(username, password);

		// Create a new BasicClient. By default gzip is enabled.
		Client client = new ClientBuilder().hosts(Constants.STREAM_HOST).endpoint(endpoint).authentication(auth).processor(new StringDelimitedProcessor(queue)).build();

		 // Establish a connection
		client.connect();
	
		//Tweets nach Suchbegriffen filtern
		for (int msgRead = 0; msgRead < 1000000; msgRead++) {
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
			String[] arr = msg.split(" ");
			if (useList(arr, airlineName) == true || useList(arr, airlineCode) == true){
				 System.out.println(airlineName);
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
				System.out.println("");
				save();					
			}	
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
			e.printStackTrace();
		}
	}
	
}

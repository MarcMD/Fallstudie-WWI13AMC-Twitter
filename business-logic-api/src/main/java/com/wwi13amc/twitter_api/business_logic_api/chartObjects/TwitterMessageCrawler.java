package com.wwi13amc.twitter_api.business_logic_api.chartObjects;

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

public class TwitterMessageCrawler {
	
	public static int LH = 0;

	public static boolean useList(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}
	
	public static void main(String[] args) {
		String consumerKey = "syuSUS1kjr5VbUpW04ANNxRhJ";
		 String consumerSecret = "teUcLlLaylfBRUPxLlv4VIrTOBvEnWIg8QqXTrplVqIaMOdZzN";
		 String token = "347953670-KJfJTQ37bEYYkF89Bkdpl5BbVG9SDWt4WC5ex350";
		 String secret = "LjK6hBAuXNWB8PoMKCGNOlMB7XcJ33OrZqJD7ySIHR8BZ";
		 BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
		 StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
		 // add some track terms
		 endpoint.trackTerms(Lists.newArrayList("Lufthansa", "#LH"));

		 Authentication auth = new OAuth1(consumerKey, consumerSecret, token,
		 secret);
		 // Authentication auth = new BasicAuth(username, password);

		 // Create a new BasicClient. By default gzip is enabled.
		 Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
		 .endpoint(endpoint).authentication(auth)
		 .processor(new StringDelimitedProcessor(queue)).build();

		 // Establish a connection
		 client.connect();

		 // Do whatever needs to be done with messages
		 for (int msgRead = 0; msgRead < 1000; msgRead++) {
		 String msg = null;
		 try {
			msg = queue.take();
		 } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 
		 /*
		  * In diesem Abschnitt wird die Nachricht aufgeteilt und bestimmte Elemente herausgesucht,
		  * in diesem Fall "#LH" und "Lufthansa". Außerdem werden die entsprechenden Werte hochgezählt.
		  *  
		  */
		 String[] arr = msg.split(" ");
		 if (useList(arr, "#LH") == true){
			 LH++;
			 System.out.println("LH"+LH);
		 }
		 else if (useList(arr, "Lufthansa") == true){
			 LH++;
			 System.out.println("Lufthansa"+LH);
		 }
		
		 }

		 client.stop();

	}

}

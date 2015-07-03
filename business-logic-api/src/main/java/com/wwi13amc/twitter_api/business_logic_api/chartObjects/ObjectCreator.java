package com.wwi13amc.twitter_api.business_logic_api.chartObjects;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


public class ObjectCreator {

	public ObjectCreator() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws SQLException{
		
		
		HashMap<String, Integer> testMap = getHashMapforAirline("LH");
		
		Set keys = getHashMapforAirline("LH").keySet();
		Iterator iterator = keys.iterator();
		
		while(iterator.hasNext())
		{
			String country = (String) iterator.next();
			System.out.println(country);
			System.out.println(testMap.get((String) country));
		}
	}
	
	public static HashMap<String, Integer> getHashMapforAirline(String airline) throws SQLException{
		
		HashMap<String, Integer> anzahlTweetsEinerAirlineProLand = new HashMap<String, Integer>();
		
		DBConnectionPool DBConnection= new DBConnectionPool();
		Connection con = DBConnection.getConnection();
		Statement statement = con.createStatement();
		
		
		if(airline.equals("Alle")){
						
			String sqlQuery= "SELECT COUNT(*) AS COUNTER, AIRLINE FROM TWEETS_BY_COUNTRY GROUP BY AIRLINE";
			ResultSet rs = statement.executeQuery(sqlQuery);
			
			while(rs.next()){
				anzahlTweetsEinerAirlineProLand.put(rs.getString("AIRLINE"), rs.getInt("COUNTER"));
			}
			
			return anzahlTweetsEinerAirlineProLand;
			
		}else{
		
		String sqlQuery= "SELECT COUNT(*) AS COUNTER, COUNTRY FROM TWEETS_BY_COUNTRY WHERE AIRLINE='"+ airline +"' GROUP BY COUNTRY ORDER BY COUNTER DESC";
		
		ResultSet rs = statement.executeQuery(sqlQuery);
		
		while(rs.next())
		{
			anzahlTweetsEinerAirlineProLand.put(rs.getString("COUNTRY"), rs.getInt("COUNTER"));
		}
		
		return anzahlTweetsEinerAirlineProLand;
		}
	}

}

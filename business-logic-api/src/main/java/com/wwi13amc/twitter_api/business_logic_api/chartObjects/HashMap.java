package com.wwi13amc.twitter_api.business_logic_api.chartObjects;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class HashMap {

	public HashMap() {
		// TODO Auto-generated constructor stub
	}
	
	public void main (String[] args) throws SQLException{
		String sqlQuery= "SELECT COUNT(*) AS COUNTER, AIRLINE FROM TWEETS_BY_COUNTRY GROUP BY AIRLINE";
		
		DBConnectionPool DBConnection= new DBConnectionPool();
		Connection con = DBConnection.getConnection();
		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sqlQuery);
		
		while(rs.next()){
			
			System.out.println("Anzahl der Tweets:" + rs.getInt("COUNTER") +"von"+ rs.getString("AIRLINE"));
			
		}
		
	}

}

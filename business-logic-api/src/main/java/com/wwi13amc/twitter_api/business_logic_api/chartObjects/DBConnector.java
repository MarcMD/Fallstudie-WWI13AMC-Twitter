package com.wwi13amc.twitter_api.business_logic_api.chartObjects;

import java.beans.PropertyVetoException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.Connection;

/**
 * 
 * @author MarcMD
 * 
 */
public class DBConnector {

	java.sql.Connection con = null;
	Statement stmt = null;
	ComboPooledDataSource cpds = null;

	public DBConnector() {	
		
		try {
			cpds = new ComboPooledDataSource();
			
			cpds.setDriverClass("com.mysql.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://db4free.net:3306/fallstudie");
			cpds.setUser("twitter");
			cpds.setPassword("fallstudie");
			
			cpds.setMinPoolSize(3);
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(40);
			
			con = cpds.getConnection();
			stmt = con.createStatement();
			String sql = "SELECT * FROM TWEETS";
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				System.out.println(
						rs.getString("TIMESTAMP") + " " +
						rs.getString("COUNTRY")   + " " + 
						rs.getString("AIRLINE"));
			}
			rs.close();
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

	}

}

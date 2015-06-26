package com.wwi13amc.twitter_api.business_logic_api.chartObjects;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Connection;

/**
 * 
 * @author MarcMD
 * 
 */
public class DBConnector {

	Connection con = null;
	Statement stmt = null;

	public DBConnector() {
		try {
			con = (Connection) DriverManager.getConnection(
					"jdbc:mysql://db4free.net:3306/fallstudie", "twitter",
					"fallstudie");
			System.out.println("DB Connection established");
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
		}

	}

}

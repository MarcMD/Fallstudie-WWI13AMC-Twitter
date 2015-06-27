package com.wwi13amc.twitter_api.business_logic_api.chartObjects;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * In dieser Klasse wird ein Pool von Connections zur Datenbank verwaltet. Mit
 * der Methode getConnection kann auf eine Verbindung zugegriffen werden
 * 
 * Verbindungen sollten geschlossen werden, wenn sie nicht mehr benötigt werden:
 * con.close();
 * 
 * @author MarcMD
 * 
 */
public class DBConnectionPool {

	private ComboPooledDataSource cpds = null;

	/**
	 * Kontruktor
	 */
	public DBConnectionPool() {

		try {
			cpds = new ComboPooledDataSource();

			cpds.setDriverClass("com.mysql.jdbc.Driver");
			cpds.setJdbcUrl("jdbc:mysql://db4free.net:3306/fallstudie");
			cpds.setUser("twitter");
			cpds.setPassword("fallstudie");

			cpds.setMinPoolSize(3);
			cpds.setAcquireIncrement(5);
			cpds.setMaxPoolSize(20);
			
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Eine Connection des Pools kann mit dieser Methode abgerufen werden
	 * 
	 * @return eine Connection zur Datenbank
	 * @throws SQLException
	 */
	public java.sql.Connection getConnection() throws SQLException {
		return this.cpds.getConnection();
	}

}

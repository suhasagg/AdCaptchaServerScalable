package com.tcaptcha.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import snaq.db.ConnectionPool;

import com.mysql.jdbc.Driver;
import com.tcaptcha.GlobalConfiguration;


public class DbConnector {

	private static String hostIP="hostIP"; 
	private static String port="port"; 
	private static String databaseName="databaseName";
	private static String username="userName";
	private static String password="password";
	private static Connection mysqlConnection = null;
	private static final Logger jdbclogger = Logger.getLogger(DbConnector.class);

	public  Connection getJDBCMySQLConnection() {

		if (mysqlConnection == null)
			mysqlConnection = createJDBCMySQLConnection();
		else
			return mysqlConnection;

		return mysqlConnection;

	}

	public  Connection createJDBCMySQLConnection() {

		Connection connection = null;
		try {
			Driver driver = (Driver)Class.forName("com.mysql.jdbc.Driver").newInstance();
			DriverManager.registerDriver(driver);
		
			hostIP=GlobalConfiguration.get(hostIP);
			port=GlobalConfiguration.get(port);
			databaseName=GlobalConfiguration.get(databaseName);
			username=GlobalConfiguration.get(username);
			password=GlobalConfiguration.get(password);
			jdbclogger.debug("Trying to connect MySQL on m/c:" + hostIP+ " on PORT:" + port);
			String url = "jdbc:mysql://" + hostIP + ":" + port + "/"+ databaseName;
			ConnectionPool pool = new ConnectionPool("TCaptchaPool",5,10,30,100,url,username,password);
			long timeout = 3000;  // 3 second timeout
			connection = pool.getConnection(timeout);
			if(connection!=null)
				return connection;
			else{
				jdbclogger.info("Connection Timeout...");
			}
		} catch (InstantiationException e) {
			jdbclogger.debug("DbConnector.getJDBCMySQLConnection() INSTANTIATION EXCEPTION!!!");
		} catch (IllegalAccessException e) {
			jdbclogger.error("DbConnector.getJDBCMySQLConnection() ILLEGAL ACCESS EXCEPTION!!!");
		} catch (ClassNotFoundException e) {
			jdbclogger.error("DbConnector.getJDBCMySQLConnection() CLASS NOT FOUND EXCEPTION!!!");
		} catch (SQLException e) {
			jdbclogger.error("DbConnector.getJDBCMySQLConnection() SQLEXCEPTION!!!");
		}

		if (connection == null){
			jdbclogger.error("DbConnector.getJDBCMySQLConnection() UNABLE TO GET JDBC CONNECTION!!!");
		}
		return connection;
	}

	public static  void closeResources(Connection connection,
			PreparedStatement preparedStatement, Statement statement,
			ResultSet resultSet) {

		if (connection != null) {
			try {
				connection.close();
				if(mysqlConnection!=null)
					mysqlConnection=null;
				
			} catch (SQLException e) {
				jdbclogger.error(e.getMessage());
			}
		}

		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				jdbclogger.error(e.getMessage());
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				jdbclogger.error(e.getMessage());
			}
		}

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				jdbclogger.error(e.getMessage());
			}
		}
	}
}

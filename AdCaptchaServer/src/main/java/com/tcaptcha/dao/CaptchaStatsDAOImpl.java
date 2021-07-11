package com.tcaptcha.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.tcaptcha.CaptchaConstants;
import com.tcaptcha.util.DbConnector;



public class CaptchaStatsDAOImpl implements CaptchaStatsDAO {
	
	private CaptchaStatsDAOImpl(){}
	
	private static CaptchaStatsDAO INSTANCE = new CaptchaStatsDAOImpl();
	
	private static final Logger logger = Logger.getLogger(CaptchaStatsDAOImpl.class);
	
	public static CaptchaStatsDAO getInstance() {
		return INSTANCE ;
	}

	@Override
	public void insertCaptchaStatsForKey(String key, String imageId) {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try{
			DbConnector obj=new DbConnector();
			connection=obj.getJDBCMySQLConnection();
			
			if(connection!=null){
				preparedStatement=connection.prepareStatement("INSERT INTO CAPTCHA_IMAGE_STATS " +
						" (CAPTCHA_KEY, IMAGE_ID, DATE ) VALUES (?,?,?)");
				
				preparedStatement.setString(1, key);
				preparedStatement.setString(2, imageId);
				preparedStatement.setTimestamp(3, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
				
				preparedStatement.execute();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		/*
		finally{
			DbConnector.closeResources(connection, preparedStatement, null, resultSet);
		}
        */
	}

	@Override
	public void updateCaptchaStatsForKey(String key, String code,
			boolean success, boolean expired) {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try{
			DbConnector obj=new DbConnector();
			connection=obj.getJDBCMySQLConnection();
			
			if(connection!=null){
				preparedStatement=connection.prepareStatement("UPDATE CAPTCHA_IMAGE_STATS SET CODE = ?, SUCCESS = ?, EXPIRED = ?,  WHERE CAPTCHA_KEY = ? ") ;
				
				preparedStatement.setString(1, code);
				preparedStatement.setString(2, success ? CaptchaConstants.YES : CaptchaConstants.NO);
				preparedStatement.setString(3, expired ? CaptchaConstants.YES : CaptchaConstants.NO);
				preparedStatement.setString(4, key);
				
				preparedStatement.execute();
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		/*
		finally{
			DbConnector.closeResources(connection, preparedStatement, null, resultSet);
		}
        */
	}

	@Override
	public void insertCaptchaStatsForCode(String key, String imageId,
			String code, boolean success, boolean expired) {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String campaignId = "1";
		try{
			DbConnector obj=new DbConnector();
			connection=obj.getJDBCMySQLConnection();
			
			if(connection!=null){
				preparedStatement=connection.prepareStatement("INSERT INTO CAPTCHA_IMAGE_STATS " +
						" (CAPTCHA_KEY, IMAGE_ID, CODE, SUCCESS, EXPIRED, DATE, CAMPAIGN_ID ) VALUES (?,?,?,?,?,?,?)");
				
				preparedStatement.setString(1, key);
				preparedStatement.setString(2, imageId);
				preparedStatement.setString(3, code);
				preparedStatement.setString(4, success ? CaptchaConstants.YES : CaptchaConstants.NO);
				preparedStatement.setString(5, expired ? CaptchaConstants.YES : CaptchaConstants.NO);
				preparedStatement.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
				preparedStatement.setString(7, campaignId);
				preparedStatement.execute();
				logger.info("Captcha Analytics inserted in database");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		/*
		finally{
			DbConnector.closeResources(connection, preparedStatement, null, resultSet);
		}
         */
		
	}

}

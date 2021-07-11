package com.tcaptcha.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.tcaptcha.util.DbConnector;


public class CaptchaDAOImpl implements CaptchaDAO {
	
	private static final Logger logger = Logger.getLogger(CaptchaDAOImpl.class);
	
	private CaptchaDAOImpl(){}
	
	private static CaptchaDAO INSTANCE = new CaptchaDAOImpl();
	
	public static CaptchaDAO getInstance() {
		return INSTANCE ;
	}

	@Override
	public byte[] getImageById(String id) {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		byte[] image = new byte[0];
		
		try{
			DbConnector obj=new DbConnector();
			connection=obj.getJDBCMySQLConnection();
			
			if(connection!=null){
				preparedStatement=connection.prepareStatement("SELECT IMAGE FROM CAPTCHA_IMAGE WHERE ID = ?  ") ;
				
				preparedStatement.setString(1, id);
				
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					image = resultSet.getBytes(1);
				}
				
				logger.info("Image Fetched from Database");
				//	connection.commit();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		/*
		finally{
			DbConnector.closeResources(connection, preparedStatement, null, resultSet);
		}
		*/
		return image;
	}

	@Override
	public String getImageCode(String id) {
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String campaignId = null;
		String imageCode = null;
		Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
		
		try{
			DbConnector obj=new DbConnector();
			connection=obj.getJDBCMySQLConnection();
			
			if(connection!=null){
				preparedStatement=connection.prepareStatement("SELECT CAMPAIGN_ID, CODE FROM CAPTCHA_IMAGE WHERE ID = ? ");
				preparedStatement.setString(1, id);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					campaignId = resultSet.getString(1);
			//		String imageId = resultSet.getString(2);
					imageCode = resultSet.getString(2);
					}
				logger.info("Code Fetched from Database");
			//	connection.commit();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		/*
		finally{
			DbConnector.closeResources(connection, preparedStatement, null, resultSet);
		}
		*/
		return imageCode;
	}

}


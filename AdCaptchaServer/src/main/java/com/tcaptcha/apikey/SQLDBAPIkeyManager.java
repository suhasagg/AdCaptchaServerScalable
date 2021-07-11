/**
 * 
 */
package com.tcaptcha.apikey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tcaptcha.GlobalConfiguration;
import com.tcaptcha.util.DbConnector;
import com.tcaptcha.util.UUIDGenerator;


/**
 * Api key manager, a Sqldb implementation. SQLDB was design to be an
 * easy-config and embedded lightweight database. This class takes advantages of
 * these features, there is no configuration except a filepath requiered to run.
 * 
 * 
 * 
 * 
 * 
 */

public class SQLDBAPIkeyManager implements APIKeyManager {

	private final Logger logger = Logger.getLogger(SQLDBAPIkeyManager.class);

	/**
	 * Connection pool, data source
	 */
	
   private static  SQLDBAPIkeyManager INSTANCE = new SQLDBAPIkeyManager();
	
	public static  SQLDBAPIkeyManager getInstance() {
		return INSTANCE ;
	}

	   
	
	
	/**
	 * sqldb jdbc connection url It's a file based standalone database, file
	 * path and name is defined in global configuration file
	 */
	private static final String CONNECTION_URL = "jdbc:hsqldb:"
			+GlobalConfiguration.get("tcaptcha.apikey.dbfilename")
			+ ";shutdown=true";

	/**
	 * sqldb default user name
	 */
	private static final String SQLDB_USERNAME = "sa";

	/**
	 * sqldb default password
	 */
	private static final String SQLDB_PASSWORD = "";

	/**
	 * constructor, init all
	 */
	
	public SQLDBAPIkeyManager(){
		init();
		DbConnector obj=new DbConnector();
		Connection connection=obj.getJDBCMySQLConnection();
	//	testAndCreateDdl(connection);
	}

	/**
	 * Initialize data source, connection pool size was set to default
	 * 
	 */
	protected void init() {
		/*
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
		dataSource.setUrl(CONNECTION_URL);
		dataSource.setUsername(SQLDB_USERNAME);
		dataSource.setPassword(SQLDB_PASSWORD);
	   */
	}

	/**
	 * generate ddl if table does not exist.
	 * 
	 * @param conn
	 *            a connection instance
	 */

   /*   protected void testAndCreateDdl(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT count(*) FROM api_key");
			rs.next();
			logger.info("Total Stored Applications: "+ String.valueOf(rs.getLong(1)));

		} catch (SQLException e) {
			logger.info("Table Not Found, Creating new api_key table");
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				stmt.execute(
						     "CREATE TABLE api_key("
								+ "id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,"
								+ "app_name VARCHAR(255) NOT NULL,"
								+ "key VARCHAR(255) NOT NULL" + ")");
				stmt.execute("CREATE INDEX key_index ON api_key(key)");
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				try {
					stmt.close();
				} catch (Exception ex) {
				}
                try{
                    conn.close();
                }catch(Exception ex){}
			}
			logger.info("Table created");
		}
	}
*/
	/*
	 * 
	 * 
	 * 
	 */
	@Override
	public String getApp(String apikey) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			DbConnector obj=new DbConnector();
			connection=obj.getJDBCMySQLConnection();
			stmt = connection.prepareStatement("SELECT app_name FROM api_key WHERE key = ?");
			stmt.setString(1, apikey);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			logger.error(null, e);
			return null;
		} finally {
			try {
				rs.close();
			} catch (Exception ex) {
			}
			try {
				stmt.close();
			} catch (Exception ex) {
			}
            try{
                connection.close();
            }catch(Exception ex){}
		}
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	@Override
	public synchronized String register(String appName) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			String apikey = UUIDGenerator.generate(appName);
			DbConnector obj=new DbConnector();
			connection=obj.getJDBCMySQLConnection();
			stmt = connection.prepareStatement("INSERT INTO api_key(app_name, key) VALUES(?, ?)");
			stmt.setString(1, appName);
			stmt.setString(2, apikey);
			stmt.execute();
			return apikey;
		} catch (SQLException e) {
			logger.error(null, e);
			return null;
		} finally {
			try {
				stmt.close();
			} catch (Exception ex) {
			}
            try{
                connection.close();
            }catch(Exception ex){}
		}
	}

   /**
    * delete
    * @param id
    */
	@Override
	public void delete(long id) {
		Connection connection = null;
		PreparedStatement stmt = null;
		try {
			DbConnector obj=new DbConnector();
			connection=obj.getJDBCMySQLConnection();
			stmt = connection.prepareStatement("DELETE FROM api_key WHERE id = ?");
			stmt.setLong(1, id);
			stmt.execute();
		} catch (SQLException e) {
			logger.error(null, e);
		} finally {
			try {
				stmt.close();
			} catch (Exception ex) {
			}
            try{
                connection.close();
            }catch(Exception ex){}
		}

	}

    /**
     * list
     * @return
     */
	@Override
	public List<ApiKeyEntry> list() {
		Connection connection = null;
		Statement stmt = null;
		List<ApiKeyEntry> results = new ArrayList<ApiKeyEntry>();
		try {
			DbConnector obj=new DbConnector();
			connection=obj.getJDBCMySQLConnection();
			stmt = connection.createStatement();
			stmt.executeQuery("SELECT * FROM api_key;");
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				ApiKeyEntry e = new ApiKeyEntry();
				e.apikey = rs.getString("key");
				e.appName = rs.getString("app_name");
				e.id = rs.getLong("id");

				results.add(e);
			}
			return results;
		} catch (SQLException e) {
			logger.error(null, e);
			return null;
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
            try{
                connection.close();
            }catch(Exception ex){}
		}
	}

}

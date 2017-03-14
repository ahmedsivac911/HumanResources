/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zira.humanresources.models;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;
import javax.sql.PooledConnection;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;

/**
 *
 * @author Ahmed_S
 */
public class DBUtil {
    //Oracle connection strings initilization
    private static OracleConnectionPoolDataSource ds;
    private static PooledConnection pConn;
    private static Connection dbConn;
    
    public static Connection getConnection() throws SQLException{
        Locale.setDefault(Locale.US);
        ds = new OracleConnectionPoolDataSource();
        //load properties from xml file
        Properties props = getDBProperties();
        //set datasource args
        ds.setDriverType(props.getProperty("driverType"));
        ds.setServerName(props.getProperty("serverName"));
        ds.setUser(props.getProperty("user"));
        ds.setPassword(props.getProperty("password"));
        ds.setPortNumber(Integer.parseInt(props.getProperty("portNumber")));
        ds.setServiceName(props.getProperty("serviceName"));
        //get connection
        pConn = ds.getPooledConnection();
        dbConn = pConn.getConnection();
        return dbConn;
    }
    
    public static void showExceptionMessage(Exception e){
        System.err.println("Something went wrong with using database: " + e.getMessage());
    }
    
    private static Properties getDBProperties(){
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("connectionProperties.xml"))){
            props.loadFromXML(in);
            return props;
        } catch(IOException e){
            System.out.println("Couldn't load properties from file: " + e.getMessage());
        }
        return null;
    }
    
    public static void closeConnection() {
        // closes only Connection conn but PooledConnection pConn stays open for performance improvement
        try{
            dbConn.close();
        }catch(SQLException e){
            showExceptionMessage(e);
        }
    }
    
    public static void closeAllConnections(){
        // closes all Connections and PooledConnections, should be used at the end of usage
        try {
            closeConnection();
            pConn.close();
        }catch(SQLException e){
            showExceptionMessage(e);
        }
    }
    
    
}

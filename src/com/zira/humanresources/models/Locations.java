/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zira.humanresources.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ahmed_S
 */


//for the sake of simplicity, this class only gets locations
//locations aren't changed often anyway so db admin can change them directly via sql editor
//this class is used to check wether we can change location of the departmet to one already existing
public class Locations {
    
    public static Location getLocationById(int locationId){
        boolean exists = false;
        
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement("SELECT * FROM LOCATIONS WHERE LOCATION_ID = ?");
            PreparedStatement pstmt2 = dbConn.prepareStatement("SELECT COUNTRY_NAME FROM COUNTRIES WHERE COUNTRY_ID = ?"))
        {
            pstmt.setInt(1, locationId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                
                int id = rs.getInt("LOCATION_ID");
                String address = rs.getString("STREET_ADDRESS");
                String city = rs.getString("CITY");
                String country = "";
                
                pstmt2.setString(1, rs.getString("COUNTRY_ID"));
                ResultSet rs2 = pstmt2.executeQuery();
                if(rs2.next())
                    country = rs2.getString("COUNTRY_NAME");
                
                return new Location(id, address, city, country);
            }
        }catch (SQLException ex) {
            DBUtil.showExceptionMessage(ex);
        }
        return null;
    }
}

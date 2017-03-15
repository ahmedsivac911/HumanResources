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
public class Departments {
    
    public static Department getDepartmentByName (String departmentName){
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement("SELECT * FROM DEPARTMENTS WHERE DEPARTMENT_NAME = ?"))
        {
            pstmt.setString(1, departmentName);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return createDepartmentFromRow(rs);
        }catch(SQLException e){
            DBUtil.showExceptionMessage(e);
        }
        return null;
    }
    
    public static Department getDepartmentById (int departmentId){
        try (Connection dbConn = DBUtil.getConnection();
             PreparedStatement pstmt = dbConn.prepareStatement("SELECT * FROM DEPARTMENTS WHERE DEPARTMENT_ID = ?"))
        {
            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next())
                return createDepartmentFromRow(rs);
        }catch(SQLException e){
            DBUtil.showExceptionMessage(e);
        }
        return null;
    }

    private static Department createDepartmentFromRow(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("DEPARTMENT_ID"), rs.getString("DEPARTMENT_NAME"), rs.getInt("MANAGER_ID"), rs.getInt("LOCATION_ID"));
    }

    static void updateDepartment(Department department) throws SQLException {
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement("UPDATE DEPARTMENTS SET"
                    + " MANAGER_ID = ?, LOCATION_ID = ? "
                    + " WHERE DEPARTMENT_ID = ?"))
        {
            pstmt.setInt(1, department.getManager().getId());
            pstmt.setInt(2, department.getLocation().getId());
            pstmt.setInt(3, department.getDepartmentId());
            int result = pstmt.executeUpdate();
            if(result <= 0)
                throw new SQLException("Couldn't update department.");
        }
    } 
}

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
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed_S
 */
public class Employees {

    static boolean storeEmployee(Employee employee) {
        //checks if id of employee is 0
        //that means that this is a new employee so his id should be auto genarated by oracle db
        //else means this employee exists so we just update his data
        int id = employee.getId();
        boolean result;
        if(id == 0){
            result = storeNewEmployee(employee);
            return result;
        }
        else{
            result = updateEmployee(employee);
            return result;
        }
    }

    private static boolean storeNewEmployee(Employee employee) {
        String sqlPrepareString = "INSERT INTO EMPLOYEES (FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, SALARY, "
                + "DEPARTMENT_ID, JOB_ID) VALUES ?,?,?,?,?,?,?";
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement(sqlPrepareString);
                ){
            setPreparedStatementFromEmployee(pstmt, employee);
            int rows = pstmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException ex) {
            DBUtil.showExceptionMessage(ex);
        }
        return false;
    }

    private static void setPreparedStatementFromEmployee(final PreparedStatement pstmt, Employee employee) throws SQLException {
        pstmt.setString(1, employee.getFirstName());
        pstmt.setString(2, employee.getLastName());
        pstmt.setString(3, employee.getEmail());
        pstmt.setString(4, employee.getPhoneNumber());
        pstmt.setDouble(5, employee.getSalary());
        if(employee.getId() == 0){
            pstmt.setInt(6, employee.getDepartmentId());
            pstmt.setString(7, employee.getJobId());
        }
    }

    private static boolean updateEmployee(Employee employee, boolean retryFlag) {
        String sqlPrepareString = getUpdateSQLPrepareString(employee, retryFlag);
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement(sqlPrepareString))
        {
            setPreparedStatementFromEmployee(pstmt, employee);
            if(!retryFlag){
                pstmt.setInt(6, employee.getDepartmentId());
                pstmt.setString(7, employee.getJobId());
            }
            int rows = pstmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException ex) {
            if(ex.getClass().getSimpleName().equals(SQLIntegrityConstraintViolationException.class.getSimpleName()) && !retryFlag){}
            else
                DBUtil.showExceptionMessage(ex);
        }
        return false;
    }

    private static String getUpdateSQLPrepareString(Employee employee, boolean retryFlag) {
        //See overloaded metod updateEmployee(Employee employee) for explanation
        StringBuilder sqlPrepareStringBuilder = new StringBuilder();
        sqlPrepareStringBuilder.append("UPDATE EMPLOYEES "
                + "SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL = ?, PHONE_NUMBER = ?, SALARY = ?");
        //If first try then update job and department as well
        if(!retryFlag){
            sqlPrepareStringBuilder.append(", DEPARTMENT_ID = ?, JOB_ID = ?");
        }
        sqlPrepareStringBuilder.append(" WHERE EMPLOYEE_ID = ");
        sqlPrepareStringBuilder.append(employee.getId());
        String sqlPrepareString = sqlPrepareStringBuilder.toString();
        return sqlPrepareString;
    }
    
    private static boolean updateEmployee(Employee employee) {
        //Job and Department of Employee can only be updated once a day
        //We first try to update all fields
        //If not successfull we try to update all fields except Job and Department
        boolean success;
        if(success = updateEmployee(employee, false))
            return success;
        else
            return updateEmployee(employee, true);
    }
    public static Set<Employee> getEmployeesFromDepartment(int departmentID){
        Set<Employee> employees = new HashSet();
        String sqlpstmt = "SELECT * FROM EMPLOYEES WHERE DEPARTMENT_ID = ?";
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement(sqlpstmt))
        {
            pstmt.setInt(1, departmentID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                employees.add(createEmployeeFromRow(rs));
        } catch (SQLException ex) {
            DBUtil.showExceptionMessage(ex);
        }
        return employees;
    }
    
    public static Employee getEmployeeByID(int id){
        try(
            Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement("SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = ?");)
        {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return createEmployeeFromRow(rs);
            }
            
        }catch (SQLException e){
            DBUtil.showExceptionMessage(e);
        }
        return null;
    }

    private static Employee createEmployeeFromRow(ResultSet rs) throws SQLException {
        //returns an instance of Employee given a row of ResultSet
        int eid = rs.getInt("EMPLOYEE_ID");
        String eFirstName = rs.getString("FIRST_NAME");
        String eLastName = rs.getString("LAST_NAME");
        String eEmail = rs.getString("EMAIL");
        String ePhoneNumber = rs.getString("PHONE_NUMBER");
        String eJobId = rs.getString("JOB_ID");
        double eSalary = rs.getDouble("SALARY");
        int eDepartmentId = rs.getInt("DEPARTMENT_ID");
        return new Employee(eid, eFirstName, eLastName, eEmail, ePhoneNumber, eJobId, eSalary, eDepartmentId);
    }
    
    public static Set<Employee> getManagers(){
        //gets all the companies managers using inner join with DEPARTMENTS table
        //returns results as a set of employees
        
        Set<Employee> managers = new HashSet();
        
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement("SELECT * FROM"
                    + " EMPLOYEES INNER JOIN DEPARTMENTS ON EMPLOYEES.EMPLOYEE_ID = DEPARTMENTS.MANAGER_ID"
                    + " AND EMPLOYEES.DEPARTMENT_ID = DEPARTMENTS.DEPARTMENT_ID"))
        {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
                managers.add(createEmployeeFromRow(rs));
        } catch (SQLException ex) {
            DBUtil.showExceptionMessage(ex);
        }
        
        return managers;
    }
    
}

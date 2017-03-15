/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zira.humanresources.models;

import java.sql.SQLException;

/**
 *
 * @author Ahmed_S
 */
public class Department {
    private final int departmentId;
    private final String departmentName;
    private Employee manager;
    private Location location;
   
    public int getDepartmentId() {
        return departmentId;
    }
   
    public String getDepartmentName() {
        return departmentName;
    }

    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(int locationId) throws IllegalArgumentException{
        Location oldLocation = this.location;
        Location newLocation = Locations.getLocationById(locationId);
        if (newLocation != null){
            try{
                this.location = newLocation;
                this.updateDepartment();
            } catch (SQLException ex) {
                DBUtil.showExceptionMessage(ex);
                this.location = oldLocation;
            }
        }
        else
            throw new IllegalArgumentException("Location not found in database.");
    }
   
    public Employee getManager() {
        return manager;
    }
    
    public void setManager(Employee manager) throws IllegalArgumentException {
        Employee oldManager = this.manager;
        if (manager != null && Employees.getEmployeeByID(manager.getId()) != null && manager.getDepartmentId() == this.departmentId && this.manager.storeToDatabase()){
            try{
                this.manager = manager;
                this.updateDepartment();
            }
            catch(SQLException e){
                DBUtil.showExceptionMessage(e);
                this.manager = oldManager;
            }
        }
        else{
            throw new IllegalArgumentException("Given manager must be stored in a database first.");
        }
    }
    
    public Department(int departmentId, String departmentName, int managerId, int locationId){
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.manager = Employees.getEmployeeByID(managerId);
        this.location = Locations.getLocationById(locationId);
    }

    private void updateDepartment() throws SQLException{
        Departments.updateDepartment(this);
    }
    
    @Override
    public String toString(){
        return String.format("%-10d%-25s%-10d%-20s", this.departmentId, this.departmentName, this.manager.getId(), this.location.getAddress());
    }
}

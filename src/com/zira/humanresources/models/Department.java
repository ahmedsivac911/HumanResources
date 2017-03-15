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
        if(manager.isManager())
            throw new IllegalArgumentException("Given person is already a manager of one department.");
        if(!Employees.getEmployeesFromDepartment(departmentId).contains(manager))
            throw new IllegalArgumentException("Given person must already be in this department to become a manager of it.");
        try{
            manager.storeToDatabase();
            this.manager = manager;
            this.updateDepartment();
        } catch (SQLException e) {
            DBUtil.showExceptionMessage(e);
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
    
    @Override
    public boolean equals (Object otherObject){
        if(otherObject == null)
            return false;
        if(!Department.class.isAssignableFrom(otherObject.getClass()))
            return false;
        Department otherDepartment = (Department) otherObject;
        return this.departmentId == otherDepartment.departmentId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.departmentId;
        return hash;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zira.humanresources;

import com.zira.humanresources.models.Employee;
import com.zira.humanresources.models.Employees;
import java.util.Set;

/**
 *
 * @author Ahmed_S
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testEmployeeGetInsertUpdate();
        //testGetEmployeesByDepartment(60);
    }   

    private static void testGetEmployeesByDepartment(int departmentID) {
        Set<Employee> employees = Employees.getEmployeesFromDepartment(departmentID);
        System.out.println("Employees working in department " + departmentID);
        employees.forEach((employee) -> {
            System.out.println(employee);
        });
    }   

    private static void testEmployeeGetInsertUpdate() {
        Employee employee = Employees.getEmployeeByID(105);
        System.out.println(employee);
        employee.setFirstName("Nermin");
        boolean success = employee.storeToDatabase();
        if(success)
            System.out.println("Employee updated successfully.");
        else
            System.out.println("Update failed.");
    }   
}

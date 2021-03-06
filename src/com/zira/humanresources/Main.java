/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zira.humanresources;


import com.zira.humanresources.models.Department;
import com.zira.humanresources.models.Departments;
import com.zira.humanresources.models.Employee;
import com.zira.humanresources.models.Employees;
import com.zira.humanresources.models.Jobs;
import com.zira.humanresources.models.Jobs.Job;
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
        testGetEmployeesByDepartment(60);
        testGetManagers();
        testMakeManager();
    }   

    private static Set<Job> testGetJobsBySalaryRange(int minSalary, int maxSalary) {
        Set<Job> jobs = Jobs.getJobsBySalaryRange(minSalary, maxSalary);
        return jobs;
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
    


    private static void testGetManagers() {
        Set<Employee> managers = Employees.getManagers();
        managers.forEach((manager) -> {
            System.out.println(manager);
        });
    }

    private static void testMakeManager() {
        Employee someone = Employees.getEmployeeByID(107);
        System.out.println(someone);
        Department it = Departments.getDepartmentById(60);
        try{
            it.setManager(someone);
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println(someone);
    }
}

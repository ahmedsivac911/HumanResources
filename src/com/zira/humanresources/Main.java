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
import com.zira.humanresources.models.Location;
import com.zira.humanresources.models.Locations;
import com.zira.humanresources.models.exceptions.JobNotFoundException;
import com.zira.humanresources.models.exceptions.SalaryOutOfRangeException;
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
        /*
        testEmployeeGetInsertUpdate();
        testGetEmployeesByDepartment(60);
        Set<Job> jobs = testGetJobsBySalaryRange(4000, 15000);
        jobs.forEach((job) -> {
            System.out.println(job);
        });
        
        Set<Employee> managers = Employees.getManagers();
        managers.forEach((manager) -> {
            System.out.println(manager);
        });
        Employee someone = Employees.getEmployeeByID(201);
        System.out.println(someone);
        System.out.println(someone.isManager());
        
        Employee herman = Employees.getEmployeeByID(204);
        System.out.println(herman);
        try{
            herman.setJob("Footballer");
        }catch(JobNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            herman.setJob("Programmer");
        }catch(JobNotFoundException e){
            System.out.println(e.getMessage());
        }
        System.out.println(herman);
        herman.setSalary(9000);
        System.out.println(herman);
        try{
            herman.setSalary(0);
        }
        catch(SalaryOutOfRangeException e) {
            System.out.println(e.getMessage());
        }
        herman.setSalary(10000);
        System.out.println(herman);
        //Job newJob = Jobs.getJobByIdOrTitle("IT_PROG");
        //System.out.println(newJob);
        
        
        Employee her = Employees.getEmployeeByID(161);
        Job myJob  = Jobs.getJobByEmployee(her);
        System.out.println(her);
        System.out.println(myJob);
        Department dep = Departments.getDepartmentById(her.getDepartmentId());
        Employee oldMan = dep.getManager();
        System.out.println(oldMan);
        dep.setManager(her);
        System.out.println(dep);
        
        Department it = Departments.getDepartmentById(60);
        System.out.println(it);
        try{
            it.setLocation(1400);
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        System.out.println(it);
        System.out.println(it.getLocation());
        Location firstLocation = Locations.getLocationById(1000);
        Location secondLocation = Locations.getLocationById(1000);
        Location thirdLocation = Locations.getLocationById(1001);
        System.out.println(firstLocation.equals(thirdLocation));
        System.out.println(firstLocation.equals(secondLocation));
        Job someJob = Jobs.getJobByIdOrTitle("Programmer");
        Job otherJob = Jobs.getJobByIdOrTitle("IT_PROG");
        System.out.println(someJob.equals(otherJob));
        */
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
}

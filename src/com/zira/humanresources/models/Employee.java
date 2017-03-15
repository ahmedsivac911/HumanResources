/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zira.humanresources.models;

import com.zira.humanresources.models.Jobs.Job;
import com.zira.humanresources.models.exceptions.JobNotFoundException;
import com.zira.humanresources.models.exceptions.SalaryOutOfRangeException;
import java.util.Map;

/**
 *
 * @author Ahmed_S
 */
public class Employee implements Comparable<Employee>{

    private final int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String jobId;
    private double salary;
    private int managerId;
    private int departmentId;
    private Job job;
    private int minimumSalary;
    private int maximumSalary;
    
    /**
     * @return the job
     */
    private void setMinMaxSalary(String jobIdOrTitle){
        Map<String, Integer> minMaxSal = Jobs.getMinimumAndMaximumSalary(jobIdOrTitle);
        minimumSalary = minMaxSal.get("minimumSalary");
        maximumSalary = minMaxSal.get("maximumSalary");
    }
    
    public Job getJob() {
        return job;
    }
    
    public void setJob(Job job){
        //sets a new job for employee and also checks that his salary is within range of his new minimum allowed salary and maximum allowed salary
        setMinMaxSalary(job.getJobId());
        if(this.salary < minimumSalary)
            this.salary  = minimumSalary;
        if(this.salary > maximumSalary)
            this.salary = maximumSalary;
        this.job = job;
    }  
    /**
     * @return the id
     */
   
    public int getId() {
        return id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the jobId
     */
    public String getJobId() {
        return jobId;
    }

    public void setJob(String jobIdOrJobTitle) throws JobNotFoundException {
        Job newJob = Jobs.getJobByIdOrTitle(jobIdOrJobTitle);
        if(newJob != null)
            this.job = newJob;
        else
            throw new JobNotFoundException();
    }

    /**
     * @return the salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(double salary) throws SalaryOutOfRangeException {
        if(salary <= this.maximumSalary && salary >= this.minimumSalary)
            this.salary = salary;
        else
            throw new SalaryOutOfRangeException();
    }
    /**
     * @return the managerId
     */
    public int getManagerId() {
        return managerId;
    }

    /**
     * @param managerId the managerId to set
     */
    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
    /**
     * @return the departmentId
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    
    
    //Class constructors
    public Employee (String lastName, String email, String jobId){
        this.id = 0;
        this.lastName = lastName;
        this.email = email;
        this.jobId = jobId;
        try{
            this.setJob(jobId);
        }
        catch(JobNotFoundException e){
            System.out.println("Warning: couldn't get Job for this employee: " + this);
        }
        this.setMinMaxSalary(jobId);
        this.salary = minimumSalary;
    }
    
    public Employee (String firstName, String lastName, String email, String jobId){
        this(lastName, email,  jobId);
        this.firstName = firstName;
    }
    
    public Employee(int eid, String eFirstName, String eLastName, String eEmail, String ePhoneNumber, 
                    String eJobId, double eSalary, int eDepartmentId){
        id = eid; 
        firstName = eFirstName;
        lastName = eLastName;
        email = eEmail;
        phoneNumber = ePhoneNumber;
        jobId = eJobId;
        salary = eSalary;
        departmentId = eDepartmentId;
        try{
            this.setJob(eJobId);
            this.setMinMaxSalary(eJobId);
        }
        catch(JobNotFoundException e){
            System.out.println("Warning: couldn't get Job for this employee.");
        }
    }
    
    //Update changes permanently to database, or insert new employee if doesn't exist
    public boolean storeToDatabase(){
        return Employees.storeEmployee(this);
    }
    
    public String getJobTitle(){
        return this.job.getJobTitle();
    }
    
    public boolean isManager(){
        return Employees.getManagers().contains(this);
    }
    
    @Override
    public String toString(){
        String formatString = "%-8d%-20s%-20s%-25s%-20s%-10.2f";
        return String.format(formatString, getId(), getFirstName(), getLastName(), getEmail(), getJobTitle(), getSalary());
    }
    
    @Override
    public boolean equals(Object otherObject){
        if(otherObject == null)
            return false;
        if(!Employee.class.isAssignableFrom(otherObject.getClass()))
            return false;
        final Employee otherEmployee = (Employee) otherObject;
        return this.id == otherEmployee.id;
    }   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.id;
        return hash;
    }

    @Override
    public int compareTo(Employee other) {
        return(this.id - other.id);
    }
}

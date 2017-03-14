/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zira.humanresources.models;

import com.zira.humanresources.models.Jobs.Job;

/**
 *
 * @author Ahmed_S
 */
public class Employee implements ComparableEmployee{

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
    
    /**
     * @return the job
     */
    public Job getJob() {
        return job;
    }
    
    public void setJob(Job job){
        this.job = job;
    }
    
    public void setJob(String jobId){
        Jobs.getJobByEmployee(this);
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

    /**
     * @param jobId the jobId to set
     */
    public void setJobId(String jobId) {
        this.jobId = jobId;
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
    public void setSalary(double salary) {
        this.salary = salary;
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
    }
    
    //Update changes permanently to database, or insert new employee if doesn't exist
    public boolean storeToDatabase(){
        return Employees.storeEmployee(this);
    }
    
    @Override
    public String toString(){
        String formatString = "%-8d%-20s%-20s%-25s%-10.2f";
        return String.format(formatString, getId(), getFirstName(), getLastName(), getEmail(), getSalary());
    }
    
    public boolean equals(Employee other){
        return (this.id == other.getId() && this.lastName.equals(other.email) && this.email.equals(other.email));
    }   

    @Override
    public int compareTo(Employee other) {
        return(this.id - other.id);
    }
}

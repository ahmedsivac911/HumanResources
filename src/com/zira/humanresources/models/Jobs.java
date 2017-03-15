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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 *
 * @author Ahmed_S
 */
public class Jobs {
    //not meant to be instantiated
    //used to interact with database table JOBS in HR database
    //mostly returns instances of nested class Job
    //nested class job to protect from creating jobs all around
    //only used to instantiate a job
    public static class Job {
    private final String jobId;
    private final String jobTitle;
    private int minSalary;
    private int maxSalary;
    
    public Job(String jobId, String jobTitle, int minSalary, int maxSalary){
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        
    }

        /**
         * @return the jobId
         */
        public String getJobId() {
            return jobId;
        }

        /**
         * @return the jobTitle
         */
        public String getJobTitle() {
            return jobTitle;
        }

        /**
         * @return the minSalary
         */
        public int getMinSalary() {
            return minSalary;
        }

        /**
         * @param minSalary the minSalary to set
         */
        private void setMinSalary(int minSalary) {
            this.minSalary = minSalary;
        }

        /**
         * @return the maxSalary
         */
        public int getMaxSalary() {
            return maxSalary;
        }

        /**
         * @param maxSalary the maxSalary to set
         */
        private void setMaxSalary(int maxSalary) {
            this.maxSalary = maxSalary;
        }
        
        @Override
        public String toString(){
            return this.jobTitle;
        }
}
    
    public static Job getJobByIdOrTitle(String jobIdOrJobTitle){
        //first tries to find job by JOB_ID
        //then tries to find job by JOB_TITLE
        //returns instance of nested class Job
        PreparedStatement pstmt;
        try(Connection dbConn = DBUtil.getConnection())
        {   
            pstmt = dbConn.prepareStatement("SELECT * FROM JOBS WHERE JOB_ID = ?");
            pstmt.setNString(1, jobIdOrJobTitle);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Job(rs.getString("JOB_ID"), rs.getString("JOB_TITLE"), rs.getInt("MIN_SALARY"), rs.getInt("MAX_SALARY"));
            }
            else{
                pstmt = dbConn.prepareStatement("SELECT * FROM JOBS WHERE JOB_TITLE = ?");
                pstmt.setString(1, jobIdOrJobTitle);
                rs = pstmt.executeQuery();
                if(rs.next())
                    return new Job(rs.getString("JOB_ID"), rs.getString("JOB_TITLE"), rs.getInt("MIN_SALARY"), rs.getInt("MAX_SALARY"));
            }
        }catch (SQLException ex) {
            DBUtil.showExceptionMessage(ex);
        }
        return null;
    }
    
    public static Job getJobByEmployee(Employee employee){
        //attemts to find employee's job 
        //if found automatically sets this employee's job member to found job
        //returns instance of nested class Job
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement("SELECT * FROM JOBS WHERE JOB_ID = ?"))
        {
            pstmt.setString(1, employee.getJobId());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                Job job = new Job(rs.getString("JOB_ID"), rs.getString("JOB_TITLE"), rs.getInt("MIN_SALARY"), rs.getInt("MAX_SALARY"));
                employee.setJob(job);
                return job;
            }
        }catch (SQLException ex) {
            DBUtil.showExceptionMessage(ex);
        }
        return null;
    }
    
    public static Set<Job> getJobsBySalaryRange(int minSalary, int maxSalary){
        //returns set of Jobs within given salary range
        Set<Job> jobs = new HashSet();
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement("SELECT * FROM JOBS WHERE MIN_SALARY >= ? AND MAX_SALARY <= ?"))
        {
            pstmt.setInt(1, minSalary);
            pstmt.setInt(2, maxSalary);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                jobs.add(new Job(rs.getString("JOB_ID"), rs.getString("JOB_TITLE"), rs.getInt("MIN_SALARY"), rs.getInt("MAX_SALARY")));
        } catch (SQLException ex) {
            DBUtil.showExceptionMessage(ex);
        }
        return jobs;
    }
    
    public static Set<Job> getJobsByMinimumSalary(int minSalary){
        //returns set of Jobs within given salary range
        Set<Job> jobs = new HashSet();
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement("SELECT * FROM JOBS WHERE MIN_SALARY >= ?"))
        {
            pstmt.setInt(1, minSalary);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                jobs.add(new Job(rs.getString("JOB_ID"), rs.getString("JOB_TITLE"), rs.getInt("MIN_SALARY"), rs.getInt("MAX_SALARY")));
        } catch (SQLException ex) {
            DBUtil.showExceptionMessage(ex);
        }
        return jobs;
    }
    
    public static Set<Job> getJobsByMaximumSalary(int maxSalary){
        //returns set of Jobs within given salary range
        Set<Job> jobs = new HashSet();
        try(Connection dbConn = DBUtil.getConnection();
            PreparedStatement pstmt = dbConn.prepareStatement("SELECT * FROM JOBS WHERE MAX_SALARY <= ?"))
        {
            pstmt.setInt(1, maxSalary);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
                jobs.add(new Job(rs.getString("JOB_ID"), rs.getString("JOB_TITLE"), rs.getInt("MIN_SALARY"), rs.getInt("MAX_SALARY")));
        } catch (SQLException ex) {
            DBUtil.showExceptionMessage(ex);
        }
        return jobs;
    }
    
    public static Map<String, Integer> getMinimumAndMaximumSalary(String jobIdOrJobTitle){
        PreparedStatement pstmt;
        Map<String, Integer> minSalMaxSal = new HashMap<>();
        try(Connection dbConn = DBUtil.getConnection())
        {   
            pstmt = dbConn.prepareStatement("SELECT MIN_SALARY, MAX_SALARY FROM JOBS WHERE JOB_ID = ?");
            pstmt.setNString(1, jobIdOrJobTitle);
            ResultSet rs = pstmt.executeQuery();
            if(!rs.next()){
                pstmt = dbConn.prepareStatement("SELECT * FROM JOBS WHERE JOB_TITLE = ?");
                pstmt.setString(1, jobIdOrJobTitle);
                rs = pstmt.executeQuery();
            }
            minSalMaxSal.put("minimumSalary", rs.getInt("MIN_SALARY"));
            minSalMaxSal.put("maximumSalary", rs.getInt("MAX_SALARY"));
        }catch (SQLException ex) {
            DBUtil.showExceptionMessage(ex);
        }
        return minSalMaxSal;
    }
}

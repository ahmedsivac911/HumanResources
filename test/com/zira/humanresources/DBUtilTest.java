/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zira.humanresources;

import java.sql.Connection;
import java.sql.SQLException;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ahmed_S
 */
public class DBUtilTest {
    
    public DBUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getConnection method, of class DBUtil.
     */
    @Test
    public void testGetConnection() throws Exception {
        System.out.println("getConnection");
        DBType dbType = DBType.ORACLEDB;
        Connection result = DBUtil.getConnection(dbType);
        assertNotNull(result);
    }

    /**
     * Test of showExceptionMessage method, of class DBUtil.
     */
    @Test
    public void testShowExceptionMessage() {
        System.out.println("showExceptionMessage");
        Exception e = new SQLException("Test message");
        DBUtil.showExceptionMessage(e);
    }
    
}

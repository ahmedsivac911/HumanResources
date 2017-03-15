/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zira.humanresources.models.exceptions;

/**
 *
 * @author Ahmed_S
 */
public class SalaryOutOfRangeException extends IllegalArgumentException {

    /**
     * Creates a new instance of <code>SalaryOutOfRangeException</code> without
     * detail message.
     */
    public SalaryOutOfRangeException() {
        super("Salary argument out of allowed range set by minimumSalary and maximumSalary fields.");
    }

    /**
     * Constructs an instance of <code>SalaryOutOfRangeException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SalaryOutOfRangeException(String msg) {
        super(msg);
    }
}

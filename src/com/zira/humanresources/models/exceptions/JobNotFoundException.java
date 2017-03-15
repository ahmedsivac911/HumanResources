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
public class JobNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>JobNotFound</code> without detail
     * message.
     */
    public JobNotFoundException() {
        super("No such Job found in the database.");
    }

    /**
     * Constructs an instance of <code>JobNotFound</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public JobNotFoundException(String msg) {
        super(msg);
    }
}

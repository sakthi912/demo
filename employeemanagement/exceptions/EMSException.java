/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved.
 * 
 */
package com.ideas2it.employeemanagement.exceptions;

/**
 * The Exception throws the custom error code to retrivel
 * the error message.
 * 
 * @version 1.0
 * @author Sakthi Annamalai
 */
public class EMSException extends Exception {

    /**
     * Constructor for creating the custom exception.
     *
     * @param errMsg Errmsg as a type of String.
     */
    public EMSException (String errMsg) {
        super(errMsg);
    }

}
/* 
 * Copyright (c) Sakthi Annamalai, Inc. All Rights Reserved
 * 
 */
package com.ideas2it.employeemanagement.logger;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

/**
 * Logger for employees and projects in Employee 
 * Management System
 *
 * @version 1.8.0_281
 * @author Sakthi Annamalai
 */
public class LoggerFactory { 
    private static Logger logger = null;

    private LoggerFactory() {
    }

    public static Logger getInstance() {
        
        if (null == logger) {
            LoggerContext context = (LoggerContext)LogManager.getContext(false);
            File file = new File("C:/Users/lenovo/eclipse-workspace/maven/src/main/java/log4j2.xml");
            context.setConfigLocation(file.toURI());
            logger =  LogManager.getLogger(LoggerFactory.class);
        }
        return logger; 
    }
}
        
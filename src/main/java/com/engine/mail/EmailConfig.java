/*
 * Copyright (c) 2022.
 * Automation Framework Selenium - Anh Tester
 */

package com.engine.mail;


/**
 * Data for Sending email after execution
 */
public class EmailConfig {

    //Remember to create an app password (App Password) for Gmail before sending
    //If you use Hosting's email, it's normal
    //Enable Override Report and Send mail in config file => src/test/resources/config/config.properties
    //OVERRIDE_REPORTS=yes
    //send_email_to_users=yes
//host smtp.googlemail.com
    public static final String SERVER = "smtp.gmail.com";
    public static final String PORT = "587";
    public static final String FROM = "ismail.tester.93@gmail.com";
    //apppasswod= mdkf dcot euzy xfgm
    public static final String PASSWORD = "Som3a@2020";

    public static final String[] TO = {"ismail.elshafiey@gmail.com"};
    public static final String SUBJECT = "Automation Framework Selenium";
}
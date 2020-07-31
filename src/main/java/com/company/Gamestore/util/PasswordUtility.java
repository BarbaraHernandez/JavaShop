package com.company.Gamestore.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtility {

    /** This utility produces encoded passwords using bcrypt.
     * See below for sample login credentials*/
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        //for account staffUser
        String staffPassword = "sPassw0rd";
        //for account managerUser
        String managerPassword = "mPassw0rd";
        //for account adminUser
        String adminPassword = "aPassw0rd";

        String encodedStaff = encoder.encode(staffPassword);
        String encodedManager = encoder.encode(managerPassword);
        String encodedAdmin = encoder.encode(adminPassword);

        System.out.println("staff: " + encodedStaff);
        System.out.println("manager: " + encodedManager);
        System.out.println("admin: " + encodedAdmin);
    }
}

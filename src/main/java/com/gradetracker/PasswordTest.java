package com.gradetracker;

import com.gradetracker.util.PasswordUtil;

public class PasswordTest {

    public static void main(String[] args) {

        String hash = PasswordUtil.hashPassword("admin123");
        System.out.println(hash);

        System.out.println(PasswordUtil.verifyPassword("admin123", hash));

        System.out.println(PasswordUtil.verifyPassword("wrongPassword", hash));
    }
}

package com.gradetracker;

import com.gradetracker.model.User;
import com.gradetracker.service.UserService;

public class UserServiceTest {
    public static void main(String[] args) {

        UserService service = new UserService();

        User user = service.authenticate("admin", "admin123");

        System.out.println(user);

        User wrong =  service.authenticate("admin", "wrong123");
        System.out.println(wrong);
    }
}

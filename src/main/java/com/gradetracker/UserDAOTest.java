package com.gradetracker;

import com.gradetracker.dao.UserDAO;
import com.gradetracker.model.User;

public class UserDAOTest {

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        User user = userDAO.findByUsername("admin");

        System.out.println(user);
    }
}

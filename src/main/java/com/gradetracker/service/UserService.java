package com.gradetracker.service;

import com.gradetracker.dao.UserDAO;
import com.gradetracker.model.User;
import com.gradetracker.util.PasswordUtil;

public class UserService {

    private final UserDAO userDAO;

    public UserService(){
        userDAO = new UserDAO();
    }

    public User authenticate(String username, String password){

        if(username == null || username.isBlank() ||  password == null || password.isBlank()){
            return null;
        }

        User user = userDAO.findByUsername(username);

        if(user == null){
            return null;
        }

        if(!Boolean.TRUE.equals(user.getActive())){
            return null;
        }

        boolean validPassword = PasswordUtil.verifyPassword(password, user.getPassword());

        if(!validPassword){
            return null;
        }

        return user;

    }

}

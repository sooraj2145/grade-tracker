package com.gradetracker.service;

import com.gradetracker.dao.UserDAO;
import com.gradetracker.model.User;
import com.gradetracker.util.PasswordUtil;
import com.gradetracker.validation.UserValidator;
import com.gradetracker.validation.ValidationResult;

import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    private final UserValidator userValidator = new UserValidator();

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

    public List<User> getAllUsers(){

       return  userDAO.findAll();
    }

    public User getUserById(Integer id){

        if(id == null) return null;

        return userDAO.findById(id);
    }

    public ServiceResult<User> createUser(User user){

        ValidationResult result = userValidator.validateForCreate(user);
        if(!result.isValid()){
            return ServiceResult.failure(result.getFirstError());
        }

        if(userDAO.existsByUsername(user.getUsername())){
            return ServiceResult.failure("Username already exists");
        }

        if(userDAO.existsByEmail(user.getEmail())){
            return ServiceResult.failure("Email already exists");
        }

        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));

        if(!userDAO.save(user)){
            return ServiceResult.failure("Unable to create user");
        }

        return ServiceResult.success(
                "User created successfully",
                user
        );
    }

    public ServiceResult<User> updateUser(User user) {

        ValidationResult result = userValidator.validateForUpdate(user);
        if(!result.isValid()){
            return ServiceResult.failure(result.getFirstError());
        }



        if(userDAO.existsByUsernameExceptId(user.getUsername(), user.getId())){
            return ServiceResult.failure("Username already exists");
        }

        if(userDAO.existsByEmailExceptId(user.getEmail(), user.getId())){
            return ServiceResult.failure("Email already exists");
        }

        if(!userDAO.update(user)){
            return ServiceResult.failure("Unable to update user.");
        }
        return ServiceResult.success("User updated successfully", user);
    }

    public ServiceResult<Void> deleteUser(Integer id) {

        if(id == null) {
            return ServiceResult.failure("Invalid user.");
        }
        if(userDAO.findById(id) == null){
            return ServiceResult.failure("User not found");
        }
        if(!userDAO.deactivate(id)){
            return ServiceResult.failure("Unable to deactivate user");
        }
        return ServiceResult.success("User deactivated successfully");
    }

    public ServiceResult<Void> resetPassword(Integer id, String newPassword) {

        if(id == null || newPassword == null || newPassword.isBlank())
            return ServiceResult.failure("Invalid  password.");

        if(userDAO.findById(id) == null){
            return ServiceResult.failure("User not found.");
        }

        String hashedPassword = PasswordUtil.hashPassword(newPassword);

        if(!userDAO.updatePassword(id, hashedPassword)){
            return ServiceResult.failure("Unable to reset password.");
        }

        return ServiceResult.success("Password reset successfully");
    }
}

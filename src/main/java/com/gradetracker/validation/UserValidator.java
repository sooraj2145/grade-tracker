package com.gradetracker.validation;

import com.gradetracker.model.User;

import java.util.regex.Pattern;

public class UserValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public ValidationResult validateForCreate(User user){

        ValidationResult result = new ValidationResult();



        if(user == null){
            result.addError("User cannot be null");
            return result;
        }

        validateUsername(user, result);
        validateFullname(user, result);
        validateEmail(user, result);
        validatePassword(user, result);
        validateRole(user, result);

        return result;

    }

    public ValidationResult validateForUpdate(User user){

        ValidationResult result = new ValidationResult();



        if(user == null){
            result.addError("User cannot be null");
            return result;
        }

        validateUserId(user, result);
        validateUsername(user, result);
        validateFullname(user, result);
        validateEmail(user, result);
        validateRole(user, result);

        return result;

    }


    private void validateUsername(User user, ValidationResult result){

        String username = user.getUsername();

        if(username == null || username.isBlank()){
            result.addError("Username is required.");
            return;
        }

        if(username.length() < 3 || username.length() > 20) {
            result.addError("Username must be between 3 and 20 characters.");
            return;
        }

    }

    private void validateFullname(User user, ValidationResult result){

        String fullname = user.getFullName();
        if(fullname == null || fullname.isBlank()){
            result.addError("Full name is required.");
            return;
        }

    }

    private void validateEmail(User user, ValidationResult result){

        String email = user.getEmail();
        if(email == null || email.isBlank()){
            result.addError("Email is required.");
            return;
        }
        if(!EMAIL_PATTERN.matcher(email).matches()){
            result.addError("Invalid email address.");
            return;
        }
    }

    private void validatePassword(User user, ValidationResult result){

        String password = user.getPassword();

        if(password == null || password.isBlank()){
            result.addError("Password is required.");
            return;
        }

        if(password.length() < 8){
            result.addError("Password must be at least 8 characters.");
            return;
        }

    }

    private void validateRole(User user, ValidationResult result){

        if(user.getRole() == null){
            result.addError("Role is required.");
            return;
        }
    }

    private void validateUserId(User user, ValidationResult result){
        Integer userId = user.getId();
        if(userId == null){
            result.addError("UserId is required.");
        }
    }
}

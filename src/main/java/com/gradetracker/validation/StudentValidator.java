package com.gradetracker.validation;

import com.gradetracker.model.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

//Validate a Student object and return all validation errors.
public class StudentValidator {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public static Map<String, String> validate(Student student){




        Map<String, String> errors = new HashMap<String, String>();

        validateFirstName(student,errors);
        validateLastName(student,errors);
        validateEmail(student,errors);
        validateDepartment(student,errors);
        validateSemester(student,errors);

        return errors;
    }

    private static void validateFirstName(Student student, Map<String, String> errors){
        String firstName = student.getFirstName();
        if(firstName == null ||  firstName.isBlank()) {
            errors.put("firstName", "First name is required");
            return;
        }

        firstName = firstName.trim();

        if (firstName.length() < 2) {
           errors.put("firstName", "First name must be at least 2 characters");
        }
        if (firstName.length() > 50) {
            errors.put("firstName", "First name must not exceed 50 characters");
        }
    }

    private static void validateLastName(Student student, Map<String, String> errors){
        String lastName = student.getLastName();
        if(lastName == null || lastName.isBlank()) {
            errors.put("lastName", "Last name is required");
            return;
        }
        lastName = lastName.trim();
        if (lastName.isEmpty()) {
            errors.put("lastName", "Last name must be at least 1 characters");
        }
        if (lastName.length() > 50) {
            errors.put("lastName", "Last name must not exceed 50 characters");
        }
    }

    private static void validateEmail(Student student, Map<String, String> errors){
        String email = student.getEmail();
        if(email == null || email.isBlank()) {
            errors.put("email", "Email is required");
            return;
        }
        email = email.trim();

        if(!EMAIL_PATTERN.matcher(email).matches()) {
            errors.put("email", "Please enter a valid email address.");
        }

    }

    private static void validateDepartment(Student student, Map<String, String> errors){
        String department = student.getDepartment();
        if(department == null || department.isBlank()) {
            errors.put("department", "Department is required");
            return;
        }
        department = department.trim();
        if (department.length() < 2) {
            errors.put("department", "Department name must be at least 2 characters");
        }
        if (department.length() > 50) {
            errors.put("department", "Department name must not exceed 50 characters");
        }
    }

    private static void validateSemester(Student student, Map<String, String> errors){
        int semester = student.getSemester();

        if(semester < 1 || semester > 8) {
            errors.put("semester", "Semester must be between 1 and 8");
        }
    }
}

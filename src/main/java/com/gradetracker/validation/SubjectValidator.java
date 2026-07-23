package com.gradetracker.validation;

import com.gradetracker.model.Subject;

import java.util.HashMap;
import java.util.Map;

public class SubjectValidator {

    public static Map<String, String> validate(Subject subject) {
        Map<String, String> errors = new HashMap<>();

        validateCode(subject, errors);
        validateName(subject,errors);
        validateCredits(subject,errors);
        validateSemester(subject,errors);
        return errors;
    }

    private static void validateCode(Subject subject, Map<String, String> errors) {
        String code = subject.getCode();
        if(code == null || code.isBlank()) {
            errors.put("code", "Code is required");
            return;
        }

        code = code.trim();

        if(code.length() > 20) {
            errors.put("code", "Code must not exceed 20 characters");
        }
    }

    private static void validateName(Subject subject, Map<String, String> errors) {
        String name = subject.getName();
        if(name == null || name.isBlank()) {
            errors.put("name", "Name is required");
            return;
        }
        name = name.trim();
        if(name.length() > 100) {
            errors.put("name", "Name must be less than 100 characters");
        }
    }

    private static void validateCredits(Subject subject, Map<String, String> errors) {
        Integer credits = subject.getCredits();

        if(credits == null || credits < 1) {
            errors.put("credits", "Credits must be greater than 0");
        }
    }

    private static void validateSemester(Subject subject, Map<String, String> errors) {
        Integer semester = subject.getSemester();


        if(semester == null || semester < 1 ||  semester > 8) {
            errors.put("semester", "Semester must be between 1 and 8");
        }
    }
}

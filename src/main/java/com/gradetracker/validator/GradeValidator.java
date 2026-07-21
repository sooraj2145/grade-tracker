package com.gradetracker.validator;

import com.gradetracker.model.Grade;

import java.util.HashMap;
import java.util.Map;

public class GradeValidator {

    public Map<String, String>  validate(Grade grade) {
        Map<String, String> errors = new HashMap<>();
        validateStudent(grade,errors);
        validateSubject(grade,errors);
        validateMarks(grade,errors);

        validateRemarks(grade,errors);

        return errors;
    }

    private void validateStudent(Grade grade, Map<String, String> errors) {
        if(grade.getStudentId() == null){
            errors.put("studentId", "Please select a student.");
        }
    }

    private void validateSubject(Grade grade, Map<String, String> errors) {
        if(grade.getSubjectId() == null){
            errors.put("subjectId", "Please select a subject.");
        }
    }

    private void validateMarks(Grade grade, Map<String, String> errors) {
        if(grade.getMarks() == null){
            errors.put("marks", "Marks are required.");
        } else if (grade.getMarks() < 0 || grade.getMarks() > 100) {
            errors.put("marks", "Marks must be between 0 and 100.");

        }
    }



    private void validateRemarks(Grade grade, Map<String, String> errors) {
        if(grade.getRemarks() != null && grade.getRemarks().length() > 255){
            errors.put("remarks", "Remarks cannot exceed 255 characters.");
        }
    }
}

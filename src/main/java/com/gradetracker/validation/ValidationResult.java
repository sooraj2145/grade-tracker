package com.gradetracker.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private final List<String> errors = new ArrayList<>();

    public void addError(String error){
        if(error != null &&  !error.isBlank()){
            errors.add(error);
        }
    }

    public boolean isValid(){
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public String getFirstError(){
        if(errors.isEmpty()){
            return null;
        }
        return errors.getFirst();
    }
}

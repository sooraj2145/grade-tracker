package com.gradetracker.util;

public class GradeUtil {

    private GradeUtil() {}

    public static String calculateGrade(double marks) {

        if(marks>=90){
            return "A+";
        } else if(marks>=80){
            return "A";
        } else if(marks>=70){
            return "B+";
        }  else if(marks>=60){
            return "B";
        }  else if(marks>=50){
            return "C";
        }  else if(marks>=40){
            return "D";
        } else {
            return "F";
        }

    }
}

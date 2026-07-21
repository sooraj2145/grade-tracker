package com.gradetracker.service;

import com.gradetracker.dao.GradeDAO;
import com.gradetracker.model.Grade;
import com.gradetracker.util.GradeUtil;

import java.util.List;

public class GradeService {

    private final GradeDAO gradeDAO;

    public GradeService(){
        this.gradeDAO = new GradeDAO();
    }

    public List<Grade> getGrades(int offset,
                                 int pageSize,
                                 String keyword,
                                 String sortBy,
                                 String direction){

        if(keyword == null || keyword.isBlank()){
            return gradeDAO.getGrades(offset,pageSize,sortBy,direction);
        }

        return gradeDAO.searchGrades(
                keyword,
                offset,
                pageSize,
                sortBy,
                direction
        );
    }

    public int getGradesCount(String keyword){
        if(keyword == null || keyword.isBlank()){
            return gradeDAO.getGradeCount();
        }
        return gradeDAO.getGradeCount(keyword);
    }

    public Grade getGradeById(int id){
        return gradeDAO.getGradeById(id);
    }

    public boolean addGrade(Grade grade){

        grade.setGrade(GradeUtil.calculateGrade(grade.getMarks()));
        return gradeDAO.addGrade(grade);
    }

    public boolean updateGrade(Grade grade){

        grade.setGrade(GradeUtil.calculateGrade(grade.getMarks()));
        return gradeDAO.updateGrade(grade);
    }

    public boolean deleteGrade(int id){
        return gradeDAO.deleteGradeById(id);
    }


}

package com.gradetracker.service;

import com.gradetracker.dao.SubjectDAO;
import com.gradetracker.model.Subject;

import java.util.List;

public class SubjectService {

    private final SubjectDAO subjectDAO;

    public SubjectService(){
        this.subjectDAO = new SubjectDAO();
    }

    public List<Subject> getSubjects(int offset,
                                     int pageSize,
                                     String keyword,
                                     String sortBy,
                                     String direction){

        if(keyword == null || keyword.isBlank()){
            return subjectDAO.getSubjects(offset,pageSize,sortBy,direction);
        }

        return subjectDAO.searchSubjects(
                keyword,
                offset,
                pageSize,
                sortBy,
                direction
        );
    }

    public int getSubjectCount(String keyword){

        if(keyword == null || keyword.isBlank()){
            return subjectDAO.getSubjectCount();
        }
        return subjectDAO.getSubjectCount(keyword);
    }

    public int getSubjectCount(){
        return subjectDAO.getSubjectCount();
    }

    public Subject getSubjectById(int id){
        return subjectDAO.getSubjectById(id);
    }

    public boolean addSubject(Subject subject){
        return subjectDAO.addSubject(subject);
    }

    public boolean updateSubject(Subject subject){
        return subjectDAO.updateSubject(subject);

    }

    public boolean deleteSubject(int id){
        return subjectDAO.deleteSubjectById(id);
    }

    public List<Subject> getAllSubjects(){
        return subjectDAO.getAllSubjects();
    }
}

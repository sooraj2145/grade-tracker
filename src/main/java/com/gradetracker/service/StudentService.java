package com.gradetracker.service;

import com.gradetracker.dao.StudentDAO;
import com.gradetracker.model.Student;

import java.util.List;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public List<Student> getStudents(int offset,
                                     int pageSize,
                                     String keyword,
                                     String sortBy,
                                     String direction) {

        if (keyword == null || keyword.isBlank()) {
            return studentDAO.getStudents(offset, pageSize, sortBy, direction);
        }

        return studentDAO.searchStudents(
                keyword,
                offset,
                pageSize,
                sortBy,
                direction
        );
    }

    public int getStudentCount(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return studentDAO.getStudentCount();
        }

        return studentDAO.getStudentCount(keyword);
    }

    public int getStudentCount(){
        return studentDAO.getStudentCount();
    }

    public Student getStudentById(int id) {
        return studentDAO.getStudentById(id);
    }

    public boolean addStudent(Student student) {
        return studentDAO.addStudent(student);
    }

    public boolean updateStudent(Student student) {
        return studentDAO.updateStudent(student);
    }

    public boolean deleteStudent(int id) {
        return studentDAO.deleteStudentById(id);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
}
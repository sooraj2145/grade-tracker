package com.gradetracker.model;

public class Grade {

    private Integer id;
    private Integer studentId;
    private Integer subjectId;
    private Double marks;
    private String grade;
    private String remarks;
    private String studentName;
    private String subjectName;


    public Grade(){}

    public Grade(Double marks, String grade, String remarks) {
        this.marks = marks;
        this.grade = grade;
        this.remarks = remarks;
    }

    public Grade(Integer id, Integer studentId, Integer subjectId, Double marks, String grade, String remarks) {
        this.id = id;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.marks = marks;
        this.grade = grade;
        this.remarks = remarks;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", subjectId=" + subjectId +
                ", marks=" + marks +
                ", grade='" + grade + '\'' +
                ", remarks='" + remarks + '\'' +
                ", studentName='" + studentName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}

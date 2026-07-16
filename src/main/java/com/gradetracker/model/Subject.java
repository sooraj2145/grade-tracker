package com.gradetracker.model;

public class Subject {
    private Integer id;
    private String code;
    private String name;
    private Integer credits;
    private Integer semester;

    public Subject(){}

    public Subject(String code, String name, Integer credits, Integer semester) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.semester = semester;

    }

    public Subject(Integer id, String code, String name, Integer credits, Integer semester) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.semester = semester;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getCredits() {
        return credits;
    }
    public void setCredits(Integer credits) {
        this.credits = credits;
    }
    public Integer getSemester() {
        return semester;
    }
    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", credits=" + credits +
                ", semester=" + semester +
                '}';
    }
}

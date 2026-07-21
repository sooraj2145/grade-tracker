package com.gradetracker.controller;

import com.gradetracker.dao.GradeDAO;
import com.gradetracker.dao.StudentDAO;
import com.gradetracker.dao.SubjectDAO;
import com.gradetracker.model.Grade;
import com.gradetracker.model.Student;
import com.gradetracker.model.Subject;
import com.gradetracker.util.GradeUtil;
import com.gradetracker.validator.GradeValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/grades")
public class GradeServlet extends HttpServlet{

    private static final int PAGE_SIZE = 10;

    private GradeDAO gradeDAO;
    private StudentDAO studentDAO;
    private SubjectDAO subjectDAO;
    private GradeValidator validator;

    @Override
    public void init() throws ServletException {

        super.init();

        gradeDAO = new GradeDAO();
        studentDAO = new StudentDAO();
        subjectDAO = new SubjectDAO();
        validator = new GradeValidator();
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
        throws ServletException, IOException {
        String action = req.getParameter("action");

        if(action == null){
            action = "";
        }

        switch(action){
            case "new" -> showNewForm(req,resp);
            case "edit" -> showForm(req,resp);
            case "delete" -> deleteGrade(req,resp);
            default -> listGrades(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch(action){
            case "insert" -> insertGrade(req,resp);
            case "update" -> updateGrade(req,resp);
            default -> resp.sendRedirect(req.getContextPath() + "/grades");
        }
    }

    private void listGrades(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;

        try {
            page = Integer.parseInt(req.getParameter("page"));

        } catch (Exception ignored) {


        }

        String keyword = req.getParameter("keyword");
        if(keyword == null){
            keyword = "";
        }

        String sortBy = req.getParameter("sortBy");
        if(sortBy == null || sortBy.isBlank()){
            sortBy = "id";
        }

        String direction = req.getParameter("direction");
        if(direction == null || direction.isBlank()){
            direction = "asc";
        }

        int offset = (page - 1) * PAGE_SIZE;

        List<Grade> grades;
        int totalRecords;

        if(keyword.isBlank()){
            grades = gradeDAO.getGrades(
                    offset,
                    PAGE_SIZE,
                    sortBy,
                    direction
            );
            totalRecords = gradeDAO.getGradeCount();
        } else {
            grades = gradeDAO.searchGrades(
                    keyword,
                    offset,
                    PAGE_SIZE,
                    sortBy,
                    direction
            );

            totalRecords = gradeDAO.getGradeCount(keyword);
        }

        int totalPages = (int) Math.ceil((double)totalRecords / PAGE_SIZE);

        req.setAttribute("grades", grades);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("keyword", keyword);
        req.setAttribute("sortBy", sortBy);
        req.setAttribute("direction", direction);

        req.getRequestDispatcher("/grades/grade-list.jsp").forward(req, resp);


    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        loadDropdowns(req);

        req.getRequestDispatcher("/grades/grade-form.jsp").forward(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));

        Grade grade = gradeDAO.getGradeById(id);

        if(grade == null){
            resp.sendRedirect(req.getContextPath() + "/grades");
            return;
        }



        req.setAttribute("grade",  grade);

        loadDropdowns(req);

        req.getRequestDispatcher("/grades/grade-form.jsp").forward(req, resp);
    }

    private void insertGrade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer studentId = Integer.parseInt(req.getParameter("studentId"));
        Integer subjectId = Integer.parseInt(req.getParameter("subjectId"));
        double marks =  Double.parseDouble(req.getParameter("marks"));

        String remarks = req.getParameter("remarks");

        Grade grade = new Grade();

        grade.setStudentId(studentId);
        grade.setSubjectId(subjectId);
        grade.setMarks(marks);
        grade.setGrade(GradeUtil.calculateGrade(marks));
        grade.setRemarks(remarks);

        Map<String, String> errors = validator.validate(grade);
        if(!errors.isEmpty()){
           forwardToForm(req, resp, grade, errors);
           return;

        }
        gradeDAO.addGrade(grade);

        resp.sendRedirect(req.getContextPath() + "/grades");


    }

    private void updateGrade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.parseInt(req.getParameter("id"));
        Integer studentId = Integer.parseInt(req.getParameter("studentId"));
        Integer subjectId = Integer.parseInt(req.getParameter("subjectId"));
        Double marks =  Double.parseDouble(req.getParameter("marks"));
        String remarks = req.getParameter("remarks");

        Grade grade = new Grade();

        grade.setId(id);
        grade.setStudentId(studentId);
        grade.setSubjectId(subjectId);
        grade.setMarks(marks);
        grade.setGrade(GradeUtil.calculateGrade(marks));
        grade.setRemarks(remarks);

        Map<String, String> errors = validator.validate(grade);

        if(!errors.isEmpty()){
            forwardToForm(req, resp, grade, errors);
            return;
        }
        gradeDAO.updateGrade(grade);
        resp.sendRedirect(req.getContextPath() + "/grades");




    }

    private void deleteGrade(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Integer id = Integer.parseInt(req.getParameter("id"));

        gradeDAO.deleteGradeById(id);
        resp.sendRedirect(req.getContextPath() + "/grades");
    }


//    helper methods

    private void loadDropdowns(HttpServletRequest req){

        req.setAttribute("students", studentDAO.getAllStudents());
        req.setAttribute("subjects", subjectDAO.getAllSubjects());
    }


    private void forwardToForm(HttpServletRequest req,
                               HttpServletResponse resp,
                               Grade grade,
                               Map<String, String> errors)
            throws ServletException, IOException {

        req.setAttribute("grade", grade);
        req.setAttribute("errors", errors);

        loadDropdowns(req);

        req.getRequestDispatcher("/grades/grade-form.jsp")
                .forward(req, resp);
    }






}

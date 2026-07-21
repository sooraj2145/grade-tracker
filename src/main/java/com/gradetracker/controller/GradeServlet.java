package com.gradetracker.controller;


import com.gradetracker.model.Grade;

import com.gradetracker.service.GradeService;
import com.gradetracker.service.StudentService;
import com.gradetracker.service.SubjectService;

import com.gradetracker.validator.GradeValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/grades")
public class GradeServlet extends BaseServlet{

    private static final String ACTION_NEW = "new";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_INSERT = "insert";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_LIST = "list";



    private GradeService gradeService;
    private StudentService studentService;
    private SubjectService subjectService;
    private GradeValidator validator;

    @Override
    public void init() throws ServletException {

        super.init();

        gradeService = new GradeService();
        studentService = new StudentService();
        subjectService = new SubjectService();
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
            case ACTION_NEW -> showNewForm(req,resp);
            case ACTION_EDIT -> showEditForm(req,resp);
            case ACTION_DELETE -> deleteGrade(req,resp);
            default -> listGrades(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = ACTION_LIST;
        }
        switch(action){
            case ACTION_INSERT -> insertGrade(req,resp);
            case ACTION_UPDATE -> updateGrade(req,resp);
            default -> resp.sendRedirect(req.getContextPath() + "/grades");
        }
    }

    private void listGrades(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int currentPage = getCurrentPage(req);

        String keyword = req.getParameter("keyword");
        if(keyword == null){
            keyword = "";
        }

        String sortBy = getSortBy(req);

        String direction = getSortDirection(req);

        int offset = (currentPage - 1) * PAGE_SIZE;

        List<Grade> grades = gradeService.getGrades(
                offset,
                PAGE_SIZE,
                keyword,
                sortBy,
                direction
        );
        int totalRecords =  gradeService.getGradesCount(keyword);


        int totalPages = Math.max(1,
                (int)Math.ceil((double)totalRecords / (double)PAGE_SIZE));

        req.setAttribute("grades", grades);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("keyword", keyword);
        req.setAttribute("sortBy", sortBy);
        req.setAttribute("direction", direction);

        showSuccessMessage(req);
        showErrorMessage(req);
        forwardToGradeList(req, resp);


    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

        forwardToForm(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = parseInteger(req.getParameter("id"));

        if(id == null){
            redirectToGradeList(req, resp);
            return;
        }

        Grade grade = gradeService.getGradeById(id);

        if(grade == null){
            redirectToGradeList(req, resp);
            return;
        }
        req.setAttribute("grade",  grade);

        forwardToForm(req, resp);
    }

    private void insertGrade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Grade grade = buildGradeFromRequest(req);

        saveGrade(req, resp, grade, false);

    }

    private void updateGrade(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = parseInteger(req.getParameter("id"));

        if(id == null){
            redirectToGradeList(req, resp);
            return;
        }

        Grade grade = buildGradeFromRequest(req);

        grade.setId(id);

        saveGrade(req, resp, grade, true);

    }

    private void saveGrade(HttpServletRequest req,
                           HttpServletResponse resp,
                           Grade grade,
                           boolean isUpdate)
            throws ServletException, IOException {

        Map<String, String> errors = validator.validate(grade);

        if(!errors.isEmpty()){
            preserveListState(req);
            forwardToForm(req, resp, grade, errors);
            return;
        }

        if(isUpdate){
            gradeService.updateGrade(grade);
            addSuccessMessage(req, "Grade updated successfully");
        } else {
            gradeService.addGrade(grade);
            addSuccessMessage(req,"Grade added successfully");
        }

        redirectToGradeList(req, resp);
    }

    private void deleteGrade(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Integer id = parseInteger(req.getParameter("id"));

        if(id == null){
            redirectToGradeList(req, resp);
            return;
        }

        gradeService.deleteGrade(id);

        addSuccessMessage(req, "Grade deleted successfully");

        redirectToGradeList(req, resp);
    }


//    helper methods

    private void loadDropdowns(HttpServletRequest req){

        req.setAttribute("students", studentService.getAllStudents());
        req.setAttribute("subjects", subjectService.getAllSubjects());
    }


    private void forwardToForm(HttpServletRequest req,
                               HttpServletResponse resp,
                               Grade grade,
                               Map<String, String> errors)
            throws ServletException, IOException {

        req.setAttribute("grade", grade);
        req.setAttribute("errors", errors);

        forwardToForm(req, resp);
    }

    private void forwardToForm(HttpServletRequest req,
                               HttpServletResponse resp)
            throws ServletException, IOException {

        loadDropdowns(req);

        req.getRequestDispatcher("/grades/grade-form.jsp")
                .forward(req, resp);
    }




    private Grade buildGradeFromRequest(HttpServletRequest req){
        Grade grade = new Grade();

        grade.setStudentId(parseInteger(req.getParameter("studentId")));
        grade.setSubjectId(parseInteger(req.getParameter("subjectId")));

        String marks = req.getParameter("marks");

        if(marks != null && !marks.isBlank()){
            grade.setMarks(Double.parseDouble(marks));
        }

        grade.setRemarks(req.getParameter("remarks"));

        return grade;
    }

    private String getSortBy(HttpServletRequest req) {

        String sortBy = req.getParameter("sortBy");

        if (sortBy == null || sortBy.isBlank()) {
            return "id";
        }

        return switch (sortBy) {
            case "id",
                 "student_name",
                 "subject_name",
                 "marks",
                 "grade"
                    -> sortBy;

            default -> "id";
        };
    }



    private void redirectToGradeList(HttpServletRequest req,
                                     HttpServletResponse resp)
            throws IOException {

        resp.sendRedirect(req.getContextPath() + "/grades");
    }

    private void forwardToGradeList(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.getRequestDispatcher("/grades/grade-list.jsp").forward(req, resp);
    }
}
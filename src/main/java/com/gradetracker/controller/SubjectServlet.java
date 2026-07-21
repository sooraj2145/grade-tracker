package com.gradetracker.controller;


import com.gradetracker.model.Subject;
import com.gradetracker.service.SubjectService;
import com.gradetracker.validator.SubjectValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;
import java.util.Map;


@WebServlet("/subjects")
public class SubjectServlet extends BaseServlet {

    private static final String ACTION_LIST = "list";
    private static final String ACTION_NEW = "new";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_INSERT = "insert";
    private static final String ACTION_UPDATE = "update";

    private SubjectService subjectService;

    @Override
    public void init(){
        subjectService = new SubjectService();
    }

    @Override
    protected void doGet(HttpServletRequest req,  HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if(action == null){
            action = ACTION_LIST;
        }

        switch(action){
            case ACTION_NEW -> showNewForm(req,resp);
            case ACTION_EDIT -> showForm(req,resp);
            case ACTION_DELETE -> deleteSubject(req,resp);
            default -> listSubjects(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req,  HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if(action == null){
            action = ACTION_INSERT;
        }

        switch(action){
            case ACTION_INSERT -> insertSubject(req, resp);
            case ACTION_UPDATE -> updateSubject(req, resp);

            default -> redirectToSubjectList(req,resp);
        }
    }


    private void listSubjects(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showSuccessMessage(req);
        showErrorMessage(req);
        String keyword = req.getParameter("keyword");
        String sortBy = getSortBy(req);
        String direction = getSortDirection(req);

        int currentPage = getCurrentPage(req);
        int offset = (currentPage - 1) * PAGE_SIZE;



        int totalSubjects = subjectService.getSubjectCount(keyword);



        int totalPages = Math.max(
                1,
                (int) Math.ceil((double) totalSubjects / PAGE_SIZE)
        );

        List<Subject> subjects =  subjectService.getSubjects(offset,PAGE_SIZE,keyword,sortBy,direction);




        req.setAttribute("subjects", subjects);
        req.setAttribute("keyword", keyword);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("sortBy", sortBy);
        req.setAttribute("direction", direction);

        forwardToSubjectList(req, resp);

    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToForm(req, resp);
    }

    private void showForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = parseInteger(req.getParameter("id"));

        Subject subject = subjectService.getSubjectById(id);

        if(subject == null){
            redirectToSubjectList(req, resp);
            return;
        }


        preserveListState(req);
        req.setAttribute("subject", subject);

        forwardToForm(req, resp);
    }

    private void insertSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Subject subject = buildSubjectFromRequest(req);

        Map<String, String> errors = SubjectValidator.validate(subject);

        if(!errors.isEmpty()){

            preserveListState(req);

            req.setAttribute("subject", subject);
            req.setAttribute("errors", errors);
            forwardToForm(req, resp);
            return;
        }

        if(subjectService.addSubject(subject)){
            addSuccessMessage(req, "Subject added successfully.");
            redirectToSubjectList(req, resp);
        } else{

            preserveListState(req);

            req.setAttribute("subject", subject);
            req.setAttribute("databaseError", "Unable to save subject.");
            forwardToSubjectList(req, resp);
        }

    }

    private void updateSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Subject subject = buildSubjectFromRequest(req);

        subject.setId(parseInteger(req.getParameter("id")));

        Map<String, String> errors = SubjectValidator.validate(subject);

        if(!errors.isEmpty()){

            preserveListState(req);

            req.setAttribute("subject", subject);
            req.setAttribute("errors", errors);
            forwardToForm(req, resp);
            return;
        }
        if(subjectService.updateSubject(subject)){
            addSuccessMessage(req, "Subject updated successfully.");
            redirectToSubjectList(req, resp);
        } else {
            req.setAttribute("subject", subject);
            req.setAttribute("databaseError", "Unable to update subject.");
            forwardToForm(req, resp);
        }
    }

    private void deleteSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = parseInteger(req.getParameter("id"));
        if(id == null){
            redirectToSubjectList(req, resp);
            return;
        }
        Subject subject = subjectService.getSubjectById(id);
        if(subject == null){
            redirectToSubjectList(req, resp);
            return;
        }
        if(subjectService.deleteSubject(id)){
            addSuccessMessage(req,"Subject deleted successfully.");

        }else {
            addErrorMessage(req, "Unable to delete subject.");
        }

        redirectToSubjectList(req, resp);
    }

    private Subject buildSubjectFromRequest(HttpServletRequest req) {
        Subject subject = new Subject();
        subject.setCode(req.getParameter("code"));
        subject.setName(req.getParameter("name"));
        subject.setCredits(parseInteger(req.getParameter("credits")));
        subject.setSemester(parseInteger(req.getParameter("semester")));

        return subject;

    }



    private void forwardToSubjectList(HttpServletRequest req,
                                      HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/subjects/subject-list.jsp")
                .forward(req, resp);
    }

    private void forwardToForm(HttpServletRequest req,
                               HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/subjects/subject-form.jsp")
                .forward(req, resp);
    }

    private void redirectToSubjectList(HttpServletRequest req,
                                       HttpServletResponse resp)
            throws IOException {


    }




    private String getSortBy(HttpServletRequest req){
        String sortBy = req.getParameter("sortBy");
        if(sortBy == null || sortBy.isBlank()){
            return "id";
        }
        return switch(sortBy){
            case "id",
                 "code",
                 "name",
                 "credits",
                 "semester" -> sortBy;
            default -> "id";
        };
    }





}

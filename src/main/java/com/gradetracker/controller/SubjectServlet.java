package com.gradetracker.controller;

import com.gradetracker.dao.SubjectDAO;
import com.gradetracker.model.Subject;
import com.gradetracker.validator.SubjectValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@WebServlet("/subjects")
public class SubjectServlet extends HttpServlet {

    private static final int PAGE_SIZE = 10;

    private SubjectDAO subjectDAO;

    @Override
    public void init(){
        subjectDAO = new SubjectDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req,  HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");

        if(action == null){
            action = "list";
        }

        switch(action){
            case "new" -> showNewForm(req,resp);
            case "edit" -> showForm(req,resp);
            case "delete" -> deleteSubject(req,resp);
            default -> listSubjects(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req,  HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if(action == null){
            action = "insert";
        }

        switch(action){
            case "insert" -> insertSubject(req, resp);
            case "update" -> updateSubject(req, resp);

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

        boolean searching = keyword != null && !keyword.isBlank();

        int totalSubjects = searching
                ? subjectDAO.getSubjectCount(keyword)
                : subjectDAO.getSubjectCount() ;



        int totalPages = Math.max(
                1,
                (int) Math.ceil((double) totalSubjects / PAGE_SIZE)
        );

        List<Subject> subjects = searching
                ? subjectDAO.searchSubjects(keyword,offset,PAGE_SIZE,sortBy,direction)
                : subjectDAO.getSubjects(offset,PAGE_SIZE,sortBy,direction);



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
        int id = Integer.parseInt(req.getParameter("id"));

        Subject subject = subjectDAO.getSubjectById(id);

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

        if(subjectDAO.addSubject(subject)){
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
        if(subjectDAO.updateSubject(subject)){
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
        Subject subject = subjectDAO.getSubjectById(id);
        if(subject == null){
            redirectToSubjectList(req, resp);
            return;
        }
        if(subjectDAO.deleteSubjectById(id)){
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

    private Integer parseInteger(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void preserveListState(HttpServletRequest req) {
        req.setAttribute("keyword", req.getParameter("keyword"));
        req.setAttribute("page", req.getParameter("page"));
        req.setAttribute("sortBy", req.getParameter("sortBy"));
        req.setAttribute("direction", req.getParameter("direction"));
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

        resp.sendRedirect(req.getContextPath() + "/subjects");
    }

    private int getCurrentPage(HttpServletRequest req){

        String page = req.getParameter("page");

        if(page == null || page.isBlank()){
            return 1;
        }

        try {
            int currentPage = Integer.parseInt(page);

            return Math.max(currentPage, 1);
        } catch (NumberFormatException e) {
            return 1;
        }
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

    private String getSortDirection(HttpServletRequest req){
        String direction = req.getParameter("direction");
        if(direction == null || direction.isBlank()){
            return "asc";
        }
        return switch(direction){
            case "asc",
                 "desc" -> direction;
            default -> "asc";
        };
    }

    private void addSuccessMessage(HttpServletRequest req,
                                   String message) {

        if (message == null || message.isBlank()) {
            return;
        }

        HttpSession session = req.getSession();
        session.setAttribute("successMessage", message);
    }

    private void showSuccessMessage(HttpServletRequest req) {

        HttpSession session = req.getSession();

        String successMessage =
                (String) session.getAttribute("successMessage");

        if (successMessage != null) {

            req.setAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }
    }

    private void addErrorMessage(HttpServletRequest req, String message) {
        if (message == null || message.isBlank()) {
            return;
        }
        HttpSession session = req.getSession();
        session.setAttribute("errorMessage", message);
    }

    private void showErrorMessage(HttpServletRequest req) {


        HttpSession session = req.getSession();

        String errorMessage =
                (String) session.getAttribute("errorMessage");

        if (errorMessage != null) {

            req.setAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }
    }
}

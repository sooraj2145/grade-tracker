package com.gradetracker.controller;

import com.gradetracker.dao.StudentDAO;
import com.gradetracker.model.Student;
import com.gradetracker.validator.StudentValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    private static final String ACTION_NEW = "new";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_DELETE = "delete";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_LIST = "list";
    private static final String ACTION_INSERT = "insert";

    private static final int PAGE_SIZE = 10;

    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            action = ACTION_LIST;
        }

        switch (action) {
            case ACTION_NEW -> showNewForm(req, resp);
            case ACTION_EDIT -> showEditForm(req, resp);
            case ACTION_DELETE -> deleteStudent(req, resp);
            default -> listStudents(req, resp);
        }
    }

    private void listStudents(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String keyword = req.getParameter("keyword");
        String sortBy = getSortBy(req);
        String direction = getSortDirection(req);

        boolean searching = keyword != null && !keyword.isBlank();

        int currentPage = getCurrentPage(req);
        int offset = (currentPage - 1) * PAGE_SIZE;

        int totalStudents = searching
                ? studentDAO.getStudentCount(keyword)
                : studentDAO.getStudentCount();

        int totalPages = Math.max(
                1,
                (int) Math.ceil((double) totalStudents / PAGE_SIZE)
        );

        List<Student> students = searching
                ? studentDAO.searchStudents(keyword, offset, PAGE_SIZE,sortBy, direction)
                : studentDAO.getStudents(offset, PAGE_SIZE, sortBy, direction);

        req.setAttribute("keyword", keyword);
        req.setAttribute("sortBy", sortBy);
        req.setAttribute("direction", direction);
        req.setAttribute("students", students);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);

        showSuccessMessage(req);

        req.getRequestDispatcher("/students/student-list.jsp")
                .forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req,
                             HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/students/student-form.jsp")
                .forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req,
                              HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        req.setAttribute("keyword", req.getParameter("keyword"));
        req.setAttribute("student", studentDAO.getStudentById(id));

        req.getRequestDispatcher("/students/student-form.jsp")
                .forward(req, resp);
    }

    private void deleteStudent(HttpServletRequest req,
                               HttpServletResponse resp)
            throws IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        if (studentDAO.deleteStudentById(id)) {

            addSuccessMessage(req, "Student deleted successfully.");

            redirectToStudentList(req, resp);

        } else {

            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Failed to delete student."
            );
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            action = ACTION_INSERT;
        }

        switch (action) {
            case ACTION_UPDATE -> updateStudent(req, resp);
            default -> insertStudent(req, resp);
        }
    }

    private void insertStudent(HttpServletRequest req,
                               HttpServletResponse resp)
            throws ServletException, IOException {

        Student student = buildStudentFromRequest(req);

        Map<String, String> errors = StudentValidator.validate(student);

        if (!errors.isEmpty()) {

            req.setAttribute("student", student);
            req.setAttribute("errors", errors);

            req.getRequestDispatcher("/students/student-form.jsp")
                    .forward(req, resp);

            return;
        }

        if (studentDAO.addStudent(student)) {

            addSuccessMessage(req, "Student added successfully.");

            resp.sendRedirect(req.getContextPath() + "/students");

        } else {

            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Failed to insert student."
            );
        }
    }

    private void updateStudent(HttpServletRequest req,
                               HttpServletResponse resp)
            throws IOException, ServletException {

        Student student = buildStudentFromRequest(req);

        student.setId(Integer.parseInt(req.getParameter("id")));

        if (studentDAO.updateStudent(student)) {

            addSuccessMessage(req, "Student updated successfully.");

            redirectToStudentList(req, resp);


        } else {

            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Failed to update student."
            );
        }
    }

    private Student buildStudentFromRequest(HttpServletRequest req) {

        Student student = new Student();

        student.setFirstName(req.getParameter("firstName"));
        student.setLastName(req.getParameter("lastName"));
        student.setEmail(req.getParameter("email"));
        student.setDepartment(req.getParameter("department"));
        student.setSemester(
                Integer.parseInt(req.getParameter("semester"))
        );

        return student;
    }

    private void redirectToStudentList(HttpServletRequest req,
                                       HttpServletResponse resp)
            throws IOException {

        String keyword = req.getParameter("keyword");
        String page = req.getParameter("page");
        String sortBy = req.getParameter("sortBy");
        String direction = req.getParameter("direction");

        StringBuilder url = new StringBuilder(
                req.getContextPath() + "/students?"
        );

        if (keyword != null && !keyword.isBlank()) {
            url.append("keyword=")
                    .append(URLEncoder.encode(keyword, StandardCharsets.UTF_8))
                    .append("&");
        }

        if (page != null && !page.isBlank()) {
            url.append("page=").append(page).append("&");
        }

        if (sortBy != null && !sortBy.isBlank()) {
            url.append("sortBy=").append(sortBy).append("&");
        }

        if (direction != null && !direction.isBlank()) {
            url.append("direction=").append(direction).append("&");
        }

        if (url.charAt(url.length() - 1) == '&'
                || url.charAt(url.length() - 1) == '?') {
            url.deleteCharAt(url.length() - 1);
        }

        resp.sendRedirect(url.toString());
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

    private int getCurrentPage(HttpServletRequest req) {

        String page = req.getParameter("page");

        if (page == null || page.isBlank()) {
            return 1;
        }

        try {

            int currentPage = Integer.parseInt(page);

            return Math.max(currentPage, 1);

        } catch (NumberFormatException e) {

            return 1;
        }
    }

    private String getSortBy(HttpServletRequest req) {
        String sortBy = req.getParameter("sortBy");
        if (sortBy == null || sortBy.isBlank()) {
            return "id";
        }
       return  switch (sortBy) {
             case  "id" ,
              "first_name",
               "last_name" ,
               "email" ,
              "department" ,
               "semester" -> sortBy;

            default ->  "id";

         };

    }

    private String getSortDirection(HttpServletRequest req) {
        String direction =  req.getParameter("direction");
        if (direction == null || direction.isBlank()) {
            return "asc";
        }

       return switch (direction) {
            case "asc",
                 "desc" -> direction;
            default -> "asc";
        };
    }
}
package com.gradetracker.controller;

import com.gradetracker.model.User;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.gradetracker.enums.Role;
import com.gradetracker.model.User;

public abstract class BaseServlet extends HttpServlet {

    protected static final int PAGE_SIZE = 10;


    protected void preserveListState(HttpServletRequest req) {

        req.setAttribute("keyword", req.getParameter("keyword"));
        req.setAttribute("page", req.getParameter("page"));
        req.setAttribute("sortBy", req.getParameter("sortBy"));
        req.setAttribute("direction", req.getParameter("direction"));
    }



    protected void addFlashMessage(HttpServletRequest req,
                                   String key,
                                   String message) {

        if (message == null || message.isBlank()) {
            return;
        }

        req.getSession().setAttribute(key, message);
    }

    protected void showFlashMessage(HttpServletRequest req,
                                    String key) {

        HttpSession session = req.getSession();

        String message = (String) session.getAttribute(key);

        if (message != null) {
            req.setAttribute(key, message);
            session.removeAttribute(key);
        }
    }

    protected void addSuccessMessage(HttpServletRequest req, String message){

        addFlashMessage(req,"successMessage",message );

    }

    protected void showSuccessMessage(HttpServletRequest req){

        showFlashMessage(req,"successMessage");
    }

    protected void addErrorMessage(HttpServletRequest req, String message) {
        addFlashMessage(req,"errorMessage",message);
    }

    protected void showErrorMessage(HttpServletRequest req) {

        showFlashMessage(req,"errorMessage");
    }

    protected int getCurrentPage(HttpServletRequest req){

        String page = req.getParameter("page");

        if(page == null || page.isBlank()){
            return 1;
        }

        try {
            return Math.max(Integer.parseInt(page), 1);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    protected String getSortDirection(HttpServletRequest req){

        String direction =  req.getParameter("direction");

        if(direction == null || direction.isBlank()){
            return "asc";
        }

        return switch(direction.toLowerCase()){
            case "asc", "desc" -> direction.toLowerCase();
            default -> "asc";
        };
    }

    protected Integer parseInteger(String value) {

        if (value == null || value.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected HttpSession getSession(HttpServletRequest req) {
        return req.getSession();
    }

    protected HttpSession getSession(HttpServletRequest req,
                                     boolean create) {
        return req.getSession(create);
    }

    protected User getLoggedInUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if (session == null) {
            return null;
        }

        return (User) session.getAttribute("loggedInUser");
    }

    protected boolean isLoggedIn(HttpServletRequest req) {
        return getLoggedInUser(req) != null;
    }

    protected boolean hasRole(HttpServletRequest req, Role role) {

        User user = getLoggedInUser(req);

        return user != null && user.getRole() == role;
    }





}

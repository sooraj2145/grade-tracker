package com.gradetracker.controller;


import com.gradetracker.model.User;
import com.gradetracker.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet  extends BaseServlet {

    public UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if(session != null &&
                session.getAttribute("loggedInUser") != null){

            resp.sendRedirect(req.getContextPath() + "/students");
            return;
        }

        showSuccessMessage(req);
        showErrorMessage(req);

        req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = userService.authenticate(username, password);

        if(user == null){

            req.setAttribute("username", username);

            addErrorMessage(req,"Invalid username or password");

            showErrorMessage(req);

            req.getRequestDispatcher("/auth/login.jsp").forward(req, resp);

            return;
        }

        HttpSession session = req.getSession(true);

        session.setAttribute("loggedInUser", user);

        addSuccessMessage(req,"Welcome, " +  user.getFullName() + "!");

        resp.sendRedirect(req.getContextPath()+"/students");
    }
}

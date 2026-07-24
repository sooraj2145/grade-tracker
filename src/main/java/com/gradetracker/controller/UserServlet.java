package com.gradetracker.controller;


import com.gradetracker.enums.Role;
import com.gradetracker.model.User;
import com.gradetracker.service.ServiceResult;
import com.gradetracker.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UserServlet extends BaseServlet {

    private final UserService userService = new UserService();



    private static final String ACTION_NEW = "new";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_RESET_PASSWORD = "reset-password";
    private static final String ACTION_SAVE = "save";
    private static final String ACTION_UPDATE = "update";
    private static final String ACTION_DEACTIVATE = "deactivate";

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if(action == null || action.isBlank()){
            listUsers(req,resp);
            return;
        }

        switch(action){
            case ACTION_NEW -> showCreateForm(req,resp);
            case ACTION_EDIT -> showEditForm(req,resp);
            case ACTION_RESET_PASSWORD -> showResetPasswordForm(req, resp);

            default -> listUsers(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null || action.isBlank()){
            resp.sendRedirect(req.getContextPath()+"/users");
            return;
        }

        switch(action){

            case ACTION_SAVE -> createUser(req,resp);
            case ACTION_UPDATE -> updateUser(req,resp);
            case ACTION_DEACTIVATE -> deactivateUser(req, resp);
            case ACTION_RESET_PASSWORD -> resetPassword(req,resp);
            default -> resp.sendRedirect(req.getContextPath()+"/users");
        }
    }

    private void listUsers(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        showSuccessMessage(req);
        showErrorMessage(req);

        List<User> users = userService.getAllUsers();

        req.setAttribute("users", users);

        req.getRequestDispatcher("/user/user-list.jsp").forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("user", new User());

        req.getRequestDispatcher("/user/user-form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = resolveUser(req, resp);

        if(user == null){
            return;
        }

        req.setAttribute("user", user);
        req.getRequestDispatcher("/user/user-form.jsp").forward(req, resp);
    }

    private void showResetPasswordForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = resolveUser(req, resp);

        if(user == null){
            return;
        }

        req.setAttribute("user", user);

        req.getRequestDispatcher("/user/reset-password.jsp").forward(req, resp);
    }


    private void createUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = buildUserFromRequest(req);

        saveUser(req, resp, user, false);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = buildUserFromRequest(req);

        saveUser(req, resp, user, true);
    }

    private void saveUser(HttpServletRequest req,
                          HttpServletResponse resp,
                          User user,
                          boolean isUpdate)
            throws ServletException, IOException {

        ServiceResult<User> result = isUpdate
                ? userService.updateUser(user)
                : userService.createUser(user);

        if(result.isSuccess()){
            addSuccessMessage(req, result.getMessage());
            resp.sendRedirect(req.getContextPath()+"/users");
            return;
        }


        req.setAttribute("errorMessage", result.getMessage());
        req.setAttribute("user", user);

        req.getRequestDispatcher("/user/user-form.jsp").forward(req, resp);
    }

    private void deactivateUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer userId = getValidUserId(req, resp);

        if(userId == null){
            return;
        }

        ServiceResult<Void> result = userService.deleteUser(userId);

        if(result.isSuccess()){
            addSuccessMessage(req, result.getMessage());
        } else {
            addErrorMessage(req, result.getMessage());
        }

        resp.sendRedirect(req.getContextPath()+"/users");

    }

    private void resetPassword(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Integer userId = getValidUserId(req, resp);

        if(userId == null){
            return;
        }

        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if(newPassword == null || newPassword.isBlank()){
            forwardToResetPasswordWithError(req, resp, userId, "Password is required.");
            return;
        }

        if(!newPassword.equals(confirmPassword)){
            forwardToResetPasswordWithError(req, resp, userId, "Passwords do not match.");
            return;
        }

        ServiceResult<Void> result = userService.resetPassword(userId, newPassword);

        if(result.isSuccess()){
            addSuccessMessage(req, result.getMessage());
        } else {
            addErrorMessage(req, result.getMessage());
        }

        resp.sendRedirect(req.getContextPath()+"/users");
    }

    private void forwardToResetPasswordWithError(HttpServletRequest req,
                                                 HttpServletResponse resp,
                                                 Integer userId,
                                                 String message)
            throws ServletException, IOException {

        req.setAttribute("errorMessage", message);

        req.setAttribute("user", userService.getUserById(userId));

        req.getRequestDispatcher("/user/reset-password.jsp").forward(req, resp);
    }

    private Integer getValidUserId(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String id = req.getParameter("id");

        if(id == null || id.isBlank()){
            addErrorMessage(req, "Invalid user.");
            resp.sendRedirect(req.getContextPath()+"/users");
            return null;
        }

        return parseInteger(id);
    }

    private User resolveUser(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Integer id = getValidUserId(req, resp);

        if(id == null){
            return null;
        }

        User user = userService.getUserById(id);

        if(user == null){
            addErrorMessage(req, "User not found.");
            resp.sendRedirect(req.getContextPath()+"/users");
            return null;
        }

        return user;
    }

    private User buildUserFromRequest(HttpServletRequest req) {
        User user = new User();

        String id = req.getParameter("id");

        if(id != null && !id.isBlank()){
            user.setId(parseInteger(id));
        }

        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        user.setFullName(req.getParameter("fullName"));
        user.setEmail(req.getParameter("email"));

        String role = req.getParameter("role");
        try {
            if (role != null && !role.isBlank()) {
                user.setRole(Role.valueOf(role));
            }
        }catch (IllegalArgumentException e){}

        user.setActive(req.getParameter("active") != null);

        return user;
    }



}
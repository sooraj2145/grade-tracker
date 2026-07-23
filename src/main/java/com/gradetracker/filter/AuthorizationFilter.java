package com.gradetracker.filter;


import com.gradetracker.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp  = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if(session == null) {
            chain.doFilter(req, resp);
            return;
        }

        User user = (User) session.getAttribute("loggedInUser");

        if(user == null) {
            chain.doFilter(req,  resp);
            return;
        }

        String uri = req.getRequestURI();
        String context = req.getContextPath();

        String path = uri.substring(context.length());

//        Admin Only

        if(path.startsWith("/users") && !user.isAdmin()) {

            resp.sendError(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        // student only

        if(path.startsWith("/my-grades") && !user.isStudent()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        chain.doFilter(req, resp);


    }
}

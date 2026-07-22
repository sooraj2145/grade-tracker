package com.gradetracker.filter;


import com.gradetracker.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                         jakarta.servlet.ServletResponse response,
                         FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String contextPath = req.getContextPath();

        String path = uri.substring(contextPath.length());

        HttpSession session = req.getSession(false);

        User loggedInUser = null;

        if(session != null) {
            loggedInUser = (User) session.getAttribute("loggedInUser");
        }

        boolean loggedIn = loggedInUser != null;

        boolean publicResource =
                    path.equals("/login")
                            || path.equals("/logout")
                            || path.startsWith("/css/")
                            || path.startsWith("/js/")
                            || path.startsWith("/images/")
                            || path.startsWith("/assets/");

        if(loggedIn || publicResource) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }

}

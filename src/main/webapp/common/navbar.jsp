<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/students">
            <i class="bi bi-mortarboard-fill me-2"></i>Grade Tracker
        </a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link ${fn:contains(pageContext.request.servletPath, '/students') ? 'active' : ''}"
               href="${pageContext.request.contextPath}/students">
                Students
            </a>
            <a class="nav-link ${fn:contains(pageContext.request.servletPath, '/subjects') ? 'active' : ''}"
               href="${pageContext.request.contextPath}/subjects">
                Subjects
            </a>
            <a class="nav-link ${fn:contains(pageContext.request.servletPath, '/grades') ? 'active' : ''}"
               href="${pageContext.request.contextPath}/grades">
                Grades
            </a>
        </div>
    </div>
</nav>


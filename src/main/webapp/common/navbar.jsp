<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">

    <div class="container">

        <a class="navbar-brand"
           href="${pageContext.request.contextPath}/students">
            <i class="bi bi-mortarboard-fill me-2"></i>
            Grade Tracker
        </a>

        <button class="navbar-toggler" type="button"
                data-bs-toggle="collapse"
                data-bs-target="#navbarContent"
                aria-controls="navbarContent"
                aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarContent">

            <div class="navbar-nav me-auto">

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

            <!-- Right Side -->

            <div class="navbar-nav ms-auto align-items-lg-center">

                <c:if test="${not empty sessionScope.loggedInUser}">

                    <span class="navbar-text text-white me-lg-3 mb-2 mb-lg-0">
                        Welcome, <i class="bi bi-person-circle me-1"></i>
                        <strong>${sessionScope.loggedInUser.fullName}</strong>
                        <span class="badge bg-info ms-2">
                            ${sessionScope.loggedInUser.role}
                        </span>
                    </span>

                    <a class="btn btn-outline-light btn-sm"
                       href="${pageContext.request.contextPath}/logout">
                        <i class="bi bi-box-arrow-right me-1"></i>
                        Logout
                    </a>

                </c:if>

            </div>

        </div>

    </div>

</nav>

<style>

</style>
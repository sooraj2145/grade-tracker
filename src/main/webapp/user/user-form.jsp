<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:choose>
    <c:when test="${empty user.id}">
        <c:set var="pageTitle" value="Create User"/>
        <c:set var="action" value="save"/>
        <c:set var="buttonText" value="Create User"/>
    </c:when>
    <c:otherwise>
        <c:set var="pageTitle" value="Edit User"/>
        <c:set var="action" value="update"/>
        <c:set var="buttonText" value="Update User"/>
    </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${pageTitle}</title>

    <jsp:include page="/common/imports.jsp"/>
</head>

<body>

<jsp:include page="/common/navbar.jsp"/>

<div class="container py-5">

    <jsp:include page="/common/messages.jsp"/>

    <div class="row justify-content-center">

        <div class="col-xl-5 col-lg-6 col-md-8">

            <div class="card shadow">

                <div class="card-header bg-primary text-white">

                    <h4 class="mb-0">
                        <i class="bi bi-person-fill me-2"></i>
                        ${pageTitle}
                    </h4>

                </div>

                <div class="card-body">

                    <form method="post"
                          action="${pageContext.request.contextPath}/users?action=${action}">

                        <c:if test="${not empty user.id}">
                            <input
                                    type="hidden"
                                    name="id"
                                    value="${user.id}">
                        </c:if>

                        <div class="mb-3">

                            <label class="form-label">
                                Username
                            </label>

                            <input
                                    type="text"
                                    class="form-control"
                                    name="username"
                                    value="${user.username}"
                                    required>

                        </div>

                        <c:if test="${empty user.id}">

                            <div class="mb-3">

                                <label class="form-label">
                                    Password
                                </label>

                                <input
                                        type="password"
                                        class="form-control"
                                        name="password"
                                        required>

                            </div>

                        </c:if>

                        <div class="mb-3">

                            <label class="form-label">
                                Full Name
                            </label>

                            <input
                                    type="text"
                                    class="form-control"
                                    name="fullName"
                                    value="${user.fullName}"
                                    required>

                        </div>

                        <div class="mb-3">

                            <label class="form-label">
                                Email
                            </label>

                            <input
                                    type="email"
                                    class="form-control"
                                    name="email"
                                    value="${user.email}"
                                    required>

                        </div>

                        <div class="mb-3">

                            <label class="form-label">
                                Role
                            </label>

                            <select
                                    class="form-select"
                                    name="role"
                                    required>

                                <option value="">Select Role</option>

                                <option value="ADMIN"
                                    <c:if test="${user.role == 'ADMIN'}">selected</c:if>>
                                    Admin
                                </option>

                                <option value="FACULTY"
                                    <c:if test="${user.role == 'FACULTY'}">selected</c:if>>
                                    Faculty
                                </option>

                                <option value="STUDENT"
                                    <c:if test="${user.role == 'STUDENT'}">selected</c:if>>
                                    Student
                                </option>

                            </select>

                        </div>

                        <div class="form-check mb-4">

                            <input
                                    class="form-check-input"
                                    type="checkbox"
                                    id="active"
                                    name="active"
                                    <c:if test="${empty user.id || user.active}">
                                        checked
                                    </c:if>>

                            <label
                                    class="form-check-label"
                                    for="active">

                                Active User

                            </label>

                        </div>

                        <div class="d-flex justify-content-end gap-2 flex-wrap">

                            <a
                                    href="${pageContext.request.contextPath}/users"
                                    class="btn btn-secondary">

                                Cancel

                            </a>

                            <button
                                    type="submit"
                                    class="btn btn-primary">

                                ${buttonText}

                            </button>

                        </div>

                    </form>

                </div>

            </div>

        </div>

    </div>

</div>

<jsp:include page="/common/footer.jsp"/>

<jsp:include page="/common/scripts.jsp"/>

</body>
</html>
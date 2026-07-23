<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>

<html>

<head>

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Users</title>

    <jsp:include page="/common/imports.jsp"/>

</head>

<body>

<jsp:include page="/common/navbar.jsp"/>

<div class="container mt-4">

    <h2>User Management</h2>

    <div class="container py-5">

        <jsp:include page="/common/messages.jsp"/>

        <div class="d-flex justify-content-between align-items-center mb-4">

            <h2 class="mb-0">
                <i class="bi bi-people-fill me-2"></i>
                User Management
            </h2>

            <a
                href="${pageContext.request.contextPath}/users?action=new"
                class="btn btn-success">

                <i class="bi bi-plus-circle"></i>

                Add User

            </a>

        </div>

        <div class="card shadow-sm">

            <div class="card-body p-0">

             <table class="table table-striped table-hover align-middle mb-0">

                <thead class="table-dark">

                <tr>



                <th>ID</th>
                <th>Username</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Status</th>
                <th>Actions</th>


        </tr>

        </thead>

        <tbody>

        <c:forEach items="${users}" var="user">

            <tr>

                <td>${user.id}</td>

                <td>${user.username}</td>

                <td>${user.fullName}</td>

                <td>${user.email}</td>

                <td>

                    <c:choose>

                        <c:when test="${user.role == 'ADMIN'}">
                            <span class="badge bg-danger">ADMIN</span>
                        </c:when>

                        <c:when test="${user.role == 'FACULTY'}">
                            <span class="badge bg-warning text-dark">FACULTY</span>
                        </c:when>

                        <c:otherwise>
                            <span class="badge bg-info">STUDENT</span>
                        </c:otherwise>

                    </c:choose>

                </td>

                <td>

                    <c:choose>

                        <c:when test="${user.active}">
                            <span class="badge bg-success">Active</span>
                        </c:when>

                        <c:otherwise>
                            <span class="badge bg-danger">Inactive</span>
                        </c:otherwise>

                    </c:choose>

                </td>

                <td>

                    <a class="btn btn-warning btn-sm"
                       href="${pageContext.request.contextPath}/users?action=edit&id=${user.id}">
                        Edit
                    </a>

                    <a class="btn btn-secondary btn-sm"
                       href="${pageContext.request.contextPath}/users?action=reset-password&id=${user.id}">
                        Reset Password
                    </a>

                    <form action="${pageContext.request.contextPath}/users?action=deactivate"
                          method="post"
                          style="display:inline;">

                        <input type="hidden"
                               name="id"
                               value="${user.id}">

                        <button type="submit"
                                class="btn btn-danger btn-sm"
                                onclick="return confirm('Deactivate this user?')">

                            Deactivate

                        </button>

                    </form>

                </td>

            </tr>

        </c:forEach>

        </tbody>

    </table>
    </div>
    </div>

</div>
<jsp:include page="/common/footer.jsp"/>

<jsp:include page="/common/scripts.jsp"/>

</body>

</html>
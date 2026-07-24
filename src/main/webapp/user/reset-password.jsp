<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">

    <title>Reset Password</title>

    <jsp:include page="/common/imports.jsp"/>

</head>

<body>

<jsp:include page="/common/navbar.jsp"/>

<div class="container py-5">

    <jsp:include page="/common/messages.jsp"/>

    <div class="row justify-content-center">

        <div class="col-xl-5 col-lg-6 col-md-8">

            <div class="card shadow">

                <div class="card-header bg-warning text-dark">

                    <h4 class="mb-0">
                        <i class="bi bi-key-fill me-2"></i>
                        Reset Password
                    </h4>

                </div>

                <div class="card-body">

                    <form method="post"
                          action="${pageContext.request.contextPath}/users">

                        <input type="hidden"
                               name="action"
                               value="reset-password">

                        <input type="hidden"
                               name="id"
                               value="${user.id}">

                        <div class="mb-3">

                            <label class="form-label">
                                Username
                            </label>

                            <input
                                    type="text"
                                    class="form-control"
                                    value="${user.username}"
                                    readonly>

                        </div>

                        <div class="mb-3">

                            <label class="form-label">
                                Full Name
                            </label>

                            <input
                                    type="text"
                                    class="form-control"
                                    value="${user.fullName}"
                                    readonly>

                        </div>

                        <div class="mb-3">

                            <label class="form-label">
                                Role
                            </label>

                            <input
                                    type="text"
                                    class="form-control"
                                    value="${user.role}"
                                    readonly>

                        </div>

                        <hr>

                        <div class="mb-3">

                            <label class="form-label">
                                New Password
                            </label>

                            <input
                                    type="password"
                                    class="form-control"
                                    name="newPassword"
                                    required>

                        </div>

                        <div class="mb-4">

                            <label class="form-label">
                                Confirm Password
                            </label>

                            <input
                                    type="password"
                                    class="form-control"
                                    name="confirmPassword"
                                    required>

                        </div>

                        <div class="d-flex justify-content-end gap-2">

                            <a
                                    href="${pageContext.request.contextPath}/users"
                                    class="btn btn-secondary">

                                Cancel

                            </a>

                            <button
                                    type="submit"
                                    class="btn btn-warning">

                                <i class="bi bi-key-fill me-1"></i>
                                Reset Password

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
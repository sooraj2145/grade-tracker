<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>

    <title>Subject Form</title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.7/dist/css/bootstrap.min.css"
        rel="stylesheet">

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
        rel="stylesheet">

</head>

<body class="bg-light">

    <div class="container py-5">
        <div class="row justify-content-center">
            <div class="col-lg-7">

                <div class="card shadow-sm border-0">

                    <div class="card-header bg-white py-3">
                        <h4 class="mb-0">
                            <c:choose>
                                <c:when test="${subject != null}">
                                    <i class="bi bi-pencil-square me-2"></i>Edit Subject
                                </c:when>

                                <c:otherwise>
                                    <i class="bi bi-plus-circle me-2"></i>Add Subject
                                </c:otherwise>
                            </c:choose>
                        </h4>
                    </div>

                    <div class="card-body p-4">

                        <c:if test="${databaseError != null}">
                            <div class="alert alert-danger d-flex align-items-center" role="alert">
                                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                                <div>${databaseError}</div>
                            </div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/subjects"
                              method="post">

                            <c:choose>
                                <c:when test="${subject != null}">
                                    <input
                                        type="hidden"
                                        name="action"
                                        value="update">
                                    <input
                                        type="hidden"
                                        name="id"
                                        value="${subject.id}">
                                </c:when>

                                <c:otherwise>
                                    <input
                                        type="hidden"
                                        name="action"
                                        value="insert">
                                </c:otherwise>
                            </c:choose>

                            <div class="mb-3">
                                <label for="code" class="form-label fw-semibold">
                                    Subject Code
                                </label>

                                <input
                                    type="text"
                                    id="code"
                                    name="code"
                                    class="form-control ${errors.code != null ? 'is-invalid' : ''}"
                                    value="${subject.code}">

                                <c:if test="${errors.code != null}">
                                    <div class="text-danger mt-1 small">
                                        ${errors.code}
                                    </div>
                                </c:if>
                            </div>

                            <div class="mb-3">
                                <label for="name" class="form-label fw-semibold">
                                    Subject Name
                                </label>

                                <input
                                    type="text"
                                    id="name"
                                    name="name"
                                    class="form-control ${errors.name != null ? 'is-invalid' : ''}"
                                    value="${subject.name}">

                                <c:if test="${errors.name != null}">
                                    <div class="text-danger mt-1 small">
                                        ${errors.name}
                                    </div>
                                </c:if>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="credits" class="form-label fw-semibold">
                                        Credits
                                    </label>

                                    <input
                                        type="text"
                                        id="credits"
                                        name="credits"
                                        class="form-control ${errors.credits != null ? 'is-invalid' : ''}"
                                        value="${subject.credits}">

                                    <c:if test="${errors.credits != null}">
                                        <div class="text-danger mt-1 small">
                                            ${errors.credits}
                                        </div>
                                    </c:if>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label for="semester" class="form-label fw-semibold">
                                        Semester
                                    </label>

                                    <input
                                        type="text"
                                        id="semester"
                                        name="semester"
                                        class="form-control ${errors.semester != null ? 'is-invalid' : ''}"
                                        value="${subject.semester}">

                                    <c:if test="${errors.semester != null}">
                                        <div class="text-danger mt-1 small">
                                            ${errors.semester}
                                        </div>
                                    </c:if>
                                </div>
                            </div>

                            <hr class="my-4">

                            <div class="d-flex justify-content-end gap-2">

                                <a href="${pageContext.request.contextPath}/subjects"
                                   class="btn btn-secondary">
                                    Cancel
                                </a>

                                <button
                                    type="submit"
                                    class="btn btn-primary">
                                    <i class="bi bi-check-lg me-1"></i>Save Subject
                                </button>

                            </div>

                        </form>

                    </div>
                </div>

            </div>
        </div>
    </div>

</body>
</html>
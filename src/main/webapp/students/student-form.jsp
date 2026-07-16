<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>
    <title>${student != null && student.id > 0 ? "Edit Student" : "Add Student"}</title>

    <jsp:include page="/common/imports.jsp"/>
</head>

<body>

<c:set var="editMode" value="${student != null && student.id > 0}" />

<c:choose>
    <c:when test="${editMode}">
        <c:set var="formAction"
               value="${pageContext.request.contextPath}/students?action=update"/>
    </c:when>

    <c:otherwise>
        <c:set var="formAction"
               value="${pageContext.request.contextPath}/students"/>
    </c:otherwise>
</c:choose>

<jsp:include page="/common/navbar.jsp"/>

<div class="container mt-5">

    <div class="row justify-content-center">

        <div class="col-lg-6 col-md-8">

            <div class="card shadow">

                <div class="card-header bg-primary text-white">

                    <h4 class="mb-0">

                        <c:choose>

                            <c:when test="${editMode}">
                                <i class="bi bi-pencil-square me-2"></i>
                                Edit Student
                            </c:when>

                            <c:otherwise>
                                <i class="bi bi-person-plus-fill me-2"></i>
                                Add Student
                            </c:otherwise>

                        </c:choose>

                    </h4>

                </div>

                <div class="card-body">

                    <form action="${formAction}" method="post">

                        <c:if test="${not empty keyword}">
                            <input
                                type="hidden"
                                name="keyword"
                                value="${keyword}">
                        </c:if>

                        <c:if test="${editMode}">
                            <input type="hidden"
                                   name="id"
                                   value="${student.id}">
                        </c:if>

                        <!-- First Name -->

                        <div class="mb-3">

                            <label class="form-label">
                                First Name
                            </label>

                            <input
                                    class="form-control"
                                    type="text"
                                    name="firstName"
                                    value="${student.firstName}">

                            <c:if test="${errors.firstName != null}">
                                <div class="text-danger mt-1">
                                    ${errors.firstName}
                                </div>
                            </c:if>

                        </div>

                        <!-- Last Name -->

                        <div class="mb-3">

                            <label class="form-label">
                                Last Name
                            </label>

                            <input
                                    class="form-control"
                                    type="text"
                                    name="lastName"
                                    value="${student.lastName}">

                            <c:if test="${errors.lastName != null}">
                                <div class="text-danger mt-1">
                                    ${errors.lastName}
                                </div>
                            </c:if>

                        </div>

                        <!-- Email -->

                        <div class="mb-3">

                            <label class="form-label">
                                Email
                            </label>

                            <input
                                    class="form-control"
                                    type="email"
                                    name="email"
                                    value="${student.email}">

                            <c:if test="${errors.email != null}">
                                <div class="text-danger mt-1">
                                    ${errors.email}
                                </div>
                            </c:if>

                        </div>

                        <!-- Department -->

                        <div class="mb-3">

                            <label class="form-label">
                                Department
                            </label>

                            <input
                                    class="form-control"
                                    type="text"
                                    name="department"
                                    value="${student.department}">

                            <c:if test="${errors.department != null}">
                                <div class="text-danger mt-1">
                                    ${errors.department}
                                </div>
                            </c:if>

                        </div>

                        <!-- Semester -->

                        <div class="mb-4">

                            <label class="form-label">
                                Semester
                            </label>

                            <input
                                    class="form-control"
                                    type="number"
                                    min="1"
                                    max="8"
                                    name="semester"
                                    value="${student.semester}">

                            <c:if test="${errors.semester != null}">
                                <div class="text-danger mt-1">
                                    ${errors.semester}
                                </div>
                            </c:if>

                        </div>

                        <!-- Buttons -->

                        <div class="d-flex justify-content-end gap-2">

                            <a href="${pageContext.request.contextPath}/students"
                               class="btn btn-secondary">

                                <i class="bi bi-arrow-left"></i>
                                Cancel

                            </a>

                            <c:choose>

                                <c:when test="${editMode}">

                                    <button
                                            type="submit"
                                            class="btn btn-warning">

                                        <i class="bi bi-check-circle"></i>
                                        Update Student

                                    </button>

                                </c:when>

                                <c:otherwise>

                                    <button
                                            type="submit"
                                            class="btn btn-success">

                                        <i class="bi bi-save"></i>
                                        Save Student

                                    </button>

                                </c:otherwise>

                            </c:choose>

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
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>${grade != null && grade.id > 0 ? "Edit Grade" : "Add Grade"}</title>

    <jsp:include page="/common/imports.jsp"/>

</head>

<body>

<c:set var="editMode" value="${grade != null && grade.id > 0}" />

<c:choose>

    <c:when test="${editMode}">
        <c:set var="formAction"
               value="${pageContext.request.contextPath}/grades?action=update"/>
    </c:when>

    <c:otherwise>
        <c:set var="formAction"
               value="${pageContext.request.contextPath}/grades?action=insert"/>
    </c:otherwise>

</c:choose>

<jsp:include page="/common/navbar.jsp"/>

<div class="container mt-5">

    <div class="row justify-content-center">

        <div class="col-lg-7 col-md-9">

            <div class="card shadow">

                <div class="card-header bg-primary text-white">

                    <h4 class="mb-0">

                        <c:choose>

                            <c:when test="${editMode}">
                                <i class="bi bi-pencil-square me-2"></i>
                                Edit Grade
                            </c:when>

                            <c:otherwise>
                                <i class="bi bi-journal-plus me-2"></i>
                                Add Grade
                            </c:otherwise>

                        </c:choose>

                    </h4>

                </div>

                <div class="card-body">

                    <form action="${formAction}" method="post">

                        <c:if test="${editMode}">
                            <input
                                    type="hidden"
                                    name="id"
                                    value="${grade.id}">
                        </c:if>

                        <!-- Student -->

                        <div class="mb-3">

                            <label class="form-label">
                                Student
                            </label>

                            <select
                                    name="studentId"
                                    class="form-select ${errors.studentId != null ? 'is-invalid' : ''}">

                                <option value="">
                                    Select Student
                                </option>

                                <c:forEach var="student" items="${students}">

                                    <option
                                            value="${student.id}"
                                            ${grade.studentId == student.id ? 'selected' : ''}>

                                            ${student.firstName}
                                            ${student.lastName}

                                    </option>

                                </c:forEach>

                            </select>

                            <c:if test="${errors.studentId != null}">
                                <div class="invalid-feedback d-block">
                                    ${errors.studentId}
                                </div>
                            </c:if>

                        </div>

                        <!-- Subject -->

                        <div class="mb-3">

                            <label class="form-label">
                                Subject
                            </label>

                            <select
                                    name="subjectId"
                                    class="form-select ${errors.subjectId != null ? 'is-invalid' : ''}">

                                <option value="">
                                    Select Subject
                                </option>

                                <c:forEach var="subject" items="${subjects}">

                                    <option
                                            value="${subject.id}"
                                            ${grade.subjectId == subject.id ? 'selected' : ''}>

                                            ${subject.name}

                                    </option>

                                </c:forEach>

                            </select>

                            <c:if test="${errors.subjectId != null}">
                                <div class="invalid-feedback d-block">
                                    ${errors.subjectId}
                                </div>
                            </c:if>

                        </div>
                        <!-- Marks -->

                        <div class="mb-3">

                            <label class="form-label">
                                Marks
                            </label>

                            <input
                                    type="number"
                                    step="0.01"
                                    min="0"
                                    max="100"
                                    name="marks"
                                    class="form-control ${errors.marks != null ? 'is-invalid' : ''}"
                                    value="${grade.marks}">

                            <c:if test="${errors.marks != null}">
                                <div class="invalid-feedback d-block">
                                    ${errors.marks}
                                </div>
                            </c:if>

                        </div>





                        <!-- Remarks -->

                        <div class="mb-4">

                            <label class="form-label">
                                Remarks
                            </label>

                            <textarea
                                    rows="4"
                                    name="remarks"
                                    class="form-control ${errors.remarks != null ? 'is-invalid' : ''}">${grade.remarks}</textarea>

                            <c:if test="${errors.remarks != null}">
                                <div class="invalid-feedback d-block">
                                    ${errors.remarks}
                                </div>
                            </c:if>

                        </div>

                        <!-- Buttons -->

                        <div class="d-flex justify-content-end gap-2">

                            <a href="${pageContext.request.contextPath}/grades"
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
                                        Update Grade

                                    </button>

                                </c:when>

                                <c:otherwise>

                                    <button
                                            type="submit"
                                            class="btn btn-success">

                                        <i class="bi bi-save"></i>
                                        Save Grade

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
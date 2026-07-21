<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>

    <title>Grades</title>

    <jsp:include page="/common/imports.jsp"/>

</head>

<body>

<jsp:include page="/common/navbar.jsp"/>

<div class="container mt-5">

<c:if test="${not empty successMessage}">
    <div class="alert alert-success alert-dismissible fade show mt-3">
        ${successMessage}
        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert"></button>
    </div>
</c:if>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger alert-dismissible fade show mt-3">
        ${errorMessage}
        <button type="button"
                class="btn-close"
                data-bs-dismiss="alert"></button>
    </div>
</c:if>



    <div class="d-flex justify-content-between align-items-center mb-4">

        <h2>
            <i class="bi bi-journal-check"></i>
            Grade Management
        </h2>

        <a
                href="${pageContext.request.contextPath}/grades?action=new"
                class="btn btn-primary">

            <i class="bi bi-plus-circle"></i>
            Add Grade

        </a>

    </div>

    <form
            method="get"
            action="${pageContext.request.contextPath}/grades"
            class="row g-3 mb-4">

        <div class="col-md-6">

            <input
                    type="text"
                    class="form-control"
                    name="keyword"
                    value="${keyword}"
                    placeholder="Search by student, subject, grade or remarks">

        </div>

        <div class="col-md-2">

            <button
                    class="btn btn-success w-100">

                <i class="bi bi-search"></i>
                Search

            </button>

        </div>

        <div class="col-md-2">

            <a
                    href="${pageContext.request.contextPath}/grades"
                    class="btn btn-secondary w-100">

                Reset

            </a>

        </div>

    </form>

     <div class="card shadow">

         <div class="card-body table-responsive">

             <table class="table table-striped table-hover align-middle">

                 <thead class="table-dark">

                 <tr>

                     <th>

                         <a class="text-white text-decoration-none"
                            href="${pageContext.request.contextPath}/grades?page=${currentPage}&keyword=${keyword}&sortBy=id&direction=${sortBy == 'id' && direction == 'ASC' ? 'DESC' : 'ASC'}">

                             ID

                         </a>

                     </th>

                     <th>

                         <a class="text-white text-decoration-none"
                            href="${pageContext.request.contextPath}/grades?page=${currentPage}&keyword=${keyword}&sortBy=student_name&direction=${sortBy == 'student_name' && direction == 'ASC' ? 'DESC' : 'ASC'}">

                             Student

                         </a>

                     </th>

                     <th>

                         <a class="text-white text-decoration-none"
                            href="${pageContext.request.contextPath}/grades?page=${currentPage}&keyword=${keyword}&sortBy=subject_name&direction=${sortBy == 'subject_name' && direction == 'ASC' ? 'DESC' : 'ASC'}">

                             Subject

                         </a>

                     </th>

                     <th>

                         <a class="text-white text-decoration-none"
                            href="${pageContext.request.contextPath}/grades?page=${currentPage}&keyword=${keyword}&sortBy=marks&direction=${sortBy == 'marks' && direction == 'ASC' ? 'DESC' : 'ASC'}">

                             Marks

                         </a>

                     </th>

                     <th>

                         <a class="text-white text-decoration-none"
                            href="${pageContext.request.contextPath}/grades?page=${currentPage}&keyword=${keyword}&sortBy=grade&direction=${sortBy == 'grade' && direction == 'ASC' ? 'DESC' : 'ASC'}">

                             Grade

                         </a>

                     </th>

                     <th>

                         Remarks

                     </th>

                     <th class="text-center">

                         Actions

                     </th>

                 </tr>

                 </thead>

                 <tbody>

                 <c:choose>

                     <c:when test="${not empty grades}">

                         <c:forEach var="grade" items="${grades}">

                             <tr>

                                 <td>${grade.id}</td>

                                 <td>${grade.studentName}</td>

                                 <td>${grade.subjectName}</td>

                                 <td>${grade.marks}</td>

                                 <td>

                                     <span class="badge bg-success">

                                         ${grade.grade}

                                     </span>

                                 </td>

                                 <td>${grade.remarks}</td>

                                 <td class="text-center">

                                     <a
                                             href="${pageContext.request.contextPath}/grades?action=edit&id=${grade.id}"
                                             class="btn btn-warning btn-sm">

                                         <i class="bi bi-pencil-square"></i>

                                     </a>

                                     <a
                                             href="${pageContext.request.contextPath}/grades?action=delete&id=${grade.id}"
                                             class="btn btn-danger btn-sm"
                                             onclick="return confirm('Are you sure you want to delete this grade?');">

                                         <i class="bi bi-trash"></i>

                                     </a>

                                 </td>

                             </tr>

                         </c:forEach>

                     </c:when>

                     <c:otherwise>

                         <tr>

                             <td colspan="7" class="text-center text-muted py-4">

                                 <i class="bi bi-info-circle"></i>

                                 No grades found.

                             </td>

                         </tr>

                     </c:otherwise>

                 </c:choose>

                 </tbody>

             </table>

         </div>

     </div>

    <!-- Pagination -->

    <c:if test="${totalPages > 1}">

        <nav class="mt-4">

            <ul class="pagination justify-content-center">

                <!-- Previous -->

                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">

                    <a class="page-link"
                       href="${pageContext.request.contextPath}/grades?page=${currentPage-1}&keyword=${keyword}&sortBy=${sortBy}&direction=${direction}">

                        Previous

                    </a>

                </li>

                <!-- Page Numbers -->

                <c:forEach begin="1" end="${totalPages}" var="page">

                    <li class="page-item ${page == currentPage ? 'active' : ''}">

                        <a class="page-link"
                           href="${pageContext.request.contextPath}/grades?page=${page}&keyword=${keyword}&sortBy=${sortBy}&direction=${direction}">

                            ${page}

                        </a>

                    </li>

                </c:forEach>

                <!-- Next -->

                <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">

                    <a class="page-link"
                       href="${pageContext.request.contextPath}/grades?page=${currentPage+1}&keyword=${keyword}&sortBy=${sortBy}&direction=${direction}">

                        Next

                    </a>

                </li>

            </ul>

        </nav>

    </c:if>

</div>

<jsp:include page="/common/footer.jsp"/>

<jsp:include page="/common/scripts.jsp"/>

</body>

</html>

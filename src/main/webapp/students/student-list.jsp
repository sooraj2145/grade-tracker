<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>
    <title>Students</title>

    <jsp:include page="/common/imports.jsp"/>
</head>

<body>

<jsp:include page="/common/navbar.jsp"/>

<div class="container py-5">

    <jsp:include page="/common/messages.jsp"/>

    <!-- Page Header -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">
            <i class="bi bi-people-fill me-2"></i>
            Student List
        </h2>

        <a href="${pageContext.request.contextPath}/students?action=new"
           class="btn btn-success">
            <i class="bi bi-plus-circle"></i>
            Add Student
        </a>
    </div>

    <!-- Search -->
    <div class="card shadow-sm mb-4">

        <div class="card-body">

            <form
                    method="get"
                    action="${pageContext.request.contextPath}/students"
                    class="row g-2">
                <input
                        type="hidden"
                        name="sortBy"
                        value="${sortBy}">

                <input
                        type="hidden"
                        name="direction"
                        value="${direction}">

                <div class="col-md">
                    <input
                            type="text"
                            class="form-control"
                            name="keyword"
                            value="${keyword}"
                            placeholder="Search by name, email or department...">
                </div>

                <div class="col-auto">

                    <button
                            type="submit"
                            class="btn btn-primary">

                        <i class="bi bi-search"></i>
                        Search

                    </button>

                </div>

                <div class="col-auto">

                    <a
                            href="${pageContext.request.contextPath}/students"
                            class="btn btn-outline-secondary">

                        Clear

                    </a>

                </div>

            </form>

        </div>

    </div>

    <!-- Search Result -->
    <c:if test="${not empty keyword}">

        <div class="alert alert-info">

            Showing results for

            <strong>"${keyword}"</strong>

        </div>

    </c:if>

    <!-- Student Table -->
    <div class="card shadow-sm">

        <div class="card-body p-0">

            <table class="table table-striped table-hover align-middle mb-0">

                <thead class="table-dark">

                <c:set var="idNextDirection" value="asc"/>

                <c:if test="${sortBy == 'id' && direction == 'asc'}">
                    <c:set var="idNextDirection" value="desc" />
                </c:if>

                <c:set var="nameNextDirection" value="asc"/>
                <c:if test="${sortBy == 'first_name' && direction == 'asc'}">
                   <c:set var="nameNextDirection" value="desc" />
                </c:if>

                 <c:set var="emailNextDirection" value="asc"/>
                 <c:if test="${sortBy == 'email' && direction == 'asc'}">
                    <c:set var="emailNextDirection" value="desc" />
                 </c:if>

                  <c:set var="departmentNextDirection" value="asc"/>
                                 <c:if test="${sortBy == 'department' && direction == 'asc'}">
                                    <c:set var="departmentNextDirection" value="desc" />
                                 </c:if>

                   <c:set var="semesterNextDirection" value="asc"/>
                                  <c:if test="${sortBy == 'semester' && direction == 'asc'}">
                                     <c:set var="semesterNextDirection" value="desc" />
                                  </c:if>

                <tr>
                    <c:url var="idSortUrl" value="/students">
                        <c:param name="page" value="1"/>
                        <c:param name="keyword" value="${keyword}"/>
                        <c:param name="sortBy" value="id"/>
                        <c:param name="direction" value="${idNextDirection}"/>
                    </c:url>
                    <th class="text-center">
                        <a
                            href="${isSortUrl}"
                            class="text-decoration-none text-white">
                        ID
                        <c:if test="${sortBy == 'id'}">
                                                        <c:choose>
                                                            <c:when test="${direction == 'asc'}">
                                                                 ▲
                                                            </c:when>
                                                            <c:otherwise>
                                                                 ▼
                                                             </c:otherwise>
                                                         </c:choose>
                                                    </c:if>
                        </a>
                     </th>
                    <th>
                        <a
                            href="${pageContext.request.contextPath}/students?page=1&keyword=${keyword}&sortBy=first_name&direction=${nameNextDirection}"
                            class="text-decoration-none text-white">
                            Name
                            <c:if test="${sortBy == 'first_name'}">
                                <c:choose>
                                    <c:when test="${direction == 'asc'}">
                                         ▲
                                    </c:when>
                                    <c:otherwise>
                                         ▼
                                     </c:otherwise>
                                 </c:choose>
                            </c:if>
                        </a>
                    </th>
                    <th>
                         <a
                             href="${pageContext.request.contextPath}/students?page=1&keyword=${keyword}&sortBy=email&direction=${emailNextDirection}"
                               class="text-decoration-none text-white">
                                Email
                                  <c:if test="${sortBy == 'email'}">
                                   <c:choose>
                                   <c:when test="${direction == 'asc'}">
                                         ▲
                                       </c:when>
                                       <c:otherwise>
                                         ▼
                                       </c:otherwise>
                                       </c:choose>
                                       </c:if>
                                      </a>
                    </th>
                    <th>
                        <a
                           href="${pageContext.request.contextPath}/students?page=1&keyword=${keyword}&sortBy=department&direction=${departmentNextDirection}"
                           class="text-decoration-none text-white">
                           Department
                          <c:if test="${sortBy == 'department'}">
                             <c:choose>
                               <c:when test="${direction == 'asc'}">
                                                  ▲
                                    </c:when>
                                        <c:otherwise>
                                                ▼
                                  </c:otherwise>
                                   </c:choose>
                                 </c:if>
                             </a>
                    </th>
                    <th class="text-center">
                        <a
                             href="${pageContext.request.contextPath}/students?page=1&keyword=${keyword}&sortBy=semester&direction=${semesterNextDirection}"
                               class="text-decoration-none text-white">
                                  Semester
                          <c:if test="${sortBy == 'semester'}">
                              <c:choose>
                            <c:when test="${direction == 'asc'}">
                           ▲
                            </c:when>
                            <c:otherwise>
                                 ▼
                             </c:otherwise>
                            </c:choose>
                             </c:if>
                            </a>
                    </th>
                    <th class="text-center">Actions</th>
                </tr>

                </thead>

                <tbody>

                <c:choose>

                    <c:when test="${not empty students}">

                        <c:forEach items="${students}" var="student">

                            <tr>

                                <td class="text-center">
                                    ${student.id}
                                </td>

                                <td>
                                    ${student.firstName}
                                    ${student.lastName}
                                </td>

                                <td>
                                    ${student.email}
                                </td>

                                <td>
                                    ${student.department}
                                </td>

                                <td class="text-center">
                                    ${student.semester}
                                </td>

                                <td>

                                    <div class="d-flex justify-content-center gap-2">

                                        <a
                                                href="${pageContext.request.contextPath}/students?action=edit&id=${student.id}&keyword=${keyword}&page=${currentPage}&sortBy=${sortBy}&direction=${direction}"
                                                class="btn btn-warning btn-sm">

                                            <i class="bi bi-pencil-square"></i>
                                            Edit

                                        </a>

                                        <a
                                                href="${pageContext.request.contextPath}/students?action=delete&id=${student.id}&keyword=${keyword}&page=${currentPage}&sortBy=${sortBy}&direction=${direction}"
                                                class="btn btn-danger btn-sm"
                                                onclick="return confirm('Are you sure you want to delete this student?')">

                                            <i class="bi bi-trash"></i>
                                            Delete

                                        </a>

                                    </div>

                                </td>

                            </tr>

                        </c:forEach>

                    </c:when>

                    <c:otherwise>

                        <tr>

                            <td colspan="6" class="text-center py-5">

                                <i class="bi bi-search fs-1 text-primary"></i>

                                <div class="mt-3">

                                    <c:choose>

                                        <c:when test="${not empty keyword}">

                                            <h5>
                                                No students found matching
                                                "<strong>${keyword}</strong>"
                                            </h5>

                                            <p class="text-muted mb-0">
                                                Try searching with a different keyword.
                                            </p>

                                        </c:when>

                                        <c:otherwise>

                                            <h5>
                                                No students found.
                                            </h5>

                                            <p class="text-muted mb-0">
                                                Click <strong>Add Student</strong> to create your first student.
                                            </p>

                                        </c:otherwise>

                                    </c:choose>

                                </div>

                            </td>

                        </tr>

                    </c:otherwise>
                </c:choose>

                </tbody>

            </table>



        </div>


    </div>

    <nav aria-label="page navigation" class="mt-4">
            <ul class="pagination justify-content-center">
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                      <a class="page-link" href="${pageContext.request.contextPath}/students?page=${currentPage - 1}&keyword=${keyword}&sortBy=${sortBy}&direction=${direction}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                      </a>
                 </li>
                 <c:forEach begin="1"
                            end="${totalPages}"
                            var="page">

                  <li class="page-item ${page == currentPage ? 'active' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/students?page=${page}&keyword=${keyword}&sortBy=${sortBy}&direction=${direction}" aria-label="page link">
                            ${page}
                      </a>
                  </li>
                  </c:forEach>
                  <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                       <a class="page-link" href="${pageContext.request.contextPath}/students?page=${currentPage + 1}&keyword=${keyword}&sortBy=${sortBy}&direction=${direction}" aria-label="Next">
                         <span aria-hidden="true">&raquo;</span>
                       </a>
                  </li>
             </ul>

    </nav>

</div>

<jsp:include page="/common/footer.jsp"/>

<jsp:include page="/common/scripts.jsp"/>

</body>

</html>
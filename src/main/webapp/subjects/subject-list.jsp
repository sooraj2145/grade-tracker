<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>
    <title>Subjects</title>

    <jsp:include page="/common/imports.jsp"/>
</head>

<body>

<jsp:include page="/common/navbar.jsp"/>

<div class="container py-5">

    <jsp:include page="/common/messages.jsp"/>

    <!-- Page Header -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="mb-0">
            <i class="bi bi-journal-bookmark-fill me-2"></i>
            Subjects
        </h2>

        <a href="${pageContext.request.contextPath}/subjects?action=new"
           class="btn btn-success">
            <i class="bi bi-plus-circle"></i>
            Add Subject
        </a>
    </div>

    <!-- Search -->
    <div class="card shadow-sm mb-4">

        <div class="card-body">

            <form
                    method="get"
                    action="${pageContext.request.contextPath}/subjects"
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
                            placeholder="Search by subject code, name,...">
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
                            href="${pageContext.request.contextPath}/subjects"
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

    <!-- Subject Table -->
    <div class="card shadow-sm">

        <div class="card-body p-0">

            <table class="table table-striped table-hover align-middle mb-0">

                <thead class="table-dark">

                <c:set var="idNextDirection" value="asc"/>

                <c:if test="${sortBy == 'id' && direction == 'asc'}">
                    <c:set var="idNextDirection" value="desc" />
                </c:if>

                <c:set var="codeNextDirection" value="asc"/>
                <c:if test="${sortBy == 'code' && direction == 'asc'}">
                   <c:set var="codeNextDirection" value="desc" />
                </c:if>

                 <c:set var="nameNextDirection" value="asc"/>
                 <c:if test="${sortBy == 'name' && direction == 'asc'}">
                    <c:set var="nameNextDirection" value="desc" />
                 </c:if>

                  <c:set var="creditsNextDirection" value="asc"/>
                                 <c:if test="${sortBy == 'credits' && direction == 'asc'}">
                                    <c:set var="creditsNextDirection" value="desc" />
                                 </c:if>

                   <c:set var="semesterNextDirection" value="asc"/>
                                  <c:if test="${sortBy == 'semester' && direction == 'asc'}">
                                     <c:set var="semesterNextDirection" value="desc" />
                                  </c:if>

                <tr>
                    <c:url var="idSortUrl" value="/subjects">
                        <c:param name="page" value="1"/>
                        <c:param name="keyword" value="${keyword}"/>
                        <c:param name="sortBy" value="id"/>
                        <c:param name="direction" value="${idNextDirection}"/>
                    </c:url>
                    <th class="text-center">
                        <a
                            href="${idSortUrl}"
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
                            href="${pageContext.request.contextPath}/subjects?page=1&keyword=${keyword}&sortBy=code&direction=${codeNextDirection}"
                            class="text-decoration-none text-white">
                            Code
                            <c:if test="${sortBy == 'code'}">
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
                             href="${pageContext.request.contextPath}/subjects?page=1&keyword=${keyword}&sortBy=name&direction=${nameNextDirection}"
                               class="text-decoration-none text-white">
                                Name
                                  <c:if test="${sortBy == 'name'}">
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
                           href="${pageContext.request.contextPath}/subjects?page=1&keyword=${keyword}&sortBy=credits&direction=${creditsNextDirection}"
                           class="text-decoration-none text-white">
                           Credits
                          <c:if test="${sortBy == 'credits'}">
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
                             href="${pageContext.request.contextPath}/subjects?page=1&keyword=${keyword}&sortBy=semester&direction=${semesterNextDirection}"
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

                    <c:when test="${not empty subjects}">

                        <c:forEach items="${subjects}" var="subject">

                            <tr>

                                <td class="text-center">
                                    ${subject.id}
                                </td>

                                <td>
                                    ${subject.code}
                                </td>

                                <td>
                                    ${subject.name}
                                </td>

                                <td>
                                    ${subject.credits}
                                </td>

                                <td class="text-center">
                                    ${subject.semester}
                                </td>

                                <td>

                                    <div class="d-flex justify-content-center gap-2">

                                        <a
                                                href="${pageContext.request.contextPath}/subjects?action=edit&id=${subject.id}&keyword=${keyword}&page=${currentPage}&sortBy=${sortBy}&direction=${direction}"
                                                class="btn btn-warning btn-sm">

                                            <i class="bi bi-pencil-square"></i>
                                            Edit

                                        </a>

                                        <a
                                                href="${pageContext.request.contextPath}/subjects?action=delete&id=${subject.id}&keyword=${keyword}&page=${currentPage}&sortBy=${sortBy}&direction=${direction}"
                                                class="btn btn-danger btn-sm"
                                                onclick="return confirm('Are you sure you want to delete this subject?')">

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
                                                No subjects found matching
                                                "<strong>${keyword}</strong>"
                                            </h5>

                                            <p class="text-muted mb-0">
                                                Try searching with a different keyword.
                                            </p>

                                        </c:when>

                                        <c:otherwise>

                                            <h5>
                                                No subjects found.
                                            </h5>

                                            <p class="text-muted mb-0">
                                                Click <strong>Add Subject</strong> to create your first subject.
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
                      <a class="page-link" href="${pageContext.request.contextPath}/subjects?page=${currentPage - 1}&keyword=${keyword}&sortBy=${sortBy}&direction=${direction}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                      </a>
                 </li>
                 <c:forEach begin="1"
                            end="${totalPages}"
                            var="page">

                  <li class="page-item ${page == currentPage ? 'active' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/subjects?page=${page}&keyword=${keyword}&sortBy=${sortBy}&direction=${direction}" aria-label="page link">
                            ${page}
                      </a>
                  </li>
                  </c:forEach>
                  <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                       <a class="page-link" href="${pageContext.request.contextPath}/subjects?page=${currentPage + 1}&keyword=${keyword}&sortBy=${sortBy}&direction=${direction}" aria-label="Next">
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
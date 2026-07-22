<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>

    <jsp:include page="/common/imports.jsp"/>

 </head>

<body class="bg-light">
   <div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-lg-5 col-md-7">
            <div class="card shadow">
                <div class="card-header bg-primary text-white text-center py-4">
                    <i class="bi bi-mortarboard-fill fs-1 d-block mb-2"></i>
                    <h3 class="mb-0">
                        Grade Tracker Login
                    </h3>

                </div>

                <div class="card-body p-4">

                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger d-flex align-items-center">
                            <i class="bi bi-exclamation-triangle-fill me-2"></i>
                            <div>${errorMessage}</div>
                        </div>
                    </c:if>

                    <form
                        action="${pageContext.request.contextPath}/login"
                        method="post"
                        autocomplete="off">
                        <div class="mb-3">
                            <label class="form-label">
                                Username
                            </label>

                            <div class="input-group">
                                <span class="input-group-text bg-white">
                                    <i class="bi bi-person-fill"></i>
                                </span>
                                <input
                                       type="text"
                                       name="username"
                                       class="form-control"
                                       value="${username}"
                                       placeholder="Enter your username"
                                       required>
                            </div>
                        </div>

                        <div class="mb-4">

                             <label class="form-label">
                                 Password
                             </label>

                             <div class="input-group">
                                 <span class="input-group-text bg-white">
                                     <i class="bi bi-lock-fill"></i>
                                 </span>
                                 <input
                                         type="password"
                                         name="password"
                                         class="form-control"
                                         placeholder="Enter your password"
                                         required>
                             </div>

                         </div>

                         <button
                                 class="btn btn-primary w-100 py-2"
                                 type="submit">

                             <i class="bi bi-box-arrow-in-right me-2"></i>
                             Login

                         </button>

                     </form>

                 </div>

             </div>

         </div>

     </div>

 </div>

 <jsp:include page="/common/scripts.jsp"/>

 </body>

 </html>
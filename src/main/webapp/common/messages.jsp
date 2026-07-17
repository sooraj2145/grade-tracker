<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:if test="${successMessage != null && successMessage.trim().length() > 0}">

    <div class="alert alert-success alert-dismissible fade show" role="alert">

        ${successMessage}

        <button
                type="button"
                class="btn-close"
                data-bs-dismiss="alert"
                aria-label="Close">
        </button>

    </div>

</c:if>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger">
        ${errorMessage}
    </div>
</c:if>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Error - Advertisement Not Found" cssFile="advertisements/errorAdvDetail">
    <div class="error-container">
        <h1>Oops!</h1>
        <p><strong>Error:</strong> ${errorMessage}</p>
        <a href="/advertisements" class="back-to-advertisement">Go back to the advertisement page</a>
    </div>
</t:mainLayout>

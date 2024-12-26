<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Error - News Not Found" cssFile="news/errorNewsDetail">
    <div class="error-container">
        <h1>Oops!</h1>
        <p><strong>Error:</strong> ${errorMessage}</p>
        <a href="/" class="back-to-news">Go back to the news page</a>
    </div>
</t:mainLayout>

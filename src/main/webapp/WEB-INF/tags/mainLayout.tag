<%@ tag description="Default Layout Tag" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name="title" %>
<%@ attribute name="cssFile" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>${title}</title>

    <c:forEach var="file" items="${fn:split(cssFile, ',')}">
      <link rel="stylesheet" href="<c:url value='/static/css/${file}.css'/>">
    </c:forEach>

    <link rel="stylesheet" href="<c:url value='/static/css/main.css'/>">
</head>
<body>
    <%@ include file="/WEB-INF/parts/_header.jsp" %>

    <div class="container">
        <jsp:doBody/>
    </div>

    <%@ include file="/WEB-INF/parts/_footer.jsp" %>
</body>
</html>
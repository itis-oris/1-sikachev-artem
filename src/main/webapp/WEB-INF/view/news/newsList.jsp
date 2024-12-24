<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="News" cssFile="news">
  <h1 class="header">Важные объявления</h1>
  <div class="news-container">
    <c:forEach var="news" items="${newsList}">
      <div class="news-card">
        <h3 class="news-name">
          <a href="news?id=${news.id}">${news.title}</a>
        </h3>
        <p><strong>Published on: </strong>${news.publishDate}</p>
        <p><img class="news-image" src="images/${news.imageUrl}" alt="Image" /></p>
        <p>${news.content}</p>
      </div>
    </c:forEach>
  </div>

  <div class="pagination">
    <c:forEach var="i" begin="1" end="${totalPages}">
      <c:choose>
        <c:when test="${i == currentPage}">
          <span class="current-page">${i}</span>
        </c:when>
        <c:otherwise>
          <a href="?page=${i}" class="page-link">${i}</a>
        </c:otherwise>
      </c:choose>
    </c:forEach>
  </div>
</t:mainLayout>
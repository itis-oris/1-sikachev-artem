<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Advertisements" cssFile="advertisements/advertisements">
  <h1 class="header">Advertisements</h1>

  <c:if test="${not empty user}">
    <div class="add-advertisement">
      <a href="/advertisements/create" class="btn-add-advertisement">Добавить новое объявление</a>
    </div>
  </c:if>

  <div class="advertisement-container">
    <c:forEach var="advertisement" items="${advertisementsList}">
      <div class="advertisement-card">
        <h3 class="advertisement-name">
          <a href="/advertisements/detail?id=${advertisement.id}">${advertisement.title}</a>
        </h3>
        <p><strong>Published on: </strong>${advertisement.publishDate}</p>
        <p><img class="advertisement-image" src="/images/${advertisement.imageUrl}" alt="Image" /></p>
        <p>${advertisement.content}</p>
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
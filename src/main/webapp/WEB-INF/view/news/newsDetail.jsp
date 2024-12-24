<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="${news.title}" cssFile="newsDetail">
    <div class="news-detail-container">
        <h1 class="news-title">${news.title}</h1>
        <p class="news-publish-date"><strong>Published on: </strong>${news.publishDate}</p>

        <div class="news-image-container">
            <img src="${news.imageUrl}" alt="News Image" class="news-detail-image" />
        </div>

        <div class="news-content">
            <p>${news.content}</p>
        </div>
    </div>
</t:mainLayout>
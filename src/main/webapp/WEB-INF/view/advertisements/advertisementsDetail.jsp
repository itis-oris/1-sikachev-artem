<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="${advertisements.title}" cssFile="advertisements/advertisementsDetail">
    <div class="advertisement-detail-container">
        <h1 class="advertisement-title">${advertisement.title}</h1>
        <p class="advertisements-publish-date"><strong>Published on: </strong>${advertisement.publishDate}</p>

        <div class="advertisement-image-container">
            <img src="/images/${advertisement.imageUrl}" alt="advertisement Image" class="advertisement-detail-image" />
        </div>

        <div class="advertisement-content">
            <p>${advertisement.content}</p>
        </div>
        <div class="advertisement-sender_id">
            <a>${advertisement.sender_id}</p>
        </div>

        <c:if test="${not empty user}">
            <div class="message-button">
                <a href="/messenger/create?advId=${advertisement.id}&userId=${advertisement.sender_id}" class="btn-message">Написать человеку</a>
            </div>
        </c:if>
    </div>
</t:mainLayout>
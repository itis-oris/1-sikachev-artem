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

        <form method="post" action="/advertisements/check/detail">
            <input type="hidden" name="id" value="${advertisement.id}" />
            <button type="submit" name="action" value="accept" class="btn-accept">Принять</button>
            <button type="submit" name="action" value="reject" class="btn-reject">Отклонить</button>
        </form>
    </div>
</t:mainLayout>
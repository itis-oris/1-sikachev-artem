<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Авторизация" cssFile="user/login, user/user, user/errorUser">
    <div class="card">
        <h1>Авторизация</h1>
        <form method="POST" action="${pageContext.request.contextPath}/login">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br>

            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required><br>

            <button type="submit">Войти</button>
        </form>

        <div>
            Ещё не создали аккаунт? <a class="reg-link" href="/register">Зарегистрироваться</a>
        </div>

        <c:if test="${not empty error}">
            <div id="errorMessage" class="error-message">${error}</div>
        </c:if>
    </div>

    <script>
        <c:if test="${not empty error}">
            document.addEventListener("DOMContentLoaded", function() {
                var errorMessage = document.getElementById("errorMessage");
                if (errorMessage) {
                    errorMessage.style.display = 'block';
                    setTimeout(function() {
                        errorMessage.style.display = 'none';
                    }, 10000);
                }
            });
        </c:if>
    </script>
</t:mainLayout>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Регистрация" cssFile="user/register, user/user, user/errorUser">
    <div class="card">
        <h1>Регистрация</h1>
        <form method="POST" action="${pageContext.request.contextPath}/register">
            <label for="userName">Имя пользователя:</label>
            <input type="text" id="userName" name="userName" required><br>

            <label for="firstName">Имя:</label>
            <input type="text" id="firstName" name="firstName" required><br>

            <label for="lastName">Фамилия:</label>
            <input type="text" id="lastName" name="lastName" required><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br>

            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required><br>

            <label for="check_password">Повторите пароль:</label>
            <input type="password" id="check_password" name="check_password" required><br>

            <label for="phoneNumber">Телефон:</label>
            <input type="text" id="phoneNumber" name="phoneNumber"><br>

            <label for="placeOfLiving">Место жительства:</label>
            <input type="text" id="placeOfLiving" name="placeOfLiving"><br>

            <button type="submit">Зарегистрироваться</button>
        </form>

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

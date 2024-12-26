<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Редактирование профиля" cssFile="user/profile, user/user, user/errorUser">
    <div class="card">
        <h1>Редактирование профиля</h1>
        <form id="profileForm" method="POST" action="${pageContext.request.contextPath}/profile">
            <label for="userName">Имя пользователя:</label>
            <input type="text" id="userName" name="userName" value="${user.userName}" required><br>

            <label for="firstName">Имя:</label>
            <input type="text" id="firstName" name="firstName" value="${user.firstName}" required><br>

            <label for="lastName">Фамилия:</label>
            <input type="text" id="lastName" name="lastName" value="${user.lastName}" required><br>

            <label for="phone">Номер Телефона:</label>
            <input type="text" id="phone" name="phone" value="${user.phone_number}" required><br>

            <label for="phone">Место проживания:</label>
            <input type="text" id="place" name="place" value="${user.place_of_living}" required><br>

            <button type="submit">Обновить</button>
            <button id="hideBut" type="submit">Скрыть</button>
        </form>

        <button id="editButton">Изменить данные</button>

        <form id="logoutForm" action="${pageContext.request.contextPath}/logout" method="POST">
            <button type="submit">Выйти</button>
        </form>

        <div class="error-message" id="profileError" style="display:none;"></div>
    </div>

    <script>
        document.getElementById('editButton').addEventListener('click', function () {
            document.getElementById('profileForm').style.display = 'block';
            this.style.display = 'none';
        });

        document.getElementById('hideButton').addEventListener('click', function () {
            document.getElementById('profileForm').style.display = 'none';
            document.getElementById('editButton').style.display = 'block';
        });

        function showError(message) {
            var errorDiv = document.getElementById('profileError');
            errorDiv.textContent = message;
            errorDiv.style.display = 'block';
            setTimeout(function () {
                errorDiv.style.display = 'none';
            }, 10000);
        }
    </script>
</t:mainLayout>

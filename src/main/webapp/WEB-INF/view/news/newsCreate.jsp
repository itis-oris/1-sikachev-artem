<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:mainLayout title="Create News" cssFile="createNewObject">
  <h1 class="header">Create New Post</h1>

  <form method="post" enctype="multipart/form-data">
    <div>
      <label for="title">Title:</label>
      <input type="text" id="title" name="title" required />
    </div>

    <div>
      <label for="content">Content:</label>
      <textarea id="content" name="content" required></textarea>
    </div>

    <div>
      <label for="image">Image:</label>
      <input type="file" id="image" name="image" accept="image/*" />
    </div>

    <button type="submit">Create News</button>
  </form>
</t:mainLayout>

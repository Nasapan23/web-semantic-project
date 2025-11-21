<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Users</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Users</h1>
            <a href="${pageContext.request.contextPath}/" class="btn">Home</a>
            <a href="${pageContext.request.contextPath}/add-user" class="btn">Add User</a>
        </header>

        <c:if test="${param.success == 'true'}">
            <div class="success-message">User added successfully!</div>
        </c:if>

        <div class="users-list">
            <c:forEach var="user" items="${users}">
                <div class="user-card">
                    <h3>${user.name} ${user.surname}</h3>
                    <p><strong>ID:</strong> ${user.id}</p>
                    <p><strong>Cooking Skill Level:</strong> ${user.cookingSkillLevel}</p>
                    <p><strong>Preferred Cuisine:</strong> ${user.preferredCuisineType}</p>
                    <div class="user-actions">
                        <a href="${pageContext.request.contextPath}/recommendations?userId=${user.id}" class="btn">Get Recommendations</a>
                        <a href="${pageContext.request.contextPath}/recommendations?userId=${user.id}&type=skillAndCuisine" class="btn">Recommendations (Skill & Cuisine)</a>
                    </div>
                </div>
            </c:forEach>
        </div>

        <c:if test="${empty users}">
            <div class="empty-state">
                <p>No users found. <a href="${pageContext.request.contextPath}/add-user">Add a user</a> to get started.</p>
            </div>
        </c:if>
    </div>
</body>
</html>



<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add User</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Add New User</h1>
            <a href="${pageContext.request.contextPath}/" class="btn">Home</a>
        </header>

        <c:if test="${not empty errors}">
            <div class="error-message">
                <ul>
                    <c:forEach var="error" items="${errors}">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/add-user" method="post" class="recipe-form">
            <div class="form-group">
                <label for="name">Name *</label>
                <input type="text" id="name" name="name" value="${name}" required>
            </div>

            <div class="form-group">
                <label for="surname">Surname *</label>
                <input type="text" id="surname" name="surname" value="${surname}" required>
            </div>

            <div class="form-group">
                <label for="cookingSkillLevel">Cooking Skill Level *</label>
                <select id="cookingSkillLevel" name="cookingSkillLevel" required>
                    <option value="">Select Skill Level...</option>
                    <option value="Beginner" ${cookingSkillLevel == 'Beginner' ? 'selected' : ''}>Beginner</option>
                    <option value="Intermediate" ${cookingSkillLevel == 'Intermediate' ? 'selected' : ''}>Intermediate</option>
                    <option value="Advanced" ${cookingSkillLevel == 'Advanced' ? 'selected' : ''}>Advanced</option>
                </select>
            </div>

            <div class="form-group">
                <label for="preferredCuisineType">Preferred Cuisine Type *</label>
                <select id="preferredCuisineType" name="preferredCuisineType" required>
                    <option value="">Select Cuisine...</option>
                    <option value="Italian" ${preferredCuisineType == 'Italian' ? 'selected' : ''}>Italian</option>
                    <option value="Asian" ${preferredCuisineType == 'Asian' ? 'selected' : ''}>Asian</option>
                    <option value="Indian" ${preferredCuisineType == 'Indian' ? 'selected' : ''}>Indian</option>
                    <option value="Mexican" ${preferredCuisineType == 'Mexican' ? 'selected' : ''}>Mexican</option>
                    <option value="French" ${preferredCuisineType == 'French' ? 'selected' : ''}>French</option>
                    <option value="Japanese" ${preferredCuisineType == 'Japanese' ? 'selected' : ''}>Japanese</option>
                    <option value="American" ${preferredCuisineType == 'American' ? 'selected' : ''}>American</option>
                    <option value="Mediterranean" ${preferredCuisineType == 'Mediterranean' ? 'selected' : ''}>Mediterranean</option>
                    <option value="European" ${preferredCuisineType == 'European' ? 'selected' : ''}>European</option>
                    <option value="Thai" ${preferredCuisineType == 'Thai' ? 'selected' : ''}>Thai</option>
                    <option value="Korean" ${preferredCuisineType == 'Korean' ? 'selected' : ''}>Korean</option>
                    <option value="British" ${preferredCuisineType == 'British' ? 'selected' : ''}>British</option>
                    <option value="Greek" ${preferredCuisineType == 'Greek' ? 'selected' : ''}>Greek</option>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Add User</button>
                <a href="${pageContext.request.contextPath}/users" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>



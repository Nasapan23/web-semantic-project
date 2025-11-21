<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recipe Recommendations</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Recipe Recommendations</h1>
            <a href="${pageContext.request.contextPath}/" class="btn">Home</a>
            <a href="${pageContext.request.contextPath}/users" class="btn">Select User</a>
        </header>

        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <c:if test="${not empty user}">
            <div class="user-info">
                <h2>User: ${user.name} ${user.surname}</h2>
                <p><strong>Cooking Skill Level:</strong> ${user.cookingSkillLevel}</p>
                <p><strong>Preferred Cuisine:</strong> ${user.preferredCuisineType}</p>
                <p><strong>Recommendation Type:</strong> ${recommendationType}</p>
            </div>

            <div class="recommendations-actions">
                <a href="${pageContext.request.contextPath}/recommendations?userId=${user.id}" class="btn">By Skill Level Only</a>
                <a href="${pageContext.request.contextPath}/recommendations?userId=${user.id}&type=skillAndCuisine" class="btn">By Skill & Cuisine</a>
            </div>

            <c:if test="${not empty recommendations}">
                <h3>Recommended Recipes (${recommendations.size()})</h3>
                <div class="recipes-grid">
                    <c:forEach var="recipe" items="${recommendations}">
                        <div class="recipe-card 
                            <c:if test="${recipe.difficultyLevel == user.cookingSkillLevel}">recipe-yellow</c:if>
                            <c:if test="${recipe.difficultyLevel != user.cookingSkillLevel}">recipe-green</c:if>
                        ">
                            <h3>${recipe.title}</h3>
                            <p><strong>Difficulty:</strong> ${recipe.difficultyLevel}</p>
                            <p><strong>Cuisine Types:</strong> 
                                <c:forEach var="cuisine" items="${recipe.cuisineTypes}" varStatus="status">
                                    ${cuisine}<c:if test="${!status.last}">, </c:if>
                                </c:forEach>
                            </p>
                            <a href="${pageContext.request.contextPath}/recipe-details?id=${recipe.id}" class="btn">View Details</a>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty recommendations}">
                <div class="empty-state">
                    <p>No recommendations found for this user.</p>
                </div>
            </c:if>
        </c:if>
    </div>
</body>
</html>


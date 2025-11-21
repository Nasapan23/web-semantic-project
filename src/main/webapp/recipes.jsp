<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recipes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>All Recipes</h1>
            <a href="${pageContext.request.contextPath}/" class="btn">Home</a>
            <a href="${pageContext.request.contextPath}/add-recipe" class="btn">Add Recipe</a>
        </header>

        <c:if test="${not empty filteredCuisine}">
            <div class="info-box">
                <p>Filtered by cuisine: <strong>${filteredCuisine}</strong></p>
                <a href="${pageContext.request.contextPath}/recipes">Clear Filter</a>
            </div>
        </c:if>

        <c:if test="${not empty searchQuery}">
            <div class="info-box">
                <p>Search results for: <strong>"${searchQuery}"</strong></p>
                <p>Found ${recipes.size()} recipe(s)</p>
                <a href="${pageContext.request.contextPath}/recipes">View All Recipes</a>
            </div>
        </c:if>

        <c:if test="${param.success == 'true'}">
            <div class="success-message">Recipe added successfully!</div>
        </c:if>

        <div class="recipes-grid">
            <c:forEach var="recipe" items="${recipes}">
                <div class="recipe-card">
                    <h3>${recipe.title}</h3>
                    <c:if test="${not empty recipe.description}">
                        <p class="recipe-description">${recipe.description}</p>
                    </c:if>
                    <p><strong>Difficulty:</strong> ${recipe.difficultyLevel}</p>
                    <c:if test="${not empty recipe.cookingTime}">
                        <p><strong>Time:</strong> ${recipe.cookingTime}</p>
                    </c:if>
                    <c:if test="${not empty recipe.servings}">
                        <p><strong>Servings:</strong> ${recipe.servings}</p>
                    </c:if>
                    <p><strong>Cuisine Types:</strong> 
                        <c:forEach var="cuisine" items="${recipe.cuisineTypes}" varStatus="status">
                            ${cuisine}<c:if test="${!status.last}">, </c:if>
                        </c:forEach>
                    </p>
                    <a href="${pageContext.request.contextPath}/recipe-details?id=${recipe.id}" class="btn">View Details</a>
                </div>
            </c:forEach>
        </div>

        <c:if test="${empty recipes}">
            <div class="empty-state">
                <p>No recipes found.</p>
            </div>
        </c:if>
    </div>
</body>
</html>



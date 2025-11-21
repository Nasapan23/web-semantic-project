<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recipe Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Recipe Details</h1>
            <a href="${pageContext.request.contextPath}/recipes" class="btn">Back to Recipes</a>
        </header>

        <c:if test="${not empty recipe}">
            <div class="recipe-details">
                <h2>${recipe.title}</h2>
                
                <c:if test="${not empty recipe.description}">
                    <div class="detail-section">
                        <p class="description">${recipe.description}</p>
                    </div>
                </c:if>
                
                <div class="detail-section">
                    <h3>Difficulty Level</h3>
                    <p class="difficulty-badge difficulty-${recipe.difficultyLevel.toLowerCase()}">${recipe.difficultyLevel}</p>
                </div>
                
                <c:if test="${not empty recipe.cookingTime}">
                    <div class="detail-section">
                        <h3>Cooking Time</h3>
                        <p>${recipe.cookingTime}</p>
                    </div>
                </c:if>
                
                <c:if test="${not empty recipe.servings}">
                    <div class="detail-section">
                        <h3>Servings</h3>
                        <p>${recipe.servings}</p>
                    </div>
                </c:if>
                
                <div class="detail-section">
                    <h3>Cuisine Types</h3>
                    <ul class="cuisine-list">
                        <c:forEach var="cuisine" items="${recipe.cuisineTypes}">
                            <li>${cuisine}</li>
                        </c:forEach>
                    </ul>
                </div>
                
                <c:if test="${not empty recipe.ingredients}">
                    <div class="detail-section">
                        <h3>Ingredients</h3>
                        <ul class="ingredients-list">
                            <c:forEach var="ingredient" items="${recipe.ingredients}">
                                <li>${ingredient}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
                
                <c:if test="${not empty recipe.instructions}">
                    <div class="detail-section">
                        <h3>Instructions</h3>
                        <ol class="instructions-list">
                            <c:forEach var="instruction" items="${recipe.instructions}">
                                <li>${instruction}</li>
                            </c:forEach>
                        </ol>
                    </div>
                </c:if>
            </div>
        </c:if>

        <c:if test="${empty recipe}">
            <div class="error-message">Recipe not found.</div>
        </c:if>
    </div>
</body>
</html>



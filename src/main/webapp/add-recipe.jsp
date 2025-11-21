<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Recipe</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Add New Recipe</h1>
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

        <form action="${pageContext.request.contextPath}/add-recipe" method="post" class="recipe-form">
            <div class="form-group">
                <label for="title">Recipe Title *</label>
                <input type="text" id="title" name="title" value="${title}" required>
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" rows="3" placeholder="Brief description of the recipe...">${description}</textarea>
            </div>

            <div class="form-group">
                <label for="cuisine1">Cuisine Type 1 *</label>
                <select id="cuisine1" name="cuisine1" required>
                    <option value="">Select Cuisine...</option>
                    <option value="Italian" ${cuisine1 == 'Italian' ? 'selected' : ''}>Italian</option>
                    <option value="Asian" ${cuisine1 == 'Asian' ? 'selected' : ''}>Asian</option>
                    <option value="Indian" ${cuisine1 == 'Indian' ? 'selected' : ''}>Indian</option>
                    <option value="Mexican" ${cuisine1 == 'Mexican' ? 'selected' : ''}>Mexican</option>
                    <option value="French" ${cuisine1 == 'French' ? 'selected' : ''}>French</option>
                    <option value="Japanese" ${cuisine1 == 'Japanese' ? 'selected' : ''}>Japanese</option>
                    <option value="American" ${cuisine1 == 'American' ? 'selected' : ''}>American</option>
                    <option value="Mediterranean" ${cuisine1 == 'Mediterranean' ? 'selected' : ''}>Mediterranean</option>
                    <option value="European" ${cuisine1 == 'European' ? 'selected' : ''}>European</option>
                    <option value="Thai" ${cuisine1 == 'Thai' ? 'selected' : ''}>Thai</option>
                    <option value="Korean" ${cuisine1 == 'Korean' ? 'selected' : ''}>Korean</option>
                    <option value="British" ${cuisine1 == 'British' ? 'selected' : ''}>British</option>
                    <option value="Greek" ${cuisine1 == 'Greek' ? 'selected' : ''}>Greek</option>
                    <option value="Russian" ${cuisine1 == 'Russian' ? 'selected' : ''}>Russian</option>
                    <option value="Dessert" ${cuisine1 == 'Dessert' ? 'selected' : ''}>Dessert</option>
                    <option value="Breakfast" ${cuisine1 == 'Breakfast' ? 'selected' : ''}>Breakfast</option>
                </select>
            </div>

            <div class="form-group">
                <label for="cuisine2">Cuisine Type 2 (Optional)</label>
                <select id="cuisine2" name="cuisine2">
                    <option value="">Select Cuisine...</option>
                    <option value="Italian" ${cuisine2 == 'Italian' ? 'selected' : ''}>Italian</option>
                    <option value="Asian" ${cuisine2 == 'Asian' ? 'selected' : ''}>Asian</option>
                    <option value="Indian" ${cuisine2 == 'Indian' ? 'selected' : ''}>Indian</option>
                    <option value="Mexican" ${cuisine2 == 'Mexican' ? 'selected' : ''}>Mexican</option>
                    <option value="French" ${cuisine2 == 'French' ? 'selected' : ''}>French</option>
                    <option value="Japanese" ${cuisine2 == 'Japanese' ? 'selected' : ''}>Japanese</option>
                    <option value="American" ${cuisine2 == 'American' ? 'selected' : ''}>American</option>
                    <option value="Mediterranean" ${cuisine2 == 'Mediterranean' ? 'selected' : ''}>Mediterranean</option>
                    <option value="European" ${cuisine2 == 'European' ? 'selected' : ''}>European</option>
                    <option value="Thai" ${cuisine2 == 'Thai' ? 'selected' : ''}>Thai</option>
                    <option value="Korean" ${cuisine2 == 'Korean' ? 'selected' : ''}>Korean</option>
                    <option value="British" ${cuisine2 == 'British' ? 'selected' : ''}>British</option>
                    <option value="Greek" ${cuisine2 == 'Greek' ? 'selected' : ''}>Greek</option>
                    <option value="Russian" ${cuisine2 == 'Russian' ? 'selected' : ''}>Russian</option>
                    <option value="Dessert" ${cuisine2 == 'Dessert' ? 'selected' : ''}>Dessert</option>
                    <option value="Breakfast" ${cuisine2 == 'Breakfast' ? 'selected' : ''}>Breakfast</option>
                </select>
            </div>

            <div class="form-group">
                <label for="difficultyLevel">Difficulty Level *</label>
                <select id="difficultyLevel" name="difficultyLevel" required>
                    <option value="">Select Difficulty...</option>
                    <option value="Beginner" ${difficultyLevel == 'Beginner' ? 'selected' : ''}>Beginner</option>
                    <option value="Intermediate" ${difficultyLevel == 'Intermediate' ? 'selected' : ''}>Intermediate</option>
                    <option value="Advanced" ${difficultyLevel == 'Advanced' ? 'selected' : ''}>Advanced</option>
                </select>
            </div>

            <div class="form-group">
                <label for="cookingTime">Cooking Time</label>
                <input type="text" id="cookingTime" name="cookingTime" value="${cookingTime}" placeholder="e.g., 30 minutes">
            </div>

            <div class="form-group">
                <label for="servings">Servings</label>
                <input type="text" id="servings" name="servings" value="${servings}" placeholder="e.g., 4">
            </div>

            <div class="form-group">
                <label for="ingredients">Ingredients * (one per line)</label>
                <textarea id="ingredients" name="ingredients" rows="6" required placeholder="Enter ingredients, one per line...">${ingredients}</textarea>
            </div>

            <div class="form-group">
                <label for="instructions">Instructions * (one per line)</label>
                <textarea id="instructions" name="instructions" rows="8" required placeholder="Enter cooking steps, one per line...">${instructions}</textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Add Recipe</button>
                <a href="${pageContext.request.contextPath}/recipes" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>



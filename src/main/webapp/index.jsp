<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recipe Recommender - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Recipe Recommender</h1>
            <p class="subtitle">Find the perfect recipe based on your cooking skills and preferences</p>
        </header>

        <section class="search-section">
            <form action="${pageContext.request.contextPath}/search" method="get" class="search-form">
                <input type="text" name="q" placeholder="Search recipes by name, description, or ingredients..." 
                       class="search-input" autocomplete="off">
                <button type="submit" class="btn btn-search">Search</button>
            </form>
        </section>

        <nav class="main-nav">
            <a href="${pageContext.request.contextPath}/recipes" class="nav-link">View All Recipes</a>
            <a href="${pageContext.request.contextPath}/recipes-xsl" class="nav-link">Recipes (XSL View)</a>
            <a href="${pageContext.request.contextPath}/add-recipe" class="nav-link">Add Recipe</a>
            <a href="${pageContext.request.contextPath}/add-user" class="nav-link">Add User</a>
            <a href="${pageContext.request.contextPath}/users" class="nav-link">View Users</a>
            <a href="${pageContext.request.contextPath}/recommendations" class="nav-link">Get Recommendations</a>
        </nav>

        <section class="features">
            <div class="feature-card">
                <h3>Browse Recipes</h3>
                <p>View all available recipes in our collection</p>
                <a href="${pageContext.request.contextPath}/recipes" class="btn">View Recipes</a>
            </div>

            <div class="feature-card">
                <h3>Add Recipe</h3>
                <p>Contribute your favorite recipe to our collection</p>
                <a href="${pageContext.request.contextPath}/add-recipe" class="btn">Add Recipe</a>
            </div>

            <div class="feature-card">
                <h3>Get Recommendations</h3>
                <p>Find recipes that match your cooking skill level and preferences</p>
                <a href="${pageContext.request.contextPath}/recommendations" class="btn">Get Recommendations</a>
            </div>

            <div class="feature-card">
                <h3>Filter by Cuisine</h3>
                <p>Browse recipes by your favorite cuisine type</p>
                <form action="${pageContext.request.contextPath}/filter-cuisine" method="get" class="filter-form">
                    <select name="cuisine" required>
                        <option value="">Select Cuisine...</option>
                        <option value="Italian">Italian</option>
                        <option value="Asian">Asian</option>
                        <option value="Indian">Indian</option>
                        <option value="Mexican">Mexican</option>
                        <option value="French">French</option>
                        <option value="Japanese">Japanese</option>
                        <option value="American">American</option>
                        <option value="Mediterranean">Mediterranean</option>
                        <option value="European">European</option>
                        <option value="Thai">Thai</option>
                        <option value="Korean">Korean</option>
                        <option value="British">British</option>
                        <option value="Greek">Greek</option>
                    </select>
                    <button type="submit" class="btn">Filter</button>
                </form>
            </div>
        </section>
    </div>
</body>
</html>



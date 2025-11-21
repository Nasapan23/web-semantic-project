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
            <form action="${pageContext.request.contextPath}/search" method="get" class="search-form" id="searchForm">
                <div class="search-input-wrapper">
                    <input type="text" name="q" id="searchInput" 
                           placeholder="Search recipes by name, description, or ingredients..." 
                           class="search-input" autocomplete="off">
                    <span class="search-icon">🔍</span>
                    <div class="search-loading" id="searchLoading" style="display: none;">⏳</div>
                </div>
                <button type="submit" class="btn btn-search">Search</button>
            </form>
            <div id="searchResults" class="search-results-container"></div>
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
    
    <script>
        (function() {
            const searchInput = document.getElementById('searchInput');
            const searchResults = document.getElementById('searchResults');
            const searchLoading = document.getElementById('searchLoading');
            const searchForm = document.getElementById('searchForm');
            let debounceTimer;
            
            // Debounce function
            function debounce(func, wait) {
                return function executedFunction(...args) {
                    const later = () => {
                        clearTimeout(debounceTimer);
                        func(...args);
                    };
                    clearTimeout(debounceTimer);
                    debounceTimer = setTimeout(later, wait);
                };
            }
            
            // Perform live search
            function performSearch(query) {
                if (!query || query.trim().length === 0) {
                    searchResults.innerHTML = '';
                    searchResults.style.display = 'none';
                    return;
                }
                
                if (query.trim().length < 2) {
                    searchResults.innerHTML = '<div class="search-message">Type at least 2 characters to search</div>';
                    searchResults.style.display = 'block';
                    return;
                }
                
                searchLoading.style.display = 'inline-block';
                searchResults.style.display = 'block';
                searchResults.innerHTML = '<div class="search-message">Searching...</div>';
                
                // Fetch search results
                fetch('${pageContext.request.contextPath}/search?q=' + encodeURIComponent(query))
                    .then(response => response.text())
                    .then(html => {
                        // Parse the HTML response to extract recipe cards
                        const parser = new DOMParser();
                        const doc = parser.parseFromString(html, 'text/html');
                        const recipesGrid = doc.querySelector('.recipes-grid');
                        const infoBox = doc.querySelector('.info-box');
                        const emptyState = doc.querySelector('.empty-state');
                        
                        searchLoading.style.display = 'none';
                        
                        if (recipesGrid && recipesGrid.children.length > 0) {
                            let resultsHTML = '';
                            if (infoBox) {
                                resultsHTML += infoBox.outerHTML;
                            }
                            resultsHTML += recipesGrid.outerHTML;
                            searchResults.innerHTML = resultsHTML;
                        } else if (emptyState) {
                            searchResults.innerHTML = emptyState.outerHTML;
                        } else {
                            searchResults.innerHTML = '<div class="search-message">No recipes found</div>';
                        }
                    })
                    .catch(error => {
                        console.error('Search error:', error);
                        searchLoading.style.display = 'none';
                        searchResults.innerHTML = '<div class="search-message error">Error performing search. Please try again.</div>';
                    });
            }
            
            // Debounced search function (300ms delay)
            const debouncedSearch = debounce(performSearch, 300);
            
            // Listen for input changes
            searchInput.addEventListener('input', function(e) {
                const query = e.target.value.trim();
                debouncedSearch(query);
            });
            
            // Handle form submission (prevent default for live search)
            searchForm.addEventListener('submit', function(e) {
                const query = searchInput.value.trim();
                if (query.length >= 2) {
                    // Allow form submission for full page navigation
                    return true;
                }
                e.preventDefault();
                return false;
            });
            
            // Clear results when input is empty
            searchInput.addEventListener('focus', function() {
                if (this.value.trim().length >= 2) {
                    performSearch(this.value.trim());
                }
            });
            
            // Hide results when clicking outside
            document.addEventListener('click', function(e) {
                if (!searchForm.contains(e.target) && !searchResults.contains(e.target)) {
                    searchResults.style.display = 'none';
                }
            });
        })();
    </script>
</body>
</html>



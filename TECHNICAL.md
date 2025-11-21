# Technical Implementation Guide

This document provides a brief overview of the technical implementation for presentation purposes.

## Architecture Overview

The application follows a **Model-View-Controller (MVC)** pattern:
- **Model**: `Recipe` and `User` Java classes
- **View**: JSP pages with JSTL
- **Controller**: Java Servlets

## Core Technologies

### 1. XML Data Storage
- **Location**: `src/main/resources/data/recipes.xml`
- **Schema Validation**: Both DTD (`recipes.dtd`) and XSD (`recipes.xsd`)
- **Structure**: Hierarchical XML with recipes and users
- **Features**: 22+ recipes with full details (ingredients, instructions, cooking time, servings)

### 2. XPath/XQuery Implementation
All data queries use XPath expressions through Java's XPath API:

**Key Queries:**
- Get all recipes: `//recipe`
- Filter by skill level: `/recipeData/recipes/recipe[difficultyLevel='Intermediate']`
- Filter by cuisine: `/recipeData/recipes/recipe[cuisineTypes/cuisine='Italian']`
- Combined filter: `/recipeData/recipes/recipe[difficultyLevel='...' and cuisineTypes/cuisine='...']`
- Search: Case-insensitive search using `translate()` function
- Get by ID: `/recipeData/recipes/recipe[@id='r1']`

**Implementation**: `XMLUtil.java` - Central utility class handling all XML operations

### 3. XSL Transformation
- **File**: `src/main/webapp/WEB-INF/xsl/recipes.xsl`
- **Purpose**: Transform XML data to HTML with styling
- **Features**:
  - Color-coded recipes (yellow for matching skill level, green for others)
  - Dynamic parameter passing (user skill level)
  - Server-side transformation via `XSLTransformServlet`

### 4. Servlet Architecture

**Servlets:**
- `RecipeListServlet` - Display all recipes
- `RecipeDetailsServlet` - Show individual recipe details
- `AddRecipeServlet` - Handle recipe creation with validation
- `AddUserServlet` - Handle user creation
- `UserListServlet` - Display all users
- `RecommendationsServlet` - Generate personalized recommendations
- `FilterCuisineServlet` - Filter recipes by cuisine type
- `SearchServlet` - Search recipes by title, description, or ingredients
- `XSLTransformServlet` - Apply XSL transformation to XML

**Configuration**: `web.xml` - Servlet mappings and configuration

### 5. Frontend Technologies

**JSP Pages:**
- `index.jsp` - Homepage with search and navigation
- `recipes.jsp` - Recipe listing with grid layout
- `recipe-details.jsp` - Detailed recipe view
- `add-recipe.jsp` - Recipe creation form
- `add-user.jsp` - User creation form
- `users.jsp` - User listing
- `recommendations.jsp` - Recommendation results

**JavaScript Features:**
- **Live Search**: Debounced search (300ms) with AJAX
- **Dynamic Results**: Real-time result updates without page reload
- **User Experience**: Loading indicators, smooth animations

**CSS:**
- Modern gradient design
- Responsive layout (mobile-friendly)
- Smooth transitions and hover effects
- Custom styling for recipe cards, forms, and buttons

### 6. Data Model

**Recipe Class:**
- `id`, `title`, `description`
- `cuisineTypes` (List)
- `difficultyLevel` (Beginner/Intermediate/Advanced)
- `cookingTime`, `servings`
- `ingredients` (List)
- `instructions` (List)

**User Class:**
- `id`, `name`, `surname`
- `cookingSkillLevel`
- `preferredCuisineType`

### 7. Key Features Implementation

#### Search Functionality
- **Method**: `XMLUtil.searchRecipes(String query)`
- **Technology**: XPath with `contains()` and `translate()` for case-insensitive search
- **Scope**: Searches in title, description, and ingredients
- **Frontend**: Debounced JavaScript with fetch API

#### Recommendations
- **Skill-based**: Filters recipes matching user's cooking skill level
- **Skill + Cuisine**: Combines skill level and preferred cuisine type
- **Implementation**: XPath queries with multiple conditions

#### Data Persistence
- **Read**: DOM parsing with XPath queries
- **Write**: DOM manipulation and XML serialization
- **Validation**: Input validation before XML updates

### 8. Build and Deployment

**Maven Configuration:**
- Java 11 compilation
- WAR packaging
- Embedded server plugins (Tomcat 7, Jetty)

**Dependencies:**
- Servlet API 4.0.1
- JSP API 2.3.3
- JSTL 1.2
- Saxon-HE 11.5 (XPath/XQuery/XSLT)
- Jsoup 1.15.3

## Technical Highlights for Presentation

1. **XML as Database**: All data stored in XML with schema validation
2. **XPath for Queries**: All filtering and searching uses XPath expressions
3. **XSL Transformation**: Server-side XML to HTML transformation with styling
4. **Live Search**: Modern AJAX-based search with debouncing
5. **Schema Validation**: Both DTD and XSD for data integrity
6. **MVC Pattern**: Clean separation of concerns
7. **Responsive Design**: Mobile-friendly interface

## File Structure

```
src/main/
├── java/com/recipe/
│   ├── model/              # Recipe.java, User.java
│   ├── servlet/            # 9 servlet classes
│   └── util/               # XMLUtil.java (core XML operations)
├── resources/
│   ├── data/recipes.xml   # Main data file (22+ recipes)
│   ├── recipes.dtd         # DTD schema
│   └── recipes.xsd         # XSD schema
└── webapp/
    ├── WEB-INF/
    │   ├── web.xml         # Servlet configuration
    │   └── xsl/recipes.xsl # XSL stylesheet
    ├── css/style.css       # Styling
    └── *.jsp               # 7 JSP pages
```

## Performance Considerations

- **XPath Caching**: Document loaded once per servlet initialization
- **Debounced Search**: Reduces server requests during typing
- **Efficient Queries**: Indexed XPath expressions for fast lookups

## Security Notes

- Input validation on all forms
- XPath injection prevention (parameter escaping)
- XML entity expansion protection


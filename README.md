# Recipe Recommender Web Application

A Java web application for recommending recipes to users based on their cooking skill level and preferred cuisine type. The application uses XML for data storage, XPath/XQuery for querying, and XSL for transformations.

## Features

- **Recipe Management**: View, add, and filter recipes
- **User Management**: Add users with cooking skill levels and cuisine preferences
- **Smart Recommendations**: Get recipe recommendations based on:
  - Cooking skill level only
  - Cooking skill level AND preferred cuisine type
- **XSL Transformation**: View recipes with color-coded backgrounds (yellow for matching skill level, green for others)
- **XPath/XQuery Queries**: All recipe filtering and recommendations use XPath/XQuery
- **Modern UI**: Clean, intuitive interface with responsive design

## Technologies Used

- Java 11
- Java Servlets & JSP
- XML, DTD, XSD
- XPath/XQuery
- XSL/XSLT
- Maven
- JSTL

## Project Structure

```
web-semantic-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/recipe/
│   │   │       ├── model/          # Recipe and User model classes
│   │   │       ├── servlet/        # Servlet classes
│   │   │       └── util/           # XML utility class
│   │   ├── resources/
│   │   │   ├── data/
│   │   │   │   └── recipes.xml     # XML data file
│   │   │   ├── recipes.dtd        # DTD schema
│   │   │   └── recipes.xsd        # XSD schema
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml        # Servlet configuration
│   │       │   └── xsl/
│   │       │       └── recipes.xsl # XSL stylesheet
│   │       ├── css/
│   │       │   └── style.css      # Stylesheet
│   │       └── *.jsp              # JSP pages
│   └── test/
└── pom.xml                         # Maven configuration
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- A servlet container (Tomcat 9+ recommended)

## Building the Project

1. Clone or download the project
2. Navigate to the project directory
3. Build the project using Maven:

```bash
mvn clean package
```

This will create a WAR file in the `target` directory.

## Running the Application

### Option 1: Using Tomcat Maven Plugin

```bash
mvn tomcat7:run
```

The application will be available at: `http://localhost:8080/recipe-recommender`

### Option 2: Deploy to Tomcat

1. Copy the generated WAR file from `target/recipe-recommender-1.0-SNAPSHOT.war` to Tomcat's `webapps` directory
2. Start Tomcat
3. Access the application at: `http://localhost:8080/recipe-recommender`

## Usage

1. **View Recipes**: Navigate to "View All Recipes" to see all available recipes
2. **Add Recipe**: Use "Add Recipe" to contribute new recipes
3. **Add User**: Create a user profile with cooking skill level and preferred cuisine
4. **Get Recommendations**: 
   - Select a user from "View Users"
   - Click "Get Recommendations" to see recipes matching the user's skill level
   - Use "Recommendations (Skill & Cuisine)" for more specific matches
5. **Filter by Cuisine**: Use the filter on the home page to browse recipes by cuisine type
6. **XSL View**: Click "Recipes (XSL View)" to see recipes transformed with XSL, with color-coded backgrounds

## XML Schema

The application uses both DTD and XSD for validation:

- **DTD**: `src/main/resources/recipes.dtd`
- **XSD**: `src/main/resources/recipes.xsd`

## XPath/XQuery Usage

The application uses XPath queries for:
- Getting the first user: `/recipeData/users/user[1]`
- Filtering recipes by skill level: `/recipeData/recipes/recipe[difficultyLevel='...']`
- Filtering by cuisine: `/recipeData/recipes/recipe[cuisineTypes/cuisine='...']`
- Combined filtering: `/recipeData/recipes/recipe[difficultyLevel='...' and cuisineTypes/cuisine='...']`

## XSL Transformation

The XSL stylesheet (`recipes.xsl`) transforms the XML data into HTML with:
- Yellow background for recipes matching the user's skill level
- Green background for other recipes

## Sample Data

The application comes with 22 sample recipes and 1 sample user. You can add more through the web interface.

## Requirements Met

- ✅ XML data with 20+ recipes and 1 user
- ✅ DTD/XSD schema
- ✅ Read recipes from XML and display in UI
- ✅ Form to add recipes with validation
- ✅ Form to add users
- ✅ Recipe recommendations by skill level (XPath/XQuery)
- ✅ Recipe recommendations by skill level AND cuisine (XPath/XQuery)
- ✅ Display recipes using XSL with color coding
- ✅ View recipe details using XPath/XQuery
- ✅ Filter recipes by cuisine type using XPath/XQuery
- ✅ Intuitive UI with user selection

## Notes

- The XML file is located in `src/main/resources/data/recipes.xml`
- All data operations use XPath/XQuery for querying
- The application validates input before saving to XML
- Users can be selected for personalized recommendations

## License

This project is created for educational purposes.



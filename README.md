# Recipe Recommender Web Application

A Java web application for recommending recipes to users based on their cooking skill level and preferred cuisine type. The application uses XML for data storage, XPath/XQuery for querying, and XSL for transformations.

## Prerequisites

- **Java 11** or higher
- **Maven 3.6** or higher

## Quick Start

### 1. Build the Project

```bash
mvn clean package
```

This will compile the project and create a WAR file in the `target` directory.

### 2. Run the Application

#### Option 1: Using Jetty (Recommended)

```bash
mvn jetty:run
```

#### Option 2: Using Tomcat

```bash
mvn tomcat7:run
```

**Note:** If you encounter issues with Tomcat, use Jetty instead.

### 3. Access the Application

Open your browser and navigate to:

```
http://localhost:8080/recipe-recommender
```

### 4. Stop the Server

Press `Ctrl+C` in the terminal where the server is running.

## Alternative: Manual Deployment

1. Build the project: `mvn clean package`
2. Copy `target/recipe-recommender-1.0-SNAPSHOT.war` to your Tomcat `webapps` directory
3. Start Tomcat
4. Access at `http://localhost:8080/recipe-recommender`

## Troubleshooting

- **Port 8080 already in use**: Change the port in `pom.xml` or stop the application using port 8080
- **Build fails**: Make sure Java 11+ and Maven 3.6+ are installed and in your PATH
- **Tomcat plugin issues**: Use Jetty instead: `mvn jetty:run`

## Features

- View and search recipes
- Add new recipes with ingredients and instructions
- Add users with cooking skill levels
- Get personalized recipe recommendations
- Filter recipes by cuisine type
- XSL transformation view with color-coded recipes

## Project Structure

```
src/main/
├── java/com/recipe/        # Java source code
├── resources/              # XML data and schemas
└── webapp/                 # JSP pages and web resources
```

## Technologies

- Java 11, Servlets, JSP
- XML, DTD, XSD
- XPath/XQuery
- XSL/XSLT
- Maven

For detailed technical implementation, see [TECHNICAL.md](TECHNICAL.md).

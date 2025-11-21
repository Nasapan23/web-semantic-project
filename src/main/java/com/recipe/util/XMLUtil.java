package com.recipe.util;

import com.recipe.model.Recipe;
import com.recipe.model.User;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class XMLUtil {
    private static final String XML_FILE_PATH = "data/recipes.xml";
    private Document document;
    private XPath xpath;
    private String xmlFilePath;

    public XMLUtil() {
        try {
            initializeFilePath();
            loadDocument();
            XPathFactory xPathFactory = XPathFactory.newInstance();
            xpath = xPathFactory.newXPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeFilePath() {
        URL resource = getClass().getClassLoader().getResource(XML_FILE_PATH);
        if (resource != null) {
            xmlFilePath = resource.getPath();
            if (xmlFilePath.startsWith("/") && System.getProperty("os.name").toLowerCase().contains("windows")) {
                xmlFilePath = xmlFilePath.substring(1);
            }
        } else {
            xmlFilePath = "src/main/resources/" + XML_FILE_PATH;
        }
    }

    private void loadDocument() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        
        File xmlFile = new File(xmlFilePath);
        if (!xmlFile.exists()) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(XML_FILE_PATH);
            if (inputStream != null) {
                document = builder.parse(inputStream);
                inputStream.close();
            } else {
                throw new IOException("XML file not found: " + XML_FILE_PATH);
            }
        } else {
            document = builder.parse(xmlFile);
        }
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        try {
            NodeList recipeNodes = document.getElementsByTagName("recipe");
            for (int i = 0; i < recipeNodes.getLength(); i++) {
                Node recipeNode = recipeNodes.item(i);
                Recipe recipe = parseRecipe(recipeNode);
                recipes.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            NodeList userNodes = document.getElementsByTagName("user");
            for (int i = 0; i < userNodes.getLength(); i++) {
                Node userNode = userNodes.item(i);
                User user = parseUser(userNode);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getFirstUser() {
        try {
            String expression = "/recipeData/users/user[1]";
            Node userNode = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
            if (userNode != null) {
                return parseUser(userNode);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(String userId) {
        try {
            String expression = "/recipeData/users/user[@id='" + userId + "']";
            Node userNode = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
            if (userNode != null) {
                return parseUser(userNode);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Recipe getRecipeById(String recipeId) {
        try {
            String expression = "/recipeData/recipes/recipe[@id='" + recipeId + "']";
            Node recipeNode = (Node) xpath.evaluate(expression, document, XPathConstants.NODE);
            if (recipeNode != null) {
                return parseRecipe(recipeNode);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Recipe> getRecipesBySkillLevel(String skillLevel) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            String expression = "/recipeData/recipes/recipe[difficultyLevel='" + skillLevel + "']";
            NodeList recipeNodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
            for (int i = 0; i < recipeNodes.getLength(); i++) {
                Recipe recipe = parseRecipe(recipeNodes.item(i));
                recipes.add(recipe);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public List<Recipe> getRecipesBySkillAndCuisine(String skillLevel, String cuisineType) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            String expression = "/recipeData/recipes/recipe[difficultyLevel='" + skillLevel + 
                              "' and cuisineTypes/cuisine='" + cuisineType + "']";
            NodeList recipeNodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
            for (int i = 0; i < recipeNodes.getLength(); i++) {
                Recipe recipe = parseRecipe(recipeNodes.item(i));
                recipes.add(recipe);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public List<Recipe> getRecipesByCuisine(String cuisineType) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            String expression = "/recipeData/recipes/recipe[cuisineTypes/cuisine='" + cuisineType + "']";
            NodeList recipeNodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
            for (int i = 0; i < recipeNodes.getLength(); i++) {
                Recipe recipe = parseRecipe(recipeNodes.item(i));
                recipes.add(recipe);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public List<Recipe> searchRecipes(String searchQuery) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            // Escape single quotes in search query for XPath
            String escapedQuery = searchQuery.replace("'", "''");
            String lowerQuery = escapedQuery.toLowerCase();
            
            // Build XPath expression to search in title, description (if exists), and ingredients
            // Using translate() for case-insensitive search in XPath 1.0
            String expression = "/recipeData/recipes/recipe[" +
                "contains(translate(title, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + lowerQuery + "') or " +
                "(description and contains(translate(description, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + lowerQuery + "')) or " +
                "contains(translate(ingredients/ingredient, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + lowerQuery + "')" +
                "]";
            
            NodeList recipeNodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
            for (int i = 0; i < recipeNodes.getLength(); i++) {
                Recipe recipe = parseRecipe(recipeNodes.item(i));
                recipes.add(recipe);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return recipes;
    }

    public void addRecipe(Recipe recipe) throws Exception {
        loadDocument();
        Element recipesElement = (Element) document.getElementsByTagName("recipes").item(0);
        
        Element recipeElement = document.createElement("recipe");
        recipeElement.setAttribute("id", recipe.getId());
        
        Element titleElement = document.createElement("title");
        titleElement.setTextContent(recipe.getTitle());
        recipeElement.appendChild(titleElement);
        
        if (recipe.getDescription() != null && !recipe.getDescription().trim().isEmpty()) {
            Element descriptionElement = document.createElement("description");
            descriptionElement.setTextContent(recipe.getDescription());
            recipeElement.appendChild(descriptionElement);
        }
        
        Element cuisineTypesElement = document.createElement("cuisineTypes");
        for (String cuisine : recipe.getCuisineTypes()) {
            Element cuisineElement = document.createElement("cuisine");
            cuisineElement.setTextContent(cuisine);
            cuisineTypesElement.appendChild(cuisineElement);
        }
        recipeElement.appendChild(cuisineTypesElement);
        
        Element difficultyElement = document.createElement("difficultyLevel");
        difficultyElement.setTextContent(recipe.getDifficultyLevel());
        recipeElement.appendChild(difficultyElement);
        
        if (recipe.getCookingTime() != null && !recipe.getCookingTime().trim().isEmpty()) {
            Element cookingTimeElement = document.createElement("cookingTime");
            cookingTimeElement.setTextContent(recipe.getCookingTime());
            recipeElement.appendChild(cookingTimeElement);
        }
        
        if (recipe.getServings() != null && !recipe.getServings().trim().isEmpty()) {
            Element servingsElement = document.createElement("servings");
            servingsElement.setTextContent(recipe.getServings());
            recipeElement.appendChild(servingsElement);
        }
        
        Element ingredientsElement = document.createElement("ingredients");
        for (String ingredient : recipe.getIngredients()) {
            Element ingredientElement = document.createElement("ingredient");
            ingredientElement.setTextContent(ingredient);
            ingredientsElement.appendChild(ingredientElement);
        }
        recipeElement.appendChild(ingredientsElement);
        
        Element instructionsElement = document.createElement("instructions");
        for (String instruction : recipe.getInstructions()) {
            Element stepElement = document.createElement("step");
            stepElement.setTextContent(instruction);
            instructionsElement.appendChild(stepElement);
        }
        recipeElement.appendChild(instructionsElement);
        
        recipesElement.appendChild(recipeElement);
        saveDocument();
    }

    public void addUser(User user) throws Exception {
        loadDocument();
        Element usersElement = (Element) document.getElementsByTagName("users").item(0);
        
        Element userElement = document.createElement("user");
        userElement.setAttribute("id", user.getId());
        
        Element nameElement = document.createElement("name");
        nameElement.setTextContent(user.getName());
        userElement.appendChild(nameElement);
        
        Element surnameElement = document.createElement("surname");
        surnameElement.setTextContent(user.getSurname());
        userElement.appendChild(surnameElement);
        
        Element skillElement = document.createElement("cookingSkillLevel");
        skillElement.setTextContent(user.getCookingSkillLevel());
        userElement.appendChild(skillElement);
        
        Element cuisineElement = document.createElement("preferredCuisineType");
        cuisineElement.setTextContent(user.getPreferredCuisineType());
        userElement.appendChild(cuisineElement);
        
        usersElement.appendChild(userElement);
        saveDocument();
    }

    private Recipe parseRecipe(Node recipeNode) {
        Recipe recipe = new Recipe();
        if (recipeNode.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) recipeNode;
            recipe.setId(element.getAttribute("id"));
            recipe.setTitle(getElementText(element, "title"));
            recipe.setDescription(getElementText(element, "description"));
            recipe.setDifficultyLevel(getElementText(element, "difficultyLevel"));
            recipe.setCookingTime(getElementText(element, "cookingTime"));
            recipe.setServings(getElementText(element, "servings"));
            
            NodeList cuisineNodes = element.getElementsByTagName("cuisine");
            for (int i = 0; i < cuisineNodes.getLength(); i++) {
                recipe.addCuisineType(cuisineNodes.item(i).getTextContent());
            }
            
            NodeList ingredientNodes = element.getElementsByTagName("ingredient");
            for (int i = 0; i < ingredientNodes.getLength(); i++) {
                recipe.addIngredient(ingredientNodes.item(i).getTextContent());
            }
            
            NodeList stepNodes = element.getElementsByTagName("step");
            for (int i = 0; i < stepNodes.getLength(); i++) {
                recipe.addInstruction(stepNodes.item(i).getTextContent());
            }
        }
        return recipe;
    }

    private User parseUser(Node userNode) {
        User user = new User();
        if (userNode.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) userNode;
            user.setId(element.getAttribute("id"));
            user.setName(getElementText(element, "name"));
            user.setSurname(getElementText(element, "surname"));
            user.setCookingSkillLevel(getElementText(element, "cookingSkillLevel"));
            user.setPreferredCuisineType(getElementText(element, "preferredCuisineType"));
        }
        return user;
    }

    private String getElementText(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return nodes.item(0).getTextContent();
        }
        return "";
    }

    private void saveDocument() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        
        DOMSource source = new DOMSource(document);
        File outputFile = new File(xmlFilePath);
        if (!outputFile.exists()) {
            outputFile.getParentFile().mkdirs();
        }
        StreamResult result = new StreamResult(outputFile);
        transformer.transform(source, result);
    }

    public Document getDocument() {
        return document;
    }
}


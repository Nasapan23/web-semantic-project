package com.recipe.servlet;

import com.recipe.model.Recipe;
import com.recipe.util.XMLUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddRecipeServlet extends HttpServlet {
    private XMLUtil xmlUtil;

    @Override
    public void init() throws ServletException {
        xmlUtil = new XMLUtil();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/add-recipe.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String cuisine1 = request.getParameter("cuisine1");
        String cuisine2 = request.getParameter("cuisine2");
        String difficultyLevel = request.getParameter("difficultyLevel");
        String cookingTime = request.getParameter("cookingTime");
        String servings = request.getParameter("servings");
        String ingredientsText = request.getParameter("ingredients");
        String instructionsText = request.getParameter("instructions");

        List<String> errors = validateRecipe(title, cuisine1, cuisine2, difficultyLevel, ingredientsText, instructionsText);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("title", title);
            request.setAttribute("description", description);
            request.setAttribute("cuisine1", cuisine1);
            request.setAttribute("cuisine2", cuisine2);
            request.setAttribute("difficultyLevel", difficultyLevel);
            request.setAttribute("cookingTime", cookingTime);
            request.setAttribute("servings", servings);
            request.setAttribute("ingredients", ingredientsText);
            request.setAttribute("instructions", instructionsText);
            request.getRequestDispatcher("/add-recipe.jsp").forward(request, response);
            return;
        }

        try {
            Recipe recipe = new Recipe();
            recipe.setId("r" + UUID.randomUUID().toString().substring(0, 8));
            recipe.setTitle(title);
            if (description != null && !description.trim().isEmpty()) {
                recipe.setDescription(description);
            }
            recipe.addCuisineType(cuisine1);
            if (cuisine2 != null && !cuisine2.trim().isEmpty()) {
                recipe.addCuisineType(cuisine2);
            }
            recipe.setDifficultyLevel(difficultyLevel);
            if (cookingTime != null && !cookingTime.trim().isEmpty()) {
                recipe.setCookingTime(cookingTime);
            }
            if (servings != null && !servings.trim().isEmpty()) {
                recipe.setServings(servings);
            }
            
            // Parse ingredients (one per line)
            if (ingredientsText != null && !ingredientsText.trim().isEmpty()) {
                String[] ingredientLines = ingredientsText.split("\\r?\\n");
                for (String ingredient : ingredientLines) {
                    String trimmed = ingredient.trim();
                    if (!trimmed.isEmpty()) {
                        recipe.addIngredient(trimmed);
                    }
                }
            }
            
            // Parse instructions (one per line)
            if (instructionsText != null && !instructionsText.trim().isEmpty()) {
                String[] instructionLines = instructionsText.split("\\r?\\n");
                for (String instruction : instructionLines) {
                    String trimmed = instruction.trim();
                    if (!trimmed.isEmpty()) {
                        recipe.addInstruction(trimmed);
                    }
                }
            }

            xmlUtil.addRecipe(recipe);
            response.sendRedirect(request.getContextPath() + "/recipes?success=true");
        } catch (Exception e) {
            request.setAttribute("error", "Error adding recipe: " + e.getMessage());
            request.getRequestDispatcher("/add-recipe.jsp").forward(request, response);
        }
    }

    private List<String> validateRecipe(String title, String cuisine1, String cuisine2, String difficultyLevel, String ingredients, String instructions) {
        List<String> errors = new ArrayList<>();
        if (title == null || title.trim().isEmpty()) {
            errors.add("Title is required");
        }
        if (cuisine1 == null || cuisine1.trim().isEmpty()) {
            errors.add("At least one cuisine type is required");
        }
        if (difficultyLevel == null || difficultyLevel.trim().isEmpty()) {
            errors.add("Difficulty level is required");
        } else if (!difficultyLevel.equals("Beginner") && 
                   !difficultyLevel.equals("Intermediate") && 
                   !difficultyLevel.equals("Advanced")) {
            errors.add("Difficulty level must be Beginner, Intermediate, or Advanced");
        }
        if (ingredients == null || ingredients.trim().isEmpty()) {
            errors.add("At least one ingredient is required");
        }
        if (instructions == null || instructions.trim().isEmpty()) {
            errors.add("At least one instruction step is required");
        }
        return errors;
    }
}



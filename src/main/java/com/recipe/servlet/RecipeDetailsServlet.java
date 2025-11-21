package com.recipe.servlet;

import com.recipe.model.Recipe;
import com.recipe.util.XMLUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecipeDetailsServlet extends HttpServlet {
    private XMLUtil xmlUtil;

    @Override
    public void init() throws ServletException {
        xmlUtil = new XMLUtil();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String recipeId = request.getParameter("id");
        if (recipeId == null || recipeId.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Recipe ID is required");
            return;
        }

        Recipe recipe = xmlUtil.getRecipeById(recipeId);
        if (recipe == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Recipe not found");
            return;
        }

        request.setAttribute("recipe", recipe);
        request.getRequestDispatcher("/recipe-details.jsp").forward(request, response);
    }
}



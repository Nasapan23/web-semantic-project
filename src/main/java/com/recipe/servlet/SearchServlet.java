package com.recipe.servlet;

import com.recipe.model.Recipe;
import com.recipe.util.XMLUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SearchServlet extends HttpServlet {
    private XMLUtil xmlUtil;

    @Override
    public void init() throws ServletException {
        xmlUtil = new XMLUtil();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("q");
        
        if (query == null || query.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/recipes");
            return;
        }

        List<Recipe> recipes = xmlUtil.searchRecipes(query.trim());
        request.setAttribute("recipes", recipes);
        request.setAttribute("searchQuery", query);
        request.getRequestDispatcher("/recipes.jsp").forward(request, response);
    }
}


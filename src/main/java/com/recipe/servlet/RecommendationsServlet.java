package com.recipe.servlet;

import com.recipe.model.Recipe;
import com.recipe.model.User;
import com.recipe.util.XMLUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RecommendationsServlet extends HttpServlet {
    private XMLUtil xmlUtil;

    @Override
    public void init() throws ServletException {
        xmlUtil = new XMLUtil();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String recommendationType = request.getParameter("type");

        User user;
        if (userId != null && !userId.trim().isEmpty()) {
            user = xmlUtil.getUserById(userId);
        } else {
            user = xmlUtil.getFirstUser();
        }

        if (user == null) {
            request.setAttribute("error", "No user found. Please add a user first.");
            request.getRequestDispatcher("/recommendations.jsp").forward(request, response);
            return;
        }

        List<Recipe> recommendations;
        if ("skillAndCuisine".equals(recommendationType)) {
            recommendations = xmlUtil.getRecipesBySkillAndCuisine(
                user.getCookingSkillLevel(), 
                user.getPreferredCuisineType()
            );
            request.setAttribute("recommendationType", "Skill Level & Cuisine");
        } else {
            recommendations = xmlUtil.getRecipesBySkillLevel(user.getCookingSkillLevel());
            request.setAttribute("recommendationType", "Skill Level");
        }

        request.setAttribute("user", user);
        request.setAttribute("recommendations", recommendations);
        request.getRequestDispatcher("/recommendations.jsp").forward(request, response);
    }
}


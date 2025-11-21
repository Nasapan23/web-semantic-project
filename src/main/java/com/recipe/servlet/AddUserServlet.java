package com.recipe.servlet;

import com.recipe.model.User;
import com.recipe.util.XMLUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddUserServlet extends HttpServlet {
    private XMLUtil xmlUtil;

    @Override
    public void init() throws ServletException {
        xmlUtil = new XMLUtil();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/add-user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String cookingSkillLevel = request.getParameter("cookingSkillLevel");
        String preferredCuisineType = request.getParameter("preferredCuisineType");

        List<String> errors = validateUser(name, surname, cookingSkillLevel, preferredCuisineType);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("name", name);
            request.setAttribute("surname", surname);
            request.setAttribute("cookingSkillLevel", cookingSkillLevel);
            request.setAttribute("preferredCuisineType", preferredCuisineType);
            request.getRequestDispatcher("/add-user.jsp").forward(request, response);
            return;
        }

        try {
            User user = new User();
            user.setId("u" + UUID.randomUUID().toString().substring(0, 8));
            user.setName(name);
            user.setSurname(surname);
            user.setCookingSkillLevel(cookingSkillLevel);
            user.setPreferredCuisineType(preferredCuisineType);

            xmlUtil.addUser(user);
            response.sendRedirect(request.getContextPath() + "/users?success=true");
        } catch (Exception e) {
            request.setAttribute("error", "Error adding user: " + e.getMessage());
            request.getRequestDispatcher("/add-user.jsp").forward(request, response);
        }
    }

    private List<String> validateUser(String name, String surname, String cookingSkillLevel, String preferredCuisineType) {
        List<String> errors = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            errors.add("Name is required");
        }
        if (surname == null || surname.trim().isEmpty()) {
            errors.add("Surname is required");
        }
        if (cookingSkillLevel == null || cookingSkillLevel.trim().isEmpty()) {
            errors.add("Cooking skill level is required");
        } else if (!cookingSkillLevel.equals("Beginner") && 
                   !cookingSkillLevel.equals("Intermediate") && 
                   !cookingSkillLevel.equals("Advanced")) {
            errors.add("Cooking skill level must be Beginner, Intermediate, or Advanced");
        }
        if (preferredCuisineType == null || preferredCuisineType.trim().isEmpty()) {
            errors.add("Preferred cuisine type is required");
        }
        return errors;
    }
}


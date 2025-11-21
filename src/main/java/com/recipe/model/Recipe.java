package com.recipe.model;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String id;
    private String title;
    private String description;
    private List<String> cuisineTypes;
    private String difficultyLevel;
    private String cookingTime;
    private String servings;
    private List<String> ingredients;
    private List<String> instructions;

    public Recipe() {
        this.cuisineTypes = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
    }

    public Recipe(String id, String title, List<String> cuisineTypes, String difficultyLevel) {
        this.id = id;
        this.title = title;
        this.cuisineTypes = cuisineTypes != null ? cuisineTypes : new ArrayList<>();
        this.difficultyLevel = difficultyLevel;
        this.ingredients = new ArrayList<>();
        this.instructions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCuisineTypes() {
        return cuisineTypes;
    }

    public void setCuisineTypes(List<String> cuisineTypes) {
        this.cuisineTypes = cuisineTypes;
    }

    public void addCuisineType(String cuisineType) {
        if (this.cuisineTypes == null) {
            this.cuisineTypes = new ArrayList<>();
        }
        this.cuisineTypes.add(cuisineType);
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
    }

    public void addIngredient(String ingredient) {
        if (this.ingredients == null) {
            this.ingredients = new ArrayList<>();
        }
        this.ingredients.add(ingredient);
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions != null ? instructions : new ArrayList<>();
    }

    public void addInstruction(String instruction) {
        if (this.instructions == null) {
            this.instructions = new ArrayList<>();
        }
        this.instructions.add(instruction);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", cuisineTypes=" + cuisineTypes +
                ", difficultyLevel='" + difficultyLevel + '\'' +
                '}';
    }
}


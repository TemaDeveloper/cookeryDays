package com.cocktail_app.apps.cocktailsfoodcreator.models;

import com.google.gson.annotations.SerializedName;

public class Cocktail {

    @SerializedName("strDrink")
    private String nameCocktail;
    @SerializedName("strDrinkThumb")
    private String imgCocktail;
    @SerializedName("strInstructions")
    private String instructionsCocktail;
    @SerializedName("strGlass")
    private String glassCocktail;
    @SerializedName("strAlcoholic")
    private String categoryCocktail;
    private String ingredients;
    private String ingredientImage;
    private String measurements;

    public Cocktail(String nameCocktail, String imgCocktail) {
        this.nameCocktail = nameCocktail;
        this.imgCocktail = imgCocktail;
    }

    public Cocktail(String ingredientImage, String ingredients, String measurements) {
        this.ingredients = ingredients;
        this.measurements = measurements;
        this.ingredientImage = ingredientImage;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getIngredientImage() {
        return ingredientImage;
    }

    public String getMeasurements() {
        return measurements;
    }

    public String getInstructionsCocktail() {
        return instructionsCocktail;
    }

    public String getGlassCocktail() {
        return glassCocktail;
    }

    public String getCategoryCocktail() {
        return categoryCocktail;
    }

    public String getNameCocktail() {
        return nameCocktail;
    }

    public String getImgCocktail() {
        return imgCocktail;
    }
}

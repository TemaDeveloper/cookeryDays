package com.cocktail_app.apps.cocktailsfoodcreator.models;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("strMeal")
    private String nameFood;
    @SerializedName("strMealThumb")
    private String imgFood;

    public Food(String nameFood, String imgFood) {
        this.nameFood = nameFood;
        this.imgFood = imgFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public String getImgFood() {
        return imgFood;
    }
}

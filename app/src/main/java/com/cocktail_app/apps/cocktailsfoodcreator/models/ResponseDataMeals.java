package com.cocktail_app.apps.cocktailsfoodcreator.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseDataMeals {

    @SerializedName("meals")
    private List<Category> categories;
    public List<Category> getCategories() {
        return categories;
    }

}

package com.cocktail_app.apps.cocktailsfoodcreator.models;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("strCategory")
    private String categoryName;
    private int categoryImage;

    public Category(String categoryName){
        this.categoryName = categoryName;
    }

    public void setCategoryImage(int categoryImage) {
        this.categoryImage = categoryImage;
    }

    public int getCategoryImage() {
        return categoryImage;
    }

    public String getCategoryName() {
        return categoryName;
    }
}

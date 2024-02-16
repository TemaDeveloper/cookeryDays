package com.cocktail_app.apps.cocktailsfoodcreator.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseData {
    @SerializedName("drinks")
    private List<Cocktail> drinks;
    public List<Cocktail> getDrinks() {
        return drinks;
    }
}

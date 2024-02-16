package com.cocktail_app.apps.cocktailsfoodcreator.ui.food;

import com.cocktail_app.apps.cocktailsfoodcreator.models.Food;

import java.util.ArrayList;

public interface UpdateFoodCategory {

    public void callBack(int position, ArrayList<Food> foodList, String category);

}

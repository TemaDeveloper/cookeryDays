package com.cocktail_app.apps.cocktailsfoodcreator.ui.food;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cocktail_app.apps.cocktailsfoodcreator.models.Category;
import com.cocktail_app.apps.cocktailsfoodcreator.models.Food;
import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class CategoriesFoodAdapter extends RecyclerView.Adapter<CategoriesFoodAdapter.CategoryViewHolder> {

    private UpdateFoodCategory updateFoodCategory;
    private ArrayList<Category> categories;
    private Context context;
    private boolean check = true;
    private boolean select = true;
    private int rowIndex = -1;

    public CategoriesFoodAdapter(UpdateFoodCategory updateFoodCategory, ArrayList<Category> categories, Context context) {
        this.updateFoodCategory = updateFoodCategory;
        this.categories = categories;
        this.context = context;
    }


    @NonNull
    @Override
    public CategoriesFoodAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View foodView = inflater.inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(foodView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesFoodAdapter.CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.categoryText.setText(categories.get(position).getCategoryName());
        switch (categories.get(position).getCategoryName()) {
            case "Beef":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.beef_img));
                break;
            case "Breakfast":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.breakfast_img));
                break;
            case "Chicken":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.grilled_meat_img));
                break;
            case "Dessert":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.dessert_img));
                break;
            case "Goat":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.skewer_img));
                break;
            case "Lamb":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.lamb_img));
                break;
            case "Miscellaneous":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.food_cart_img));
                break;
            case "Pasta":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.pasta_img));
                break;
            case "Pork":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.pork_img));
                break;
            case "Seafood":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.seafood_img));
                break;
            case "Side":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.side_food_img));
                break;
            case "Starter":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.canape_img));
                break;
            case "Vegan":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.vegan_img));
                break;
            case "Vegetarian":
                holder.categoryImage.setImageDrawable(context.getResources().getDrawable(R.drawable.vegeterian_img));
                break;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        if (check) {
            ArrayList<Food> foodList = new ArrayList<>();
            requestAPI(requestQueue, position, "Beef", foodList);

            check = false;
        }
        holder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowIndex = position;
                notifyDataSetChanged();
                if (position == 0) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Beef", foodList);
                } else if (position == 1) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Breakfast", foodList);
                }else if (position == 2) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Chicken", foodList);
                }else if (position == 3) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Dessert", foodList);
                }else if (position == 4) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Goat", foodList);
                }else if (position == 5) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Lamb", foodList);
                }else if (position == 6) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Miscellaneous", foodList);
                }else if (position == 7) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Pasta", foodList);
                }else if (position == 8) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Pork", foodList);
                }else if (position == 9) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Seafood", foodList);
                }else if (position == 10) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Side", foodList);
                }else if (position == 11) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Starter", foodList);
                }else if (position == 12) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Vegan", foodList);
                }else if (position == 13) {
                    ArrayList<Food> foodList = new ArrayList<>();
                    requestAPI(requestQueue, position, "Vegetarian", foodList);
                }
            }
        });
        if (select) {
            if (position == 0) {
                holder.categoryCard.setStrokeColor(context.getResources().getColor(R.color.blue));
                holder.categoryText.setTextColor(context.getResources().getColor(R.color.textColor));
                holder.categoryImage.setAlpha(1.f);
                select = false;
            }
        } else {
            if (rowIndex == position) {
                holder.categoryCard.setStrokeColor(context.getResources().getColor(R.color.blue));
                holder.categoryText.setTextColor(context.getResources().getColor(R.color.textColor));
                holder.categoryImage.setAlpha(1.f);
            } else {
                holder.categoryCard.setStrokeColor(context.getResources().getColor(android.R.color.darker_gray));
                holder.categoryText.setTextColor(context.getResources().getColor(android.R.color.darker_gray));
                holder.categoryImage.setAlpha(0.5f);
            }
        }


    }

    private void requestAPI(RequestQueue requestQueue, int position, String categoryName, ArrayList<Food> foodList){

       String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c="+categoryName;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("meals");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String foodName = jsonObject.getString("strMeal");
                        String foodImage = jsonObject.getString("strMealThumb");
                        foodList.add(new Food(foodName, foodImage));
                        updateFoodCategory.callBack(position, foodList, categoryName);
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryText;
        private ImageView categoryImage;
        private MaterialCardView categoryCard;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryText = itemView.findViewById(R.id.categoryText);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryCard = itemView.findViewById(R.id.category_card);

        }
    }
}

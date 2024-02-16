package com.cocktail_app.apps.cocktailsfoodcreator.ui.food;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import com.cocktail_app.apps.cocktailsfoodcreator.databinding.FragmentFoodBinding;
import com.cocktail_app.apps.cocktailsfoodcreator.models.ResponseDataMeals;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIService;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIServiceConstructorCocktails;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIServiceConstructorMeals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class FoodFragment extends Fragment implements UpdateFoodCategory {

    private RecyclerView foodRecyclerView, categoriesRecyclerView;
    private ArrayList<Food> foodList;
    private ArrayList<Category> categories;
    private CategoriesFoodAdapter categoriesFoodAdapter;
    private FoodAdapter adapter;
    private FragmentFoodBinding binding;
    private TextView categoryName;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFoodBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init(root);

        categoriesRecyclerView.setHasFixedSize(true);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        requestCategoriesAPI();

        foodRecyclerView.setHasFixedSize(true);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MealsThread mealsThread = new MealsThread(foodList, adapter, foodRecyclerView, progressBar);
        mealsThread.execute();

        return root;
    }

    private void requestCategoriesAPI() {
        APIService apiService = APIServiceConstructorMeals.CreateService(APIService.class);
        Call<ResponseDataMeals> categoryCall = apiService.getCategories();
        categoryCall.enqueue(new Callback<ResponseDataMeals>() {
            @Override
            public void onResponse(Call<ResponseDataMeals> call, retrofit2.Response<ResponseDataMeals> response) {
                for(int i = 0; i < response.body().getCategories().size(); i++){
                    String categoryName = response.body().getCategories().get(i).getCategoryName();
                    categories.add(new Category(categoryName));
                    categoriesFoodAdapter = new CategoriesFoodAdapter(FoodFragment.this::callBack, categories, getActivity().getApplicationContext());
                    categoriesRecyclerView.setAdapter(categoriesFoodAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseDataMeals> call, Throwable t) {}
        });

    }

    private void init(View root){
        foodRecyclerView = root.findViewById(R.id.foodRecyclerView);
        categoryName = root.findViewById(R.id.categoryName);
        categoriesRecyclerView = root.findViewById(R.id.categoriesRecyclerView);
        progressBar = root.findViewById(R.id.progressBar);
        foodList = new ArrayList<>();
        categories = new ArrayList<>();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void callBack(int position, ArrayList<Food> foodList, String category) {
        adapter = new FoodAdapter(foodList, getContext(), getActivity());
        adapter.notifyDataSetChanged();
        foodRecyclerView.setAdapter(adapter);
        categoryName.setText(category);
    }

    public class MealsThread extends AsyncTask<String, String, ArrayList<Food>> {
        private ArrayList<Food> foodList;
        private RecyclerView foodRecyclerView;
        private FoodAdapter adapter;
        private ProgressBar progressBar;

        public MealsThread(ArrayList<Food> foodList, FoodAdapter adapter, RecyclerView recyclerView, ProgressBar progressBar){
            this.adapter = adapter;
            this.foodRecyclerView = recyclerView;
            this.foodList = foodList;
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            foodList = new ArrayList<>();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Food> foods) {
            super.onPostExecute(foods);
            adapter = new FoodAdapter(foodList, getActivity().getApplicationContext(), getActivity());
            foodRecyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<Food> doInBackground(String... strings) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            String url = "https://www.themealdb.com/api/json/v1/1/filter.php?c=Beef";
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

            return foodList;
        }
    }

}
package com.cocktail_app.apps.cocktailsfoodcreator.ui.recipeAddition;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.models.Food;
import com.cocktail_app.apps.cocktailsfoodcreator.ui.food.FoodAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchingFragment extends Fragment {

    private RecyclerView searchingRecyclerView;
    private ArrayList<Food> foodList;
    private FoodAdapter adapter;
    private SearchView searchEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searching, container, false);

        searchEditText = view.findViewById(R.id.search_edit_text);
        searchingRecyclerView = view.findViewById(R.id.search_list);
        searchEditText.setFocusable(true);
        searchEditText.onActionViewExpanded();

        foodList = new ArrayList<>();
        searchingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchingRecyclerView.setHasFixedSize(true);

        searchingRecyclerView.setVisibility(View.GONE);

        searchEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                requestAPI(query);
                searchingRecyclerView.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "q = " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        return view;
    }



    private void requestAPI(String ingredient){
        ArrayList<Food> foodArrayList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://www.themealdb.com/api/json/v1/1/filter.php?i="+ingredient;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("meals");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String foodName = jsonObject.getString("strMeal");
                        String foodImage = jsonObject.getString("strMealThumb");
                        foodArrayList.add(new Food(foodName, foodImage));
                        adapter = new FoodAdapter(foodArrayList, getContext(), getActivity());
                        searchingRecyclerView.setAdapter(adapter);
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

    private void filterWithQuery(String query){
        ArrayList<Food> filteredList = new ArrayList<>();
        for(Food item : foodList){
            if(item.getNameFood().toLowerCase().contains(query.toLowerCase())){
                requestAPI(query);
                filteredList.add(item);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(getContext(), "Not found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.filterList(filteredList);
        }
    }

}
package com.cocktail_app.apps.cocktailsfoodcreator.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cocktail_app.apps.cocktailsfoodcreator.models.Cocktail;
import com.cocktail_app.apps.cocktailsfoodcreator.ui.cocktails.IngredientsAdapter;
import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CocktailInfoActivity extends AppCompatActivity {

    private Intent intent;
    private ImageView detailCocktailImage;
    private Toolbar toolbar;
    private TextView cocktailName, instructionsText, cocktailCategory;
    private ImageView backImageView;
    private ProgressBar progressBar;
    private RecyclerView ingredientsRecyclerView;
    private String cocktailNameStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocktail_info);

        init();
        setSupportActionBar(toolbar);

        CocktailThread cocktailThread = new CocktailThread(ingredientsRecyclerView, progressBar);
        cocktailThread.execute();

        intent = getIntent();
        cocktailNameStr = intent.getStringExtra("cocktailTitle");
        cocktailName.setText(cocktailNameStr);
        Picasso.get().load(intent.getStringExtra("imageURL")).into(detailCocktailImage);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(CocktailInfoActivity.this, LinearLayoutManager.HORIZONTAL, false));


    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        backImageView = findViewById(R.id.backImageView);
        instructionsText = findViewById(R.id.instructionsText);
        cocktailName = findViewById(R.id.cocktailName);
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        detailCocktailImage = findViewById(R.id.image_cocktail_image_view);
        cocktailCategory = findViewById(R.id.categoryText);
    }

    public class CocktailThread extends AsyncTask<String, String, ArrayList<Cocktail>> {

        private RecyclerView ingredientsRecyclerView;
        private ProgressBar progressBar;
        private IngredientsAdapter ingredientsAdapter;
        private ArrayList<Cocktail> ingredientsMeasurements;

        public CocktailThread(RecyclerView ingredientsRecyclerView, ProgressBar progressBar) {
            this.progressBar = progressBar;
            this.ingredientsRecyclerView = ingredientsRecyclerView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ingredientsMeasurements = new ArrayList<>();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Cocktail> cocktails) {
            super.onPostExecute(cocktails);
            ingredientsAdapter = new IngredientsAdapter(CocktailInfoActivity.this, ingredientsMeasurements);
            ingredientsRecyclerView.setAdapter(ingredientsAdapter);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<Cocktail> doInBackground(String... strings) {


            String url = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s=" + cocktailNameStr;

            RequestQueue requestQueue = Volley.newRequestQueue(CocktailInfoActivity.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray jsonArray = response.getJSONArray("drinks");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String cocktailInstructions = jsonObject.getString("strInstructions");
                            String cocktailGlass = jsonObject.getString("strGlass");
                            String cocktailCategoryName = jsonObject.getString("strAlcoholic");
                            instructionsText.setText(cocktailInstructions + "\n" + getResources().getString(R.string.glass_info_activity) + " " + cocktailGlass);
                            cocktailCategory.setText(getResources().getString(R.string.category_info) + " " + cocktailCategoryName);

                            for (int j = 0; j < 15; j++) {
                                String cocktailIngredient = jsonObject.getString("strIngredient" + String.valueOf(j + 1));
                                String cocktailMeasurement = jsonObject.getString("strMeasure" + String.valueOf(j + 1));

                                String ingredientUrl = "https://www.themealdb.com/images/ingredients/" + cocktailIngredient + ".png";

                                ingredientsMeasurements.add(new Cocktail(
                                        ingredientUrl + "",
                                        cocktailIngredient + "",
                                        cocktailMeasurement + ""
                                ));


                                if (cocktailIngredient.equals("") || cocktailIngredient.equals("null")) {
                                    ingredientsMeasurements.removeIf(n -> (n.getIngredients().equals("") || n.getIngredients().equals("null")));
                                    ingredientsAdapter.notifyItemRemoved(j);
                                    ingredientsAdapter.notifyDataSetChanged();
                                }

                            }
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CocktailInfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(jsonObjectRequest);


            return ingredientsMeasurements;
        }
    }

}
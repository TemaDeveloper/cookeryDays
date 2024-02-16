package com.cocktail_app.apps.cocktailsfoodcreator.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.models.Cocktail;
import com.cocktail_app.apps.cocktailsfoodcreator.ui.cocktails.IngredientsAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodInfoActivity extends AppCompatActivity {

    private TextView foodTitle, foodInstruction, areaText, categoryText;
    private YouTubePlayerView youTubePlayerView;
    private ImageView backImageView, foodImageView;
    private RecyclerView ingredientsRecyclerView;
    private ArrayList<Cocktail> ingredientsMeasurements;
    private IngredientsAdapter ingredientsAdapter;
    private Intent intent;
    private String foodNameIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        init();

        getLifecycle().addObserver(youTubePlayerView);

        ingredientsMeasurements = new ArrayList<>();

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        intent = getIntent();

        foodNameIntent = intent.getStringExtra("foodTitle");
        foodTitle.setText(foodNameIntent);
        Picasso.get().load(intent.getStringExtra("imageURL")).into(foodImageView);

        requestAPI();

        ingredientsRecyclerView.setHasFixedSize(true);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(FoodInfoActivity.this, LinearLayoutManager.HORIZONTAL, false));

    }

    private void requestAPI(){
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + foodNameIntent;
        RequestQueue requestQueue = Volley.newRequestQueue(FoodInfoActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("meals");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String categoryName = jsonObject.getString("strCategory");
                        String areaName = jsonObject.getString("strArea");
                        String instructionsText = jsonObject.getString("strInstructions");
                        String youtubeID = jsonObject.getString("strYoutube");
                        foodInstruction.setText(instructionsText);
                        categoryText.setText("Category: " + categoryName);
                        areaText.setText("Area: " + areaName);

                        for(int j = 0; j < 20; j++) {
                            String cocktailIngredient = jsonObject.getString("strIngredient" + String.valueOf(j + 1));
                            String cocktailMeasurement = jsonObject.getString("strMeasure" + String.valueOf(j + 1));

                            String ingredientUrl = "https://www.themealdb.com/images/ingredients/" + cocktailIngredient + ".png";

                            ingredientsMeasurements.add(new Cocktail(ingredientUrl + "", cocktailIngredient + "", cocktailMeasurement + ""));
                            ingredientsAdapter = new IngredientsAdapter(getApplicationContext(), ingredientsMeasurements);
                            if (cocktailIngredient.equals("") || cocktailIngredient.equals("null")) {
                                ingredientsMeasurements.removeIf(n -> (n.getIngredients().equals("") || n.getIngredients().equals("null")));
                                ingredientsAdapter.notifyItemRemoved(j);
                                ingredientsAdapter.notifyDataSetChanged();
                            }
                            ingredientsRecyclerView.setAdapter(ingredientsAdapter);
                        }
                        if(!youtubeID.equals("")){
                            String youtubeText = youtubeID.substring(32);
                            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    String videoId = youtubeText;
                                    youTubePlayer.loadVideo(videoId, 0);
                                    youTubePlayer.pause();
                                }
                            });
                        }
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

    private void init() {
        foodTitle = findViewById(R.id.foodName);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        foodImageView = findViewById(R.id.image_food_image_view);
        backImageView = findViewById(R.id.backImageView);
        foodInstruction = findViewById(R.id.instructionsText);
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        areaText = findViewById(R.id.areaText);
        categoryText = findViewById(R.id.categoryText);
    }

}
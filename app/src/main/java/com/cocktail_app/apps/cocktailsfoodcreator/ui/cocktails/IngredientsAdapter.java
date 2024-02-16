package com.cocktail_app.apps.cocktailsfoodcreator.ui.cocktails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.models.Cocktail;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    private Context context;
    private ArrayList<Cocktail> ingredientsMeasurements;

    public IngredientsAdapter(Context context, ArrayList<Cocktail> ingredients) {
        this.context = context;
        this.ingredientsMeasurements = ingredients;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingredientView = inflater.inflate(R.layout.ingredient_item, parent, false);
        return new IngredientsViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        final Cocktail ingredientMeasurement = ingredientsMeasurements.get(position);
        for (int i = 0; i < ingredientsMeasurements.size(); i++) {
            holder.ingredientName.setText(ingredientMeasurement.getIngredients());
            holder.measurementName.setText(ingredientMeasurement.getMeasurements());
            if(ingredientMeasurement.getMeasurements().equals("null")){
                holder.measurementName.setText("");
            }
            if (holder.ingredientName.getText().toString().equals("null")) {
                holder.itemView.setVisibility(View.GONE);
            } else {
                holder.itemView.setVisibility(View.VISIBLE);
            }
            Picasso.get().load(ingredientMeasurement.getIngredientImage()).into(holder.ingredientImage);
            holder.notFoundImageText.setVisibility(View.GONE);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        URL url = new URL(ingredientMeasurement.getIngredientImage());
                        HttpURLConnection http = (HttpURLConnection) url.openConnection();
                        http.setRequestMethod("GET");
                        http.setDoOutput(true);

                        if (http.getResponseMessage().equals("Not Found")) {
                           //holder.notFoundImageText.setVisibility(View.VISIBLE);
                           //holder.ingredientImage.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

        }
    }

    @Override
    public int getItemCount() {
        return ingredientsMeasurements.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientName, measurementName, notFoundImageText;
        private ImageView ingredientImage;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientName = itemView.findViewById(R.id.ingredientTitle);
            notFoundImageText = itemView.findViewById(R.id.image_not_found_text);
            measurementName = itemView.findViewById(R.id.measurementsText);
            ingredientImage = itemView.findViewById(R.id.imageCocktailIngredient);

        }
    }


}

package com.cocktail_app.apps.cocktailsfoodcreator.ui.food;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.activities.FoodInfoActivity;
import com.cocktail_app.apps.cocktailsfoodcreator.models.Food;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private ArrayList<Food> foodArrayList;
    private Context context;
    private Activity activity;

    public void filterList(ArrayList<Food> filterlist) {
        foodArrayList = filterlist;
        notifyDataSetChanged();
    }

    public FoodAdapter(ArrayList<Food> foodArrayList, Context context, Activity activity) {
        this.foodArrayList = foodArrayList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View foodView = inflater.inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(foodView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.foodName.setText(foodArrayList.get(position).getNameFood());
        Picasso.get().load(foodArrayList.get(position).getImgFood()).into(holder.foodImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodInfoActivity.class);
                intent.putExtra("imageURL", foodArrayList.get(position).getImgFood());
                intent.putExtra("foodTitle", foodArrayList.get(position).getNameFood());
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(activity, (View)holder.foodImage, "foodImage");
                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodArrayList.size();
    }

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView foodName;
        private ImageView foodImage;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodName = itemView.findViewById(R.id.foodTitle);
        }
    }
}

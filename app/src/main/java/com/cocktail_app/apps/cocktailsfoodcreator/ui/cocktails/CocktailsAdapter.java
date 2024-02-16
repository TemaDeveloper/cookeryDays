package com.cocktail_app.apps.cocktailsfoodcreator.ui.cocktails;

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

import com.airbnb.lottie.LottieAnimationView;
import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.activities.CocktailInfoActivity;
import com.cocktail_app.apps.cocktailsfoodcreator.models.Cocktail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CocktailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Cocktail> cocktails;
    private Context context;
    private Activity activity;
    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_CARD = 1;


    public CocktailsAdapter(ArrayList<Cocktail> cocktails, Context context, Activity activity) {
        this.cocktails = cocktails;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        return (position + 1) % 9 == 0 ? VIEW_TYPE_CARD : VIEW_TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                view = inflater.inflate(R.layout.cocktail_item, parent, false);
                return new CocktailViewHolder(view);
            case VIEW_TYPE_CARD:
                view = inflater.inflate(R.layout.ad_tem, parent, false);
                return new CardViewHolder(view);
            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }
    public class CardViewHolder extends RecyclerView.ViewHolder {
        // Add views and bind data for card items
        public CardViewHolder(View itemView) {
            super(itemView);
            // Initialize views
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        int viewType = getItemViewType(position);

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                CocktailViewHolder cocktailViewHolder = (CocktailViewHolder) holder;
                cocktailViewHolder.cocktailName.setText(cocktails.get(position).getNameCocktail());
                Picasso.get().load(cocktails.get(position).getImgCocktail()).into(cocktailViewHolder.cocktailImg);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, CocktailInfoActivity.class);
                        intent.putExtra("imageURL", cocktails.get(position).getImgCocktail());
                        intent.putExtra("cocktailTitle", cocktails.get(position).getNameCocktail());
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation(activity, (View)cocktailViewHolder.cocktailImg, "cocktailImage");
                        context.startActivity(intent, options.toBundle());
                    }
                });
                break;
            case VIEW_TYPE_CARD:
                // Bind data for card items
                break;
        }


    }

    @Override
    public int getItemCount() {
        return cocktails.size();
    }

    public class CocktailViewHolder extends RecyclerView.ViewHolder {
        private LottieAnimationView likeCocktailAnimation;
        private TextView cocktailName;
        private ImageView cocktailImg;
        private boolean isChecked = false;

        public CocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            likeCocktailAnimation = itemView.findViewById(R.id.likeLottieAnimation);
            cocktailName = itemView.findViewById(R.id.name_cocktail_text_view);
            cocktailImg = itemView.findViewById(R.id.image_cocktail_image_view);

            likeCocktailAnimation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isChecked){
                        likeCocktailAnimation.setSpeed(-1);
                        likeCocktailAnimation.playAnimation();
                        isChecked = false;
                    }else{
                        likeCocktailAnimation.setSpeed(1);
                        likeCocktailAnimation.playAnimation();
                        isChecked = true;
                    }
                }
            });

        }
    }
}

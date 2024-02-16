package com.cocktail_app.apps.cocktailsfoodcreator.ui.cocktails;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.models.Cocktail;
import com.cocktail_app.apps.cocktailsfoodcreator.models.ResponseData;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIService;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIServiceConstructorCocktails;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;


public class NonalcoholicCocktailsFragment extends Fragment {

    private RecyclerView nonalcoholicRecyclerView;
    private ProgressBar progressBar;
    private Context context;

    public NonalcoholicCocktailsFragment(){}
    public NonalcoholicCocktailsFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_non_alckoholic_cocktails, container, false);

        init(root);

        NonAlcoCocktailsThread nonAlcoCocktailsThread = new NonAlcoCocktailsThread(context, getActivity(), nonalcoholicRecyclerView, progressBar);
        nonAlcoCocktailsThread.execute();

        nonalcoholicRecyclerView.setHasFixedSize(true);
        nonalcoholicRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));

        return root;
    }

    private void init(View root){
        nonalcoholicRecyclerView = root.findViewById(R.id.nonalcoholRecyclerView);
        progressBar = root.findViewById(R.id.progressBar);
    }

    public class NonAlcoCocktailsThread extends AsyncTask<String, String, ArrayList<Cocktail>>{

        private Context context;
        private Activity activity;
        private RecyclerView recyclerView;
        private ProgressBar progressBar;
        private ArrayList<Cocktail> cocktails;
        private CocktailsAdapter adapter;

        public NonAlcoCocktailsThread(Context context, Activity activity, RecyclerView recyclerView, ProgressBar progressBar) {
            this.context = context;
            this.activity = activity;
            this.recyclerView = recyclerView;
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cocktails = new ArrayList<>();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Cocktail> cocktails) {
            super.onPostExecute(cocktails);
            adapter = new CocktailsAdapter(cocktails, context, activity);
            recyclerView.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<Cocktail> doInBackground(String... strings) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            APIService service = APIServiceConstructorCocktails.CreateService(APIService.class);
            Call<ResponseData> callNonAlcoSync = service.getNonAlcoCocktails();
            try {
                Response<ResponseData> response = callNonAlcoSync.execute();
                ResponseData responseAPI = response.body();
                for(int i = 0; i < responseAPI.getDrinks().size(); i++){
                    cocktails.add(new Cocktail(responseAPI.getDrinks().get(i).getNameCocktail(),
                            responseAPI.getDrinks().get(i).getImgCocktail()));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            return cocktails;
        }
    }

}
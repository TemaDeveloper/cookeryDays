package com.cocktail_app.apps.cocktailsfoodcreator.ui.cocktails;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.models.Cocktail;
import com.cocktail_app.apps.cocktailsfoodcreator.models.ResponseData;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIService;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIServiceConstructorCocktails;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;

public class AlcoholicCocktailsFragment extends Fragment {

    private RecyclerView cocktailsRecyclerView;
    private ProgressBar progressBar;
    private Context context;
    private TextView randomTipsText;

    public AlcoholicCocktailsFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_alcoholic_cocktails, container, false);

        init(root);

        AlcoholicAsync alcoholicAsync = new AlcoholicAsync(context, getActivity(), cocktailsRecyclerView, progressBar);
        alcoholicAsync.execute();

        cocktailsRecyclerView.setHasFixedSize(true);
        cocktailsRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        randomTipsText.setText(getRandomQuote() + "");

        return root;

    }

    private String getRandomQuote(){
        Random random = new Random();
        String[] tipsArray = getContext().getResources().getStringArray(R.array.recipe_quotes);
        return tipsArray[random.nextInt(tipsArray.length)];
    }

    private void init(View root) {
        cocktailsRecyclerView = root.findViewById(R.id.alcoholRecyclerView);
        progressBar = root.findViewById(R.id.progressBar);
        randomTipsText = root.findViewById(R.id.tipsText);
    }

    public static class AlcoholicAsync extends AsyncTask<String, String, ArrayList<Cocktail>> {

        private Context context;
        private Activity activity;
        private RecyclerView recyclerView;
        private ProgressBar progressBar;
        private ArrayList<Cocktail> cocktails;
        private CocktailsAdapter adapter;

        public AlcoholicAsync(Context context, Activity activity, RecyclerView recyclerView, ProgressBar progressBar) {
            this.context = context;
            this.activity = activity;
            this.progressBar = progressBar;
            this.recyclerView = recyclerView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cocktails = new ArrayList<>();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<Cocktail> s) {
            super.onPostExecute(s);

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
            Call<ResponseData> callSync = service.getAlcoCocktails();
            try {
                Response<ResponseData> response = callSync.execute();
                ResponseData apiResponse = response.body();
                for (int i = 0; i < apiResponse.getDrinks().size(); i++) {
                    //API response
                    cocktails.add(new Cocktail(apiResponse.getDrinks().get(i).getNameCocktail(),
                            apiResponse.getDrinks().get(i).getImgCocktail()));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return cocktails;
        }
    }

}
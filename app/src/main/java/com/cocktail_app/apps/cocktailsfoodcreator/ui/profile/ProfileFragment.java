package com.cocktail_app.apps.cocktailsfoodcreator.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.activities.AuthActivity;
import com.cocktail_app.apps.cocktailsfoodcreator.activities.MainActivity;
import com.cocktail_app.apps.cocktailsfoodcreator.adapters.ViewPager2Adapter;
import com.cocktail_app.apps.cocktailsfoodcreator.databinding.FragmentProfileBinding;
import com.cocktail_app.apps.cocktailsfoodcreator.models.User;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIService;
import com.cocktail_app.apps.cocktailsfoodcreator.services.DBConstructor;
import com.cocktail_app.apps.cocktailsfoodcreator.services.SharedPrefManager;
import com.cocktail_app.apps.cocktailsfoodcreator.ui.cocktails.AlcoholicCocktailsFragment;
import com.cocktail_app.apps.cocktailsfoodcreator.ui.cocktails.CocktailsFragment;
import com.cocktail_app.apps.cocktailsfoodcreator.ui.cocktails.NonalcoholicCocktailsFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ImageView settingsImage, editImage;
    private String email;
    private TextView nameText, emailText;
    private MaterialCardView cardLogin;
    private MaterialButton loginButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init(root);
        getUser();

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), UpdateProfileActivity.class)
                        .putExtra("name", nameText.getText().toString())
                        .putExtra("email", emailText.getText().toString()));
            }
        });

        settingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsBottomFragment settingsBottomFragment = new SettingsBottomFragment();
                settingsBottomFragment.show(getFragmentManager(), settingsBottomFragment.getTag());
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AuthActivity.class));
            }
        });

        loadViewPager();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getUser() {
        email = SharedPrefManager.getInstance(getContext()).getUserEmail();
        if(email.equals("")){
            int num = (int) (Math.random() * 999 - 1) + 1;
            nameText.setText("Chef " + "#" + num);
            emailText.setText("");
            cardLogin.setVisibility(View.VISIBLE);
        }else{
            APIService apiService = DBConstructor.CreateService(APIService.class);
            Call<User> userCall = apiService.getUser(email);
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    nameText.setText(response.body().getName());
                    emailText.setText(response.body().getEmail());
                    cardLogin.setVisibility(View.GONE);
                    SharedPrefManager.getInstance(getContext()).setUserID(response.body().getId());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

    private void loadViewPager() {

        tabLayout.addTab(tabLayout.newTab().setText("Saved Recipes"));
        tabLayout.addTab(tabLayout.newTab().setText("My Recipes"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        ViewPager2Adapter myAdapter = new ViewPager2Adapter(getActivity().getSupportFragmentManager(), getLifecycle());
        myAdapter.addFragment(new SavedRecipeFragment());
        myAdapter.addFragment(new MyRecipeFragment());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(myAdapter);
    }

    private void init(View root){
        tabLayout = root.findViewById(R.id.tablayout);
        viewPager = root.findViewById(R.id.viewpager);
        settingsImage = root.findViewById(R.id.settingsImage);
        nameText = root.findViewById(R.id.nameText);
        emailText = root.findViewById(R.id.emailText);
        editImage = root.findViewById(R.id.editImage);
        cardLogin = root.findViewById(R.id.cardLogin);
        loginButton = root.findViewById(R.id.loginButton);
    }

}
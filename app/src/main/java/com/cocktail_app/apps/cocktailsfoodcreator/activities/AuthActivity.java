package com.cocktail_app.apps.cocktailsfoodcreator.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.adapters.ViewPager2Adapter;
import com.cocktail_app.apps.cocktailsfoodcreator.loginSignup.LoginFragment;
import com.cocktail_app.apps.cocktailsfoodcreator.loginSignup.SignupFragment;
import com.google.android.material.tabs.TabLayout;

public class AuthActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

       tabLayout = findViewById(R.id.tablayout);
       viewPager = findViewById(R.id.loginSignUpViewPager);

       loadViewPager();

    }

    private void loadViewPager(){

        tabLayout.addTab(tabLayout.newTab().setText("Log in"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));

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

        ViewPager2Adapter myAdapter = new ViewPager2Adapter(getSupportFragmentManager(), getLifecycle());
        myAdapter.addFragment(new LoginFragment());
        myAdapter.addFragment(new SignupFragment());
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(myAdapter);
    }

}
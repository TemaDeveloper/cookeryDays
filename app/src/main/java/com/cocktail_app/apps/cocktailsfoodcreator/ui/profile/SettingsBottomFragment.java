package com.cocktail_app.apps.cocktailsfoodcreator.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.activities.AuthActivity;
import com.cocktail_app.apps.cocktailsfoodcreator.services.SharedPrefManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class SettingsBottomFragment extends BottomSheetDialogFragment {

    private LinearLayout linSignOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_bottom, container, false);

        linSignOut = view.findViewById(R.id.linSignOut);

        linSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getContext()).setUserEmail("");
                SharedPrefManager.getInstance(getContext()).setUserID(0);
                startActivity(new Intent(getContext(), AuthActivity.class));
            }
        });

        return view;
    }
}
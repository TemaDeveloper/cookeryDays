package com.cocktail_app.apps.cocktailsfoodcreator.loginSignup;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.activities.MainActivity;
import com.cocktail_app.apps.cocktailsfoodcreator.models.User;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIService;
import com.cocktail_app.apps.cocktailsfoodcreator.services.DBConstructor;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupFragment extends Fragment {

    private TextInputEditText nameEditText, emailEditText, passwordEditText;
    private MaterialButton signUpButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        init(root);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                APIService apiService = DBConstructor.CreateService(APIService.class);
                Call<ResponseBody> userAdditionCall = apiService.addUser
                        (
                                nameEditText.getText().toString().trim(),
                                emailEditText.getText().toString().trim(),
                                "",
                                passwordEditText.getText().toString().trim()
                        );

                userAdditionCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(getContext(), MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("SIGNUP_FAILURE_TAG", t.getMessage());
                    }
                });


            }
        });

        return root;
    }

    private void init(View root) {
        signUpButton = root.findViewById(R.id.signUpButton);
        nameEditText = root.findViewById(R.id.nameEditText);
        emailEditText = root.findViewById(R.id.emailEditText);
        passwordEditText = root.findViewById(R.id.passwordEditText);
    }

}
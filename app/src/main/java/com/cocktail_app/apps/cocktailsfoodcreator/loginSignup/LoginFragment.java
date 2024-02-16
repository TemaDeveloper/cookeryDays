package com.cocktail_app.apps.cocktailsfoodcreator.loginSignup;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cocktail_app.apps.cocktailsfoodcreator.R;
import com.cocktail_app.apps.cocktailsfoodcreator.activities.MainActivity;
import com.cocktail_app.apps.cocktailsfoodcreator.models.User;
import com.cocktail_app.apps.cocktailsfoodcreator.services.APIService;
import com.cocktail_app.apps.cocktailsfoodcreator.services.DBConstructor;
import com.cocktail_app.apps.cocktailsfoodcreator.services.SharedPrefManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {

    private MaterialButton loginAsGuestButton, loginButton;
    private TextInputEditText emailEditText, passwordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginAsGuestButton = view.findViewById(R.id.loginAsGuestButton);
        loginButton = view.findViewById(R.id.loginButton);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        if(SharedPrefManager.getInstance(getContext()).getUserID() != 0){
            String EMAIL = SharedPrefManager.getInstance(getContext()).getUserEmail();
            startActivity(new Intent(getContext(), MainActivity.class).putExtra("email", EMAIL));
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if(checkFields(email, password)){
                    login(email, password);
                }

            }
        });

        loginAsGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

        return view;
    }

    private void login(String EMAIL, String PASSWORD){
        APIService apiService = DBConstructor.CreateService(APIService.class);
        Call<ResponseBody> callLogin = apiService.loginUser(EMAIL, PASSWORD);
        callLogin.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                SharedPrefManager.getInstance(getContext()).setUserEmail(EMAIL);
                startActivity(new Intent(getContext(), MainActivity.class).putExtra("email", EMAIL));
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("ResponseMessage", t.getMessage() + " message" + " " + t.getCause());
            }
        });
    }

    private boolean checkFields(String email, String password){
        boolean checkFields;
        if(email.isEmpty()){
            emailEditText.setError("Enter your email");
            checkFields = false;
        }else if(password.isEmpty()){
            passwordEditText.setError("Enter your password");
            checkFields = false;
        }else if (email.isEmpty() && password.isEmpty()){
            emailEditText.setError("Enter your email");
            passwordEditText.setError("Enter your password");
            checkFields = false;
        }else{
            checkFields = true;
        }
        return checkFields;
    }

}